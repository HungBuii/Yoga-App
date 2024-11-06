package com.gohool.firstlook.yogaapp.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        private Button editButton;
        private Button deleteButton;
        private Button listClasses;



        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;

            typeYoga = (TextView) view.findViewById(R.id.typeYoga);
            dayYoga = (TextView) view.findViewById(R.id.dayYoga);
            priceYoga = (TextView) view.findViewById(R.id.priceYoga);
            editButton = (Button) view.findViewById(R.id.editButton);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);
            listClasses = (Button) view.findViewById(R.id.listClasses);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            listClasses.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
            final EditText editTypeYoga = (EditText) view.findViewById(R.id.editTypeYoga);
            final Spinner editSpinnerDay = (Spinner) view.findViewById(R.id.editSpinnerDay);
            ArrayAdapter adapter;
            final EditText editPriceYoga = (EditText) view.findViewById(R.id.editPriceYoga);

            title.setText("Edit Course Yoga");
            editTypeYoga.setText(course.getTypeYoga());
            editPriceYoga.setText(course.getPriceYoga());

            adapter = ArrayAdapter.createFromResource(context, R.array.spinnerDay, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editSpinnerDay.setAdapter(adapter);

            Button saveButton = (Button) view.findViewById(R.id.saveEditButton);


            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CourseDatabaseHandle db = new CourseDatabaseHandle(context);

                    // Update course
                    course.setTypeYoga(editTypeYoga.getText().toString());
                    course.setDayYoga(editSpinnerDay.getSelectedItem().toString());
                    course.setPriceYoga(editPriceYoga.getText().toString());

                    if (!editTypeYoga.getText().toString().isEmpty() &&
                            !editPriceYoga.getText().toString().isEmpty()) {
                        db.updateCourse(course);
                        notifyItemChanged(getAdapterPosition(), course);
                    }
                    else {
                        Snackbar.make(view, "Added Successfully!", Snackbar.LENGTH_LONG).show();
                    }

                    dialog.dismiss();
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
