package com.gohool.firstlook.yogaapp.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.yogaapp.Data.ClassDatabaseHandle;
import com.gohool.firstlook.yogaapp.Data.CourseDatabaseHandle;
import com.gohool.firstlook.yogaapp.Model.ClassYoga;
import com.gohool.firstlook.yogaapp.Model.Course;
import com.gohool.firstlook.yogaapp.R;
import com.gohool.firstlook.yogaapp.Util.DateUtil;

import java.util.Calendar;
import java.util.List;

public class SearchClassRecyclerViewAdapter extends RecyclerView.Adapter<SearchClassRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ClassYoga> classYogaList;
    private LayoutInflater inflater;

    public SearchClassRecyclerViewAdapter(Context context, List<ClassYoga> classYogaList) {
        this.context = context;
        this.classYogaList = classYogaList;
    }

    @NonNull
    @Override
    public SearchClassRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_class_list, parent, false);
        return new SearchClassRecyclerViewAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchClassRecyclerViewAdapter.ViewHolder holder, int position) {
        ClassYoga classYoga = classYogaList.get(position);

        holder.dateSearchClass.setText(classYoga.getClassDate());
        holder.teacherSearchClass.setText(classYoga.getTeacherName());
        holder.commentSearchClass.setText(classYoga.getComment());

    }

    @Override
    public int getItemCount() {
        return classYogaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView dateSearchClass;
        private TextView teacherSearchClass;
        private TextView commentSearchClass;


        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;

            dateSearchClass = (TextView) view.findViewById(R.id.dateSearchClass);
            teacherSearchClass = (TextView) view.findViewById(R.id.teacherSearchClass);
            commentSearchClass = (TextView) view.findViewById(R.id.commentSearchClass);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ClassYoga classYoga = classYogaList.get(position);
        }
    }
}
