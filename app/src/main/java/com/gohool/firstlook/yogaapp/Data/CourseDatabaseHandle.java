package com.gohool.firstlook.yogaapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gohool.firstlook.yogaapp.Model.Course;
import com.gohool.firstlook.yogaapp.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class CourseDatabaseHandle extends SQLiteOpenHelper {

    private Context ctx;

    public CourseDatabaseHandle(@Nullable Context context) {
        super(context, Constants.COURSE_DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_YOGA_COURSE_TABLE = "CREATE TABLE " + Constants.COURSE_TABLE_NAME + "("
                + Constants.COURSE_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.COURSE_KEY_TYPE_YOGA + " TEXT,"
                + Constants.COURSE_KEY_DAY_YOGA + " TEXT,"
                + Constants.COURSE_KEY_PRICE_YOGA + " TEXT,"
                + Constants.COURSE_KEY_TIME_YOGA + " TEXT,"
                + Constants.COURSE_KEY_CAPACITY_YOGA + " TEXT,"
                + Constants.COURSE_KEY_DURATION_YOGA + " TEXT,"
                + Constants.COURSE_KEY_DESCRIPTION_YOGA + " TEXT"
                + ")";

        db.execSQL(CREATE_YOGA_COURSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.COURSE_TABLE_NAME);
        onCreate(db);
    }

    /*
        CRUD Operations (Create, Read, Update, Delete)
    */

    // Add course
    public void addCourse(Course course)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COURSE_KEY_TYPE_YOGA, course.getTypeYoga());
        values.put(Constants.COURSE_KEY_DAY_YOGA, course.getDayYoga());
        values.put(Constants.COURSE_KEY_PRICE_YOGA, course.getPriceYoga());
        values.put(Constants.COURSE_KEY_TIME_YOGA, course.getTimeYoga());
        values.put(Constants.COURSE_KEY_CAPACITY_YOGA, course.getCapacityYoga());
        values.put(Constants.COURSE_KEY_DURATION_YOGA, course.getDurationYoga());
        values.put(Constants.COURSE_KEY_DESCRIPTION_YOGA, course.getDescriptionYoga());

        // Insert the row
        db.insert(Constants.COURSE_TABLE_NAME, null, values);

        Log.d("Saved!!", "Saved to DB");
        Log.d("Type: ", course.getTypeYoga());
        Log.d("Day: ", course.getDayYoga());
        Log.d("Price: ", course.getPriceYoga());
        Log.d("Time: ", course.getTimeYoga());
        Log.d("Capacity: ", course.getCapacityYoga());
        Log.d("Duration: ", course.getDurationYoga());
        Log.d("Description: ", course.getDescriptionYoga());
    }

    // Get a course
    public Course getCourse(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constants.COURSE_TABLE_NAME, new String[] {
                        Constants.COURSE_KEY_ID,
                        Constants.COURSE_KEY_TYPE_YOGA,
                        Constants.COURSE_KEY_DAY_YOGA,
                        Constants.COURSE_KEY_PRICE_YOGA,
                        Constants.COURSE_KEY_TIME_YOGA,
                        Constants.COURSE_KEY_CAPACITY_YOGA,
                        Constants.COURSE_KEY_DURATION_YOGA,
                        Constants.COURSE_KEY_DESCRIPTION_YOGA},
                Constants.COURSE_KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        Course course = new Course();
        if (cursor != null)
        {
            cursor.moveToFirst();

            // Get course
            int idIndex = cursor.getColumnIndex(Constants.COURSE_KEY_ID);
            int typeYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_TYPE_YOGA);
            int dayYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_DAY_YOGA);
            int priceYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_PRICE_YOGA);
            int timeYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_TIME_YOGA);
            int capacityYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_CAPACITY_YOGA);
            int durationYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_DURATION_YOGA);
            int descriptionYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_DESCRIPTION_YOGA);
            if (idIndex >= 0 && typeYogaIndex >= 0 && priceYogaIndex >= 0 && timeYogaIndex >= 0
                && capacityYogaIndex >= 0 && durationYogaIndex >= 0 && descriptionYogaIndex >= 0)
            {
                course.setId(Integer.parseInt(cursor.getString(idIndex)));
                course.setTypeYoga(cursor.getString(typeYogaIndex));
                course.setDayYoga(cursor.getString(dayYogaIndex));
                course.setPriceYoga(cursor.getString(priceYogaIndex));
                course.setTimeYoga(cursor.getString(timeYogaIndex));
                course.setCapacityYoga(cursor.getString(capacityYogaIndex));
                course.setDurationYoga(cursor.getString(durationYogaIndex));
                course.setDescriptionYoga(cursor.getString(descriptionYogaIndex));
            }
        }
        cursor.close();
        return course;
    }

    // Get all courses
    public List<Course> getAllCourses()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Course> courseList = new ArrayList<>();

        Cursor cursor = db.query(Constants.COURSE_TABLE_NAME, new String[] {
                        Constants.COURSE_KEY_ID,
                        Constants.COURSE_KEY_TYPE_YOGA,
                        Constants.COURSE_KEY_DAY_YOGA,
                        Constants.COURSE_KEY_PRICE_YOGA,
                        Constants.COURSE_KEY_TIME_YOGA,
                        Constants.COURSE_KEY_CAPACITY_YOGA,
                        Constants.COURSE_KEY_DURATION_YOGA,
                        Constants.COURSE_KEY_DESCRIPTION_YOGA},
                null, null, null, null, Constants.COURSE_KEY_ID + " ASC", null); // ASC: ascending, DESC: descending

