package com.gohool.firstlook.yogaapp.Model;

public class Course
{
    private int id;
    private String typeYoga;
    private String dayYoga;
    private String priceYoga;

    public Course() {
    }

    public Course(int id, String typeYoga, String dayYoga, String priceYoga) {
        this.id = id;
        this.typeYoga = typeYoga;
        this.dayYoga = dayYoga;
        this.priceYoga = priceYoga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeYoga() {
        return typeYoga;
    }

    public void setTypeYoga(String typeYoga) {
        this.typeYoga = typeYoga;
    }

    public String getDayYoga() {
        return dayYoga;
    }

    public void setDayYoga(String dayYoga) {
        this.dayYoga = dayYoga;
    }

    public String getPriceYoga() {
        return priceYoga;
    }

    public void setPriceYoga(String priceYoga) {
        this.priceYoga = priceYoga;
    }
}
