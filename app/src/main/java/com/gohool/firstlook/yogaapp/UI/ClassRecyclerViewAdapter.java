package com.gohool.firstlook.yogaapp.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.yogaapp.Activity.ClassInstanceActivity;
import com.gohool.firstlook.yogaapp.Activity.DetailCourseActivity;
import com.gohool.firstlook.yogaapp.Data.ClassDatabaseHandle;
import com.gohool.firstlook.yogaapp.Data.CourseDatabaseHandle;
import com.gohool.firstlook.yogaapp.Model.ClassYoga;
import com.gohool.firstlook.yogaapp.Model.Course;
import com.gohool.firstlook.yogaapp.R;

import java.util.Calendar;
import java.util.List;

public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ClassYoga> classYogaList;
    private LayoutInflater inflater;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    public ClassRecyclerViewAdapter(Context context, List<ClassYoga> classYogaList) {
        this.context = context;
        this.classYogaList = classYogaList;
    }

    @NonNull
    @Override
    public ClassRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list, parent, false);
        return new ClassRecyclerViewAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassRecyclerViewAdapter.ViewHolder holder, int position) {
        ClassYoga classYoga = classYogaList.get(position);

        holder.dateClass.setText(classYoga.getClassDate());
        holder.teacherClass.setText(classYoga.getTeacherName());
        holder.commentClass.setText(classYoga.getComment());

    }

    @Override
    public int getItemCount() {
        return classYogaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView dateClass;
        private TextView teacherClass;
        private TextView commentClass;

        private Button editButtonClass;
        private Button deleteButtonClass;


        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;

            dateClass = (TextView) view.findViewById(R.id.dateClass);
            teacherClass = (TextView) view.findViewById(R.id.teacherClass);
            commentClass = (TextView) view.findViewById(R.id.commentClass);


            editButtonClass = (Button) view.findViewById(R.id.editButtonClass);
            deleteButtonClass = (Button) view.findViewById(R.id.deleteButtonClass);

            editButtonClass.setOnClickListener(this);
            deleteButtonClass.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Go to next screen => DetailsClassYogaActivity
//                    int position = getAdapterPosition();
//                    ClassYoga classYoga = classYogaList.get(position);
//                    Intent intent = new Intent(context, DetailCourseActivity.class);
//
//                    intent.putExtra("detailTypeYogaInClass", classYoga.getCourse_id());
//                    intent.putExtra("detailDateClass", classYoga.getClassDate());
//                    intent.putExtra("detailTeacherClass", classYoga.getTeacherName());
//
//                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ClassYoga classYoga = classYogaList.get(position);

            if (v.getId() == R.id.editButtonClass) {
                editButtonClass(classYoga);
            }

            if (v.getId() == R.id.deleteButtonClass) {
                deleteButtonClass(classYoga.getId());
            }
        }

        public void editButtonClass(ClassYoga classYoga) {
            alertDialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.edit_class, null);

            final TextView title = (TextView) view.findViewById(R.id.titleYogaClass);
            final TextView editDateClass = (TextView) view.findViewById(R.id.editDateClass);
            final EditText editTeacherClass = (EditText) view.findViewById(R.id.editTeacherClass);
            final EditText editCommentClass = (EditText) view.findViewById(R.id.editCommentClass);

            title.setText("Edit Class Yoga");
            final Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            editDateClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, month1, dayOfMonth) -> {
                        month1 = month1 + 1;
                        String date = dayOfMonth + "/" + month1 + "/" + year1;
                        editDateClass.setText(date);

                    }, year, month, day);
                    datePickerDialog.show();

                }
            });
            editTeacherClass.setText(classYoga.getTeacherName());
            editCommentClass.setText(classYoga.getComment());

            Button saveButtonClass = (Button) view.findViewById(R.id.saveEditButtonClass);


            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            saveButtonClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClassDatabaseHandle db = new ClassDatabaseHandle(context);

                    // Update class
                    classYoga.setClassDate(editDateClass.getText().toString().trim());
                    classYoga.setTeacherName(editTeacherClass.getText().toString());
                    classYoga.setComment(editCommentClass.getText().toString());


                    String dateClass = editDateClass.getText().toString().trim();
                    String teacherClass = editTeacherClass.getText().toString();

                    boolean check = validateInfo(dateClass, teacherClass);

                    if (check) {
                        db.updateClassYoga(classYoga);
                        notifyItemChanged(getAdapterPosition(), classYoga);
                    } else {
                        Toast.makeText(context, "Sorry, Check the Information Class", Toast.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                }

                private Boolean validateInfo(String dateClass, String teacherClass) {
                    if (dateClass.length() == 0) {
                        editDateClass.requestFocus();
                        editDateClass.setError("Please enter price");
                        return false;
                    } else if (teacherClass.length() == 0) {
                        editTeacherClass.requestFocus();
                        editTeacherClass.setError("Please enter capacity");
                        return false;

                    }
                    return true;
                }
            });
        }
        public void deleteButtonClass(int id) {
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
                    ClassDatabaseHandle db = new ClassDatabaseHandle(context);
                    db.deleteClassYoga(id);
                    classYogaList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();

                }
            });
        }
    }
}

