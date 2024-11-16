package com.gohool.firstlook.yogaapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gohool.firstlook.yogaapp.Model.ClassYoga;
import com.gohool.firstlook.yogaapp.Model.Course;
import com.gohool.firstlook.yogaapp.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ClassDatabaseHandle extends SQLiteOpenHelper {

    private final Context ctx;

    public ClassDatabaseHandle(@Nullable Context context) {
        super(context, Constants.CLASS_INSTANCE_DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLASS_INSTANCE_TABLE = "CREATE TABLE " + Constants.CLASS_INSTANCE_TABLE_NAME + "(" +
                Constants.CLASS_INSTANCE_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.CLASS_INSTANCE_KEY_COURSE_ID + " INTEGER NOT NULL,"
                + Constants.CLASS_INSTANCE_KEY_TEACHER_NAME + " TEXT,"
                + Constants.CLASS_INSTANCE_KEY_CLASS_DATE + " TEXT,"
                + Constants.CLASS_INSTANCE_KEY_COMMENT + " TEXT,"
//                + "FOREIGN KEY(" + Constants.CLASS_INSTANCE_KEY_COURSE_ID + ") REFERENCES " + Constants.COURSE_TABLE_NAME + "(" + Constants.COURSE_KEY_ID + ")" + ")";
                + "FOREIGN KEY(" + Constants.CLASS_INSTANCE_KEY_COURSE_ID + ") REFERENCES " + Constants.COURSE_TABLE_NAME + "(" + Constants.COURSE_KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_CLASS_INSTANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.CLASS_INSTANCE_TABLE_NAME);
        onCreate(db);
    }

    /*
        CRUD Operations (Create, Read, Update, Delete)
    */

    // Add class
    public void addClass(ClassYoga classYoga)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.CLASS_INSTANCE_KEY_COURSE_ID, classYoga.getCourse_id());
        values.put(Constants.CLASS_INSTANCE_KEY_TEACHER_NAME, classYoga.getTeacherName());
        values.put(Constants.CLASS_INSTANCE_KEY_CLASS_DATE, classYoga.getClassDate());
        values.put(Constants.CLASS_INSTANCE_KEY_COMMENT, classYoga.getComment());

        // Insert the row
        db.insert(Constants.CLASS_INSTANCE_TABLE_NAME, null, values);

        Log.d("Saved!!", "Saved to DB");
        Log.d("Teacher Name: ", classYoga.getTeacherName());
        Log.d("Class Date: ", classYoga.getClassDate());
        Log.d("Comment: ", classYoga.getComment());

        db.close();
    }

    // Get a class
//    public ClassYoga getClassYoga(int id)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.query(Constants.CLASS_INSTANCE_TABLE_NAME, new String[] {
//                        Constants.CLASS_INSTANCE_KEY_ID,
//                        Constants.CLASS_INSTANCE_KEY_COURSE_ID,
//                        Constants.CLASS_INSTANCE_KEY_TEACHER_NAME,
//                        Constants.CLASS_INSTANCE_KEY_CLASS_DATE,
//                        Constants.CLASS_INSTANCE_KEY_COMMENT},
//                Constants.CLASS_INSTANCE_KEY_ID + "=?",
//                new String[] {String.valueOf(id)}, null, null, null, null);
//
//        ClassYoga classYoga = new ClassYoga();
//        if (cursor != null)
//        {
//            cursor.moveToFirst();
//
//            // Get class
//            int idIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_ID);
//            int courseIDIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_COURSE_ID);
//            int teacherNameIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_TEACHER_NAME);
//            int dateClassIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_CLASS_DATE);
//            int commentClassIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_COMMENT);
//
//            if (idIndex >= 0 && courseIDIndex >= 0 && teacherNameIndex >= 0 && dateClassIndex >= 0
//                    && commentClassIndex >= 0 )
//            {
//                classYoga.setId(Integer.parseInt(cursor.getString(idIndex)));
//                classYoga.setCourse_id(Integer.parseInt(cursor.getString(courseIDIndex)));
//                classYoga.setTeacherName(cursor.getString(teacherNameIndex));
//                classYoga.setClassDate(cursor.getString(dateClassIndex));
//                classYoga.setComment(cursor.getString(commentClassIndex));
//            }
//        }
//        cursor.close();
//        return classYoga;
//    }

    // Get all class
    public List<ClassYoga> getAllClassYoga(int course_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        List<ClassYoga> classYogaList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.CLASS_INSTANCE_TABLE_NAME +
                " WHERE " + Constants.CLASS_INSTANCE_KEY_COURSE_ID + " = ?", new String[] {String.valueOf(course_id)}); // ASC: ascending, DESC: descending

