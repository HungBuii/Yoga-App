package com.gohool.firstlook.yogaapp.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.yogaapp.Activity.DetailCourseActivity;
import com.gohool.firstlook.yogaapp.Activity.MainActivity;
import com.gohool.firstlook.yogaapp.Data.CourseDatabaseHandle;
import com.gohool.firstlook.yogaapp.Model.Course;
import com.gohool.firstlook.yogaapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>
{

    private Context context;
    private List<Course> courseList;
    private LayoutInflater inflater;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    public MainRecyclerViewAdapter(Context context, List<Course> courseLists) {
        this.context = context;
        this.courseList = courseLists;
    }

    @NonNull
    @Override
    public MainRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_list, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewAdapter.ViewHolder holder, int position) {
        Course course = courseList.get(position);

        holder.typeYoga.setText(course.getTypeYoga());
        holder.dayYoga.setText(course.getDayYoga());
        holder.priceYoga.setText(String.valueOf(course.getPriceYoga()));
        holder.timeYoga.setText(course.getTimeYoga());
        holder.capacityYoga.setText(course.getCapacityYoga());
        holder.durationYoga.setText(course.getDurationYoga());
        holder.descriptionYoga.setText(course.getDescriptionYoga());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView typeYoga;
        private TextView dayYoga;
        private TextView priceYoga;
        private TextView timeYoga;
        private TextView capacityYoga;
        private TextView durationYoga;
        private TextView descriptionYoga;

        private Button editButton;
        private Button deleteButton;
        private Button listClasses;



        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;

            typeYoga = (TextView) view.findViewById(R.id.typeYoga);
            dayYoga = (TextView) view.findViewById(R.id.dayYoga);
            priceYoga = (TextView) view.findViewById(R.id.priceYoga);
            timeYoga = (TextView) view.findViewById(R.id.timeYoga);
            capacityYoga = (TextView) view.findViewById(R.id.capacityYoga);
            durationYoga = (TextView) view.findViewById(R.id.durationYoga);
            descriptionYoga = (TextView) view.findViewById(R.id.descriptionYoga);

            editButton = (Button) view.findViewById(R.id.editButton);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);
            listClasses = (Button) view.findViewById(R.id.listClasses);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            listClasses.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Go to next screen => DetailsActivity
                    int position = getAdapterPosition();
                    Course course = courseList.get(position);
                    Intent intent = new Intent(context, DetailCourseActivity.class);

                    intent.putExtra("detailTypeYoga", course.getTypeYoga());
                    intent.putExtra("detailDayYoga", course.getDayYoga());
                    intent.putExtra("detailPriceYoga", course.getPriceYoga());
                    intent.putExtra("detailTimeYoga", course.getTimeYoga());
                    intent.putExtra("detailCapacityYoga", course.getCapacityYoga());
                    intent.putExtra("detailDurationYoga", course.getDurationYoga());
                    intent.putExtra("detailDescriptionYoga", course.getDescriptionYoga());

                    context.startActivity(intent);
                }
            });

        }



        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Course course = courseList.get(position);

            if (v.getId() == R.id.editButton) {
                editButton(course);
            }

            if (v.getId() == R.id.deleteButton) {
                deleteButton(course.getId());
            }
        }

        public void editButton(Course course)
        {
            alertDialogBuilder = new AlertDialog.Builder(context);
            MainActivity mainActivity = new MainActivity();

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.edit_course, null);

            final TextView title = (TextView) view.findViewById(R.id.titleEditYogaCourse);
            final Spinner editSpinnerTypeYoga = (Spinner) view.findViewById(R.id.editSpinnerTypeYoga);
            ArrayAdapter adapter2;
            final Spinner editSpinnerDay = (Spinner) view.findViewById(R.id.editSpinnerDay);
            ArrayAdapter adapter;
            final EditText editPriceYoga = (EditText) view.findViewById(R.id.editPriceYoga);
            final Spinner editSpinnerTime = (Spinner) view.findViewById(R.id.editSpinnerTime);
            ArrayAdapter adapter1;
            final EditText editCapacityYoga = (EditText) view.findViewById(R.id.editCapacityYoga);
            final EditText editDurationYoga = (EditText) view.findViewById(R.id.editDurationYoga);
            final EditText editDescriptionYoga = (EditText) view.findViewById(R.id.editDescriptionYoga);


            title.setText("Edit Course Yoga");
            editPriceYoga.setText(course.getPriceYoga());
            editCapacityYoga.setText(course.getCapacityYoga());
            editDurationYoga.setText(course.getDurationYoga());
            editDescriptionYoga.setText(course.getDescriptionYoga());

            adapter2 = ArrayAdapter.createFromResource(context, R.array.spinnerTypeYoga, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editSpinnerTypeYoga.setAdapter(adapter2);

            adapter = ArrayAdapter.createFromResource(context, R.array.spinnerDay, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editSpinnerDay.setAdapter(adapter);

            adapter1 = ArrayAdapter.createFromResource(context, R.array.spinnerTime, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editSpinnerTime.setAdapter(adapter1);

            Button saveButton = (Button) view.findViewById(R.id.saveEditButton);


            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CourseDatabaseHandle db = new CourseDatabaseHandle(context);

                    // Update course
                    course.setTypeYoga(editSpinnerTypeYoga.getSelectedItem().toString());
                    course.setDayYoga(editSpinnerDay.getSelectedItem().toString());
                    course.setPriceYoga(editPriceYoga.getText().toString());
                    course.setTimeYoga(editSpinnerTime.getSelectedItem().toString());
                    course.setCapacityYoga(editCapacityYoga.getText().toString());
                    course.setDurationYoga(editDurationYoga.getText().toString());
                    course.setDescriptionYoga(editDescriptionYoga.getText().toString());

                    String priceYoga = editPriceYoga.getText().toString();
                    String capacityYoga = editCapacityYoga.getText().toString();
                    String durationYoga = editDurationYoga.getText().toString();
                    String descriptionYoga = editDescriptionYoga.getText().toString();

                    boolean check = validateInfo(priceYoga,
                            capacityYoga, durationYoga, descriptionYoga);

                    if (check) {
                        db.updateCourse(course);
                        notifyItemChanged(getAdapterPosition(), course);
                    }
                    else {
                        Toast.makeText(context, "Sorry, Check the Information", Toast.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                }

                private Boolean validateInfo(String priceYoga, String capacityYoga, String durationYoga, String descriptionYoga)
                {
                    if (priceYoga.length() == 0)
                    {
                        editPriceYoga.requestFocus();
                        editPriceYoga.setError("Please enter price");
                        return false;
                    }
                    else if (capacityYoga.length() == 0) {
                        editCapacityYoga.requestFocus();
                        editCapacityYoga.setError("Please enter capacity");
                        return false;
                    }
                    else if (durationYoga.length() == 0) {
                        editDurationYoga.requestFocus();
                        editDurationYoga.setError("Please enter duration");
                        return false;
                    }
                    else if (descriptionYoga.length() == 0) {
                        editDescriptionYoga.requestFocus();
                        editDescriptionYoga.setError("Please enter description");
                        return false;
                    }
                    return true;
                }

            });
        }

        public void deleteButton(int id)
        {
            alertDialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirm_dialog, null);

            Button noButton = (Button) view.findViewById(R.id.noButton);
            Button yesButton = (Button) view.findViewById(R.id.yesButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CourseDatabaseHandle db = new CourseDatabaseHandle(context);
                    db.deleteCourse(id);
                    courseList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();

                }
            });
        }

    }

}
