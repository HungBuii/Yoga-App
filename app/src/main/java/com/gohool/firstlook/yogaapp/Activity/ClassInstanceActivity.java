package com.gohool.firstlook.yogaapp.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.yogaapp.Data.ClassDatabaseHandle;
import com.gohool.firstlook.yogaapp.Data.CourseDatabaseHandle;
import com.gohool.firstlook.yogaapp.Model.ClassYoga;
import com.gohool.firstlook.yogaapp.Model.Course;
import com.gohool.firstlook.yogaapp.R;
import com.gohool.firstlook.yogaapp.UI.ClassRecyclerViewAdapter;
import com.gohool.firstlook.yogaapp.UI.MainRecyclerViewAdapter;
import com.gohool.firstlook.yogaapp.Util.DateUtil;
import com.gohool.firstlook.yogaapp.databinding.ActivityClassInstanceBinding;
import com.gohool.firstlook.yogaapp.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClassInstanceActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ClassDatabaseHandle db;

    private TextView addDateClass;
    private TextView addTeacherClass;
    private TextView addCommentClass;

    private Button saveButtonClass;

    private RecyclerView recyclerClassView;
    private ClassRecyclerViewAdapter classRecyclerViewAdapter;
    private List<ClassYoga> classYogaList;
    private List<ClassYoga> classYogaListEdit;

    private AppBarConfiguration appBarConfiguration;
    private ActivityClassInstanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int course_id = 0;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt("course_id");
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_class_instance);

        binding = ActivityClassInstanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();

                int course_id = 0;
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    course_id = bundle.getInt("course_id");
                }

                createAddClassPopupDialog(course_id);
            }
        });

        // Initialize
        recyclerClassView = (RecyclerView) findViewById(R.id.recyclerClassesView);
        classYogaList = new ArrayList<>();
        classYogaListEdit = new ArrayList<>();
        db = new ClassDatabaseHandle(this);

        // Show list class
        recyclerClassView.setHasFixedSize(true);
        recyclerClassView.setLayoutManager(new LinearLayoutManager(this));


        classYogaList = db.getAllClassYoga(course_id);

        for (ClassYoga c : classYogaList) {
            ClassYoga classYoga = new ClassYoga();
            classYoga.setId(c.getId());
            classYoga.setCourse_id(c.getCourse_id());
            classYoga.setClassDate(c.getClassDate());
            classYoga.setTeacherName(c.getTeacherName());
            classYoga.setComment(c.getComment());

            classYogaListEdit.add(classYoga);
        }

        classRecyclerViewAdapter = new ClassRecyclerViewAdapter(this, classYogaListEdit);
        recyclerClassView.setAdapter(classRecyclerViewAdapter);
        classRecyclerViewAdapter.notifyDataSetChanged();

    }

    private void createAddClassPopupDialog(int course_id) {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.add_class, null);

        addDateClass = (EditText) view.findViewById(R.id.addDateClass);
        addTeacherClass = (EditText) view.findViewById(R.id.addTeacherClass);
        addCommentClass = (EditText) view.findViewById(R.id.addCommentClass);
        saveButtonClass = (Button) view.findViewById(R.id.saveButtonClass);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        addDateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ClassInstanceActivity.this, (view, year1, month1, dayOfMonth) -> {
                    month1 = month1 + 1;
                    String date = dayOfMonth + "/" + month1 + "/" + year1;
                    addDateClass.setText(date);

                }, year, month, day);
                datePickerDialog.show();

            }
        });


        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();


        saveButtonClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateClass = addDateClass.getText().toString().trim();
                String teacherClass = addTeacherClass.getText().toString();

                // Validate user input
                boolean check = validateInfo(dateClass, teacherClass);
                if (check) {
                    saveClassToDB(v, course_id);
                    Toast.makeText(ClassInstanceActivity.this, "Saved Class to DB!", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(ClassInstanceActivity.this, "The date matches the day of the week!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ClassInstanceActivity.this, "Sorry, Check the Information Class", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(ClassInstanceActivity.this, "The date does not match the day of the week!", Toast.LENGTH_SHORT).show();
                }

            }

            private Boolean validateInfo(String dateClass, String teacherClass) {
                if (dateClass.length() == 0) {
                    addDateClass.requestFocus();
                    addDateClass.setError("Please enter date");
                    return false;
                } else if (teacherClass.length() == 0) {
                    addTeacherClass.requestFocus();
                    addTeacherClass.setError("Please enter teacher name");
                    return false;
                }
                return true;
            }

//            public Boolean checkDay() {
//                Course course = new Course();
//                if (course.getId() == course_id) {
//                    String day = course.getDayYoga();
//                    String newAddDateClass = addDateClass.getText().toString().trim();
//                    boolean checkDateVsDay = DateUtil.isDateMatchingDayOfWeek("11/11/2024", "Monday");
//                    if (checkDateVsDay) return true;
//                }
//                return false;
//            }

        });
    }

    private void saveClassToDB(View v, int course_id) {
        ClassYoga classYoga = new ClassYoga();

        String newAddDateClass = addDateClass.getText().toString().trim();
        String newAddTeacherClass = addTeacherClass.getText().toString();
        String newAddCommentClass = addCommentClass.getText().toString();

        classYoga.setCourse_id(course_id);
        classYoga.setClassDate(newAddDateClass);
        classYoga.setTeacherName(newAddTeacherClass);
        classYoga.setComment(newAddCommentClass);

        // Save to db
        db.addClass(classYoga);
        Snackbar.make(v, "Class saved", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(ClassInstanceActivity.this, MainActivity.class));
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}