//        Cursor cursor = db.query(Constants.COURSE_TABLE_NAME, null,
//                null, null, null, null, null, null); // ASC: ascending, DESC: descending

        if (cursor.moveToFirst())
        {
            do {
                ClassYoga classYoga = new ClassYoga();
                int idIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_ID);
                int courseIDIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_COURSE_ID);
                int teacherNameIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_TEACHER_NAME);
                int dateClassIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_CLASS_DATE);
                int commentClassIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_COMMENT);
                if (idIndex >= 0
                        && courseIDIndex >= 0
                        && teacherNameIndex >= 0 && dateClassIndex >= 0
                        && commentClassIndex >= 0 )
                {
                    classYoga.setId(Integer.parseInt(cursor.getString(idIndex)));
                    classYoga.setCourse_id(Integer.parseInt(cursor.getString(courseIDIndex)));
                    classYoga.setTeacherName(cursor.getString(teacherNameIndex));
                    classYoga.setClassDate(cursor.getString(dateClassIndex));
                    classYoga.setComment(cursor.getString(commentClassIndex));
                }

                classYogaList.add(classYoga);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classYogaList;
    }

    // Update class
    public int updateClassYoga(ClassYoga classYoga)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.CLASS_INSTANCE_KEY_COURSE_ID, classYoga.getCourse_id());
        values.put(Constants.CLASS_INSTANCE_KEY_TEACHER_NAME, classYoga.getTeacherName());
        values.put(Constants.CLASS_INSTANCE_KEY_CLASS_DATE, classYoga.getClassDate());
        values.put(Constants.CLASS_INSTANCE_KEY_COMMENT, classYoga.getComment());

        // update row
        return db.update(Constants.CLASS_INSTANCE_TABLE_NAME, values, Constants.CLASS_INSTANCE_KEY_ID + "=?",
                new String[] {String.valueOf(classYoga.getId())}); // return ID of row updated
    }

    // Delete class
    public void deleteClassYoga(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.CLASS_INSTANCE_TABLE_NAME, Constants.CLASS_INSTANCE_KEY_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    // Get count
    public int getClassCount()
    {
        String countQuery = "SELECT * FROM " + Constants.CLASS_INSTANCE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null); // rawQuery: Runs the provided SQL and returns a "Cursor" over the result set (multiple results).
        cursor.close();
        return cursor.getCount();
    }

    // Search class by teacher name
    public List<ClassYoga> searchClassYoga(String teacherName)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        List<ClassYoga> classYogaList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.CLASS_INSTANCE_TABLE_NAME +
                " WHERE " + Constants.CLASS_INSTANCE_KEY_TEACHER_NAME + " LIKE ?",
                new String[] {"%" + teacherName + "%"});

        if (cursor.moveToFirst())
        {
            do {
                ClassYoga classYoga = new ClassYoga();
                int idIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_ID);
                int courseIDIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_COURSE_ID);
                int teacherNameIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_TEACHER_NAME);
                int dateClassIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_CLASS_DATE);
                int commentClassIndex = cursor.getColumnIndex(Constants.CLASS_INSTANCE_KEY_COMMENT);
                if (idIndex >= 0
                        && courseIDIndex >= 0
                        && teacherNameIndex >= 0 && dateClassIndex >= 0
                        && commentClassIndex >= 0 )
                {
                    classYoga.setId(Integer.parseInt(cursor.getString(idIndex)));
                    classYoga.setCourse_id(Integer.parseInt(cursor.getString(courseIDIndex)));
                    classYoga.setTeacherName(cursor.getString(teacherNameIndex));
                    classYoga.setClassDate(cursor.getString(dateClassIndex));
                    classYoga.setComment(cursor.getString(commentClassIndex));
                }

                classYogaList.add(classYoga);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classYogaList;
    }

    // Delete data class
    public void deleteDataClassTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.CLASS_INSTANCE_TABLE_NAME, null, null);
        db.close();
    }

}
