package com.gohool.firstlook.yogaapp.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gohool.firstlook.yogaapp.R;

public class DetailCourseActivity extends AppCompatActivity {

    private TextView detailTypeYoga;
    private TextView detailDayYoga;
    private TextView detailPriceYoga;
    private TextView detailTimeYoga;
    private TextView detailCapacityYoga;
    private TextView detailDurationYoga;
    private TextView detailDescriptionYoga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_course);

        detailTypeYoga = (TextView) findViewById(R.id.detailTypeYoga);
        detailDayYoga = (TextView) findViewById(R.id.detailDayYoga);
        detailPriceYoga = (TextView) findViewById(R.id.detailPriceYoga);
        detailTimeYoga = (TextView) findViewById(R.id.detailTimeYoga);
        detailCapacityYoga = (TextView) findViewById(R.id.detailCapacityYoga);
        detailDurationYoga = (TextView) findViewById(R.id.detailDurationYoga);
        detailDescriptionYoga = (TextView) findViewById(R.id.detailDescriptionYoga);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            String typeYoga = bundle.getString("detailTypeYoga");
            String dayYoga = bundle.getString("detailDayYoga");
            String priceYoga = bundle.getString("detailPriceYoga");
            String timeYoga = bundle.getString("detailTimeYoga");
            String capacityYoga = bundle.getString("detailCapacityYoga");
            String durationYoga = bundle.getString("detailDurationYoga");
            String descriptionYoga = bundle.getString("detailDescriptionYoga");

            detailTypeYoga.setText("Type of Yoga: " + typeYoga);
            detailDayYoga.setText("Day: " + dayYoga);
            detailPriceYoga.setText("Price: " + priceYoga);
            detailTimeYoga.setText("Time: " + timeYoga);
            detailCapacityYoga.setText("Capacity: " + capacityYoga);
            detailDurationYoga.setText("Duration: " + durationYoga);
            detailDescriptionYoga.setText("Description: " + descriptionYoga);



        }
    }
}