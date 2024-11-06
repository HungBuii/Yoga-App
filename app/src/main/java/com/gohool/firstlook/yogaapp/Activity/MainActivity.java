package com.gohool.firstlook.yogaapp.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.gohool.firstlook.yogaapp.Data.CourseDatabaseHandle;
import com.gohool.firstlook.yogaapp.Model.Course;
import com.gohool.firstlook.yogaapp.R;
import com.gohool.firstlook.yogaapp.UI.MainRecyclerViewAdapter;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.yogaapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private CourseDatabaseHandle db;

    private EditText addTypeYoga;
    private Spinner spinnerDay;
    ArrayAdapter adapter;
//    private EditText addDayYoga;
    private EditText addPriceYoga;
    private Button saveButton;

    private RecyclerView recyclerCoursesView;
    private MainRecyclerViewAdapter mainRecyclerViewAdapter;
    private List<Course> courseList;
    private List<Course> courseListEdit;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();

                createAddCoursePopupDialog();
            }
        });

        // Initialize
        recyclerCoursesView = (RecyclerView) findViewById(R.id.recyclerCoursesView);
        courseList = new ArrayList<>();
        courseListEdit = new ArrayList<>();
        db = new CourseDatabaseHandle(this);


        // Show list courses
        recyclerCoursesView.setHasFixedSize(true);
        recyclerCoursesView.setLayoutManager(new LinearLayoutManager(this));

        courseList = db.getAllCourses();

        for (Course c : courseList)
        {
            Course course = new Course(c.getId(), c.getTypeYoga(), c.getDayYoga() , c.getPriceYoga());
//            course.setId(c.getId());
//            course.setTypeYoga("Type of Yoga: " + c.getTypeYoga());
//            course.setDayYoga("Day: " + c.getDayYoga());
//            course.setPriceYoga("Price: " + c.getPriceYoga());
            courseListEdit.add(course);
        }

        mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this, courseListEdit);
        recyclerCoursesView.setAdapter(mainRecyclerViewAdapter);
        mainRecyclerViewAdapter.notifyDataSetChanged();


    }

    private void createAddCoursePopupDialog()
    {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.add_course, null);

        addTypeYoga = (EditText) view.findViewById(R.id.addTypeYoga);

        spinnerDay = (Spinner) view.findViewById(R.id.spinnerDay);
        adapter = ArrayAdapter.createFromResource(this, R.array.spinnerDay, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);

//        addDayYoga = (EditText) view.findViewById(R.id.addDayYoga);
        addPriceYoga = (EditText) view.findViewById(R.id.addPriceYoga);
        saveButton = (Button) view.findViewById(R.id.saveButton);



        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addTypeYoga.getText().toString().isEmpty() &&
                        !addPriceYoga.getText().toString().isEmpty()
                )

                {
                    saveYogaCourseToDB(v);
                }

            }
        });
    }

    private void saveYogaCourseToDB(View v)
    {
        Course course = new Course();

        String newAddTypeYoga = addTypeYoga.getText().toString();
        String newAddDayYoga = spinnerDay.getSelectedItem().toString();
//        String newAddDayYoga = addDayYoga.getText().toString();
        String newAddPriceYoga = addPriceYoga.getText().toString();

        course.setTypeYoga(newAddTypeYoga);
        course.setDayYoga(newAddDayYoga);
        course.setPriceYoga(newAddPriceYoga);

        // Save to db
        db.addCourse(course);
        Snackbar.make(v, "Course saved", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}