//        Cursor cursor = db.query(Constants.COURSE_TABLE_NAME, null,
//                null, null, null, null, null, null); // ASC: ascending, DESC: descending

        if (cursor.moveToFirst())
        {
            do {
                Course course = new Course();
                int idIndex = cursor.getColumnIndex(Constants.COURSE_KEY_ID);
                int typeYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_TYPE_YOGA);
                int dayYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_DAY_YOGA);
                int priceYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_PRICE_YOGA);
                int timeYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_TIME_YOGA);
                int capacityYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_CAPACITY_YOGA);
                int durationYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_DURATION_YOGA);
                int descriptionYogaIndex = cursor.getColumnIndex(Constants.COURSE_KEY_DESCRIPTION_YOGA);
                if (idIndex >= 0 && typeYogaIndex >= 0 && priceYogaIndex >= 0 && timeYogaIndex >= 0
                        && capacityYogaIndex >= 0 && durationYogaIndex >= 0 && descriptionYogaIndex >= 0)
                {
                    course.setId(Integer.parseInt(cursor.getString(idIndex)));
                    course.setTypeYoga(cursor.getString(typeYogaIndex));
                    course.setDayYoga(cursor.getString(dayYogaIndex));
                    course.setPriceYoga(cursor.getString(priceYogaIndex));
                    course.setTimeYoga(cursor.getString(timeYogaIndex));
                    course.setCapacityYoga(cursor.getString(capacityYogaIndex));
                    course.setDurationYoga(cursor.getString(durationYogaIndex));
                    course.setDescriptionYoga(cursor.getString(descriptionYogaIndex));
                }

                courseList.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseList;
    }

    // Update course
    public int updateCourse(Course course)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COURSE_KEY_TYPE_YOGA, course.getTypeYoga());
        values.put(Constants.COURSE_KEY_DAY_YOGA, course.getDayYoga());
        values.put(Constants.COURSE_KEY_PRICE_YOGA, course.getPriceYoga());
        values.put(Constants.COURSE_KEY_TIME_YOGA, course.getTimeYoga());
        values.put(Constants.COURSE_KEY_CAPACITY_YOGA, course.getCapacityYoga());
        values.put(Constants.COURSE_KEY_DURATION_YOGA, course.getDurationYoga());
        values.put(Constants.COURSE_KEY_DESCRIPTION_YOGA, course.getDescriptionYoga());

        // update row
        return db.update(Constants.COURSE_TABLE_NAME, values, Constants.COURSE_KEY_ID + "=?",
                new String[] {String.valueOf(course.getId())}); // return ID of row updated
    }

    // Delete course
    public void deleteCourse(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.COURSE_TABLE_NAME, Constants.COURSE_KEY_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    // Get count
    public int getCourseCount()
    {
        String countQuery = "SELECT * FROM " + Constants.COURSE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null); // rawQuery: Runs the provided SQL and returns a "Cursor" over the result set (multiple results).
        cursor.close();
        return cursor.getCount();
    }

    // Detele table
    public void deleteDataCourseTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.COURSE_TABLE_NAME, null, null);
    }

}
