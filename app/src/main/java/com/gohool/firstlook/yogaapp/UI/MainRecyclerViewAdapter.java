package com.gohool.firstlook.yogaapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.yogaapp.Model.Course;
import com.gohool.firstlook.yogaapp.R;

import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>
{

    private Context context;
    private List<Course> courseList;
    private LayoutInflater inflater;


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
//        holder.typeYoga.setText(String.valueOf(course.getTypeYoga()));
//        holder.dayYoga.setText(String.valueOf(course.getDayYoga()));
        holder.priceYoga.setText(String.valueOf(course.getPriceYoga()));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView typeYoga;
        public TextView dayYoga;
        public TextView priceYoga;
        public Button editButton;
        public Button deleteButton;
        public Button listClasses;

        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;

            typeYoga = (TextView) view.findViewById(R.id.typeYoga);
            dayYoga = (TextView) view.findViewById(R.id.dayYoga);
            priceYoga = (TextView) view.findViewById(R.id.priceYoga);
            editButton = (Button) view.findViewById(R.id.editButton);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);
            listClasses = (Button) view.findViewById(R.id.listClasses);

        }

        @Override
        public void onClick(View v) {

        }
    }

}
