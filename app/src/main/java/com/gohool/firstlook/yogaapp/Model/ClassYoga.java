package com.gohool.firstlook.yogaapp.Model;

public class ClassYoga
{
    private int id;
    private int course_id;
    private String teacherName;
    private String classDate;
    private String comment;

    public ClassYoga() {
    }

    public ClassYoga(int id, int course_id, String teacherName, String classDate, String comment) {
        this.id = id;
        this.course_id = course_id;
        this.teacherName = teacherName;
        this.classDate = classDate;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
