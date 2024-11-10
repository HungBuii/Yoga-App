package com.gohool.firstlook.yogaapp.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.gohool.firstlook.yogaapp.Data.ClassDatabaseHandle;
import com.gohool.firstlook.yogaapp.Data.CourseDatabaseHandle;
import com.gohool.firstlook.yogaapp.Model.Course;
import com.gohool.firstlook.yogaapp.R;
import com.gohool.firstlook.yogaapp.UI.MainRecyclerViewAdapter;
import com.gohool.firstlook.yogaapp.Util.Constants;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.telecom.Call;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private CourseDatabaseHandle dbCourse;
    private ClassDatabaseHandle dbClass;

    private Spinner spinnerTypeYoga;
    ArrayAdapter adapter2;

    private Spinner spinnerDay;
    ArrayAdapter adapter;

//    private EditText addDayYoga;
    private EditText addPriceYoga;

    private Spinner spinnerTime;
    ArrayAdapter adapter1;

    private EditText addCapacityYoga;
    private EditText addDurationYoga;
    private EditText addDescriptionYoga;

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
        dbCourse = new CourseDatabaseHandle(this);
        dbClass = new ClassDatabaseHandle(this);


        // Show list courses
        recyclerCoursesView.setHasFixedSize(true);
        recyclerCoursesView.setLayoutManager(new LinearLayoutManager(this));

        courseList = dbCourse.getAllCourses();

        for (Course c : courseList)
        {
            Course course = new Course();
            course.setId(c.getId());
            course.setTypeYoga(c.getTypeYoga());
            course.setDayYoga(c.getDayYoga());
            course.setPriceYoga(c.getPriceYoga());
            course.setTimeYoga(c.getTimeYoga());
            course.setCapacityYoga(c.getCapacityYoga());
            course.setDurationYoga(c.getDurationYoga());
            course.setDescriptionYoga(c.getDescriptionYoga());
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

        spinnerTypeYoga = (Spinner) view.findViewById(R.id.spinnerTypeYoga);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.spinnerTypeYoga, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeYoga.setAdapter(adapter2);

        spinnerDay = (Spinner) view.findViewById(R.id.spinnerDay);
        adapter = ArrayAdapter.createFromResource(this, R.array.spinnerDay, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);

        addPriceYoga = (EditText) view.findViewById(R.id.addPriceYoga);

        spinnerTime = (Spinner) view.findViewById(R.id.spinnerTime);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.spinnerTime, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter1);

        addCapacityYoga = (EditText) view.findViewById(R.id.addCapacityYoga);
        addDurationYoga = (EditText) view.findViewById(R.id.addDurationYoga);
        addDescriptionYoga = (EditText) view.findViewById(R.id.addDescriptionYoga);
        saveButton = (Button) view.findViewById(R.id.saveButton);



        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String priceYoga = addPriceYoga.getText().toString();
                String capacityYoga = addCapacityYoga.getText().toString();
                String durationYoga = addDurationYoga.getText().toString();
                String descriptionYoga = addDescriptionYoga.getText().toString();

                // Validate user input
                boolean check = validateInfo( priceYoga,
                        capacityYoga, durationYoga, descriptionYoga);
                if (check) {
                    saveYogaCourseToDB(v);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Sorry, Check the Information", Toast.LENGTH_SHORT).show();
                }
            }

            private Boolean validateInfo(String priceYoga, String capacityYoga, String durationYoga, String descriptionYoga)
            {
                if (priceYoga.length() == 0)
                {
                    addPriceYoga.requestFocus();
                    addPriceYoga.setError("Please enter price");
                    return false;
                }
                else if (capacityYoga.length() == 0) {
                    addCapacityYoga.requestFocus();
                    addCapacityYoga.setError("Please enter capacity");
                    return false;
                }
                else if (durationYoga.length() == 0) {
                    addDurationYoga.requestFocus();
                    addDurationYoga.setError("Please enter duration");
                    return false;
                }
                else if (descriptionYoga.length() == 0) {
                    addDescriptionYoga.requestFocus();
                    addDescriptionYoga.setError("Please enter description");
                    return false;
                }
                return true;
            }

        });
    }

    private void saveYogaCourseToDB(View v)
    {
        Course course = new Course();
        String newAddTypeYoga = spinnerTypeYoga.getSelectedItem().toString();
        String newAddDayYoga = spinnerDay.getSelectedItem().toString();
//        String newAddDayYoga = addDayYoga.getText().toString();
        String newAddPriceYoga = addPriceYoga.getText().toString();
        String newAddTimeYoga = spinnerTime.getSelectedItem().toString();
        String newAddCapacityYoga = addCapacityYoga.getText().toString();
        String newAddDurationYoga = addDurationYoga.getText().toString();
        String newAddDescriptionYoga = addDescriptionYoga.getText().toString();

        course.setTypeYoga(newAddTypeYoga);
        course.setDayYoga(newAddDayYoga);
        course.setPriceYoga(newAddPriceYoga);
        course.setTimeYoga(newAddTimeYoga);
        course.setCapacityYoga(newAddCapacityYoga);
        course.setDurationYoga(newAddDurationYoga);
        course.setDescriptionYoga(newAddDescriptionYoga);

        // Save to db
        dbCourse.addCourse(course);
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
        if (id == R.id.delete_course_db) {
            dbCourse.deleteDataCourseTable();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                }
            }, 1000);
            return true;
        }

        if (id == R.id.delete_class_db)
        {
            dbClass.deleteDataClassTable();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                }
            }, 1000);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}