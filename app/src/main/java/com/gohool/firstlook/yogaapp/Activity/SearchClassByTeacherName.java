package com.gohool.firstlook.yogaapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
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
import com.gohool.firstlook.yogaapp.UI.MainRecyclerViewAdapter;
import com.gohool.firstlook.yogaapp.UI.SearchClassRecyclerViewAdapter;
import com.gohool.firstlook.yogaapp.databinding.ActivityMainBinding;
import com.gohool.firstlook.yogaapp.databinding.ActivitySearchClassByTeacherNameBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchClassByTeacherName extends AppCompatActivity {

    private ClassDatabaseHandle dbClass;
    private EditText searchBarClass;
    private Button searchClassButton;
    private Button resetSearchButton;
    private Button backToMainButton;

    private RecyclerView recyclerSearchClassesView;
    private SearchClassRecyclerViewAdapter searchClassRecyclerViewAdapter;
    private List<ClassYoga> classYogaList;
    private List<ClassYoga> classYogaListEdit;

    private AppBarConfiguration appBarConfiguration;
    private ActivitySearchClassByTeacherNameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_class_by_teacher_name);

        binding = ActivitySearchClassByTeacherNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        // Initialize
        recyclerSearchClassesView = (RecyclerView) findViewById(R.id.recyclerSearchClassesView);
        classYogaList = new ArrayList<>();
        classYogaListEdit = new ArrayList<>();
        dbClass = new ClassDatabaseHandle(this);
        searchBarClass = (EditText) findViewById(R.id.searchBarClass);
        searchClassButton = (Button) findViewById(R.id.searchClassButton);
        resetSearchButton = (Button) findViewById(R.id.resetSearchButton);
        backToMainButton = (Button) findViewById(R.id.backButton);

        // Show list courses
        recyclerSearchClassesView.setHasFixedSize(true);
        recyclerSearchClassesView.setLayoutManager(new LinearLayoutManager(this));

        searchClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacherName = searchBarClass.getText().toString().trim();
                classYogaList = dbClass.searchClassYoga(teacherName);
                if (classYogaList != null) {
                    for (ClassYoga c : classYogaList) {
                        ClassYoga classYoga = new ClassYoga();
                        classYoga.setId(c.getId());
                        classYoga.setCourse_id(c.getCourse_id());
                        classYoga.setClassDate(c.getClassDate());
                        classYoga.setTeacherName(c.getTeacherName());
                        classYoga.setComment(c.getComment());

                        classYogaListEdit.add(classYoga);
                    }
                }
                searchClassRecyclerViewAdapter = new SearchClassRecyclerViewAdapter(SearchClassByTeacherName.this, classYogaListEdit);
                recyclerSearchClassesView.setAdapter(searchClassRecyclerViewAdapter);
                searchClassRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        resetSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchClassByTeacherName.this, SearchClassByTeacherName.class));
            }
        });

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchClassByTeacherName.this, MainActivity.class));
            }
        });


    }
}