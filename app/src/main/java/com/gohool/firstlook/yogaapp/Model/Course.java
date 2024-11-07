package com.gohool.firstlook.yogaapp.Model;

public class Course
{
    private int id;
    private String typeYoga;
    private String dayYoga;
    private String priceYoga;
    private String timeYoga;
    private String capacityYoga;
    private String durationYoga;
    private String descriptionYoga;

    public Course() {
    }

    public Course(int id, String typeYoga, String dayYoga, String priceYoga,
                  String timeYoga, String capacityYoga, String durationYoga, String descriptionYoga) {
        this.id = id;
        this.typeYoga = typeYoga;
        this.dayYoga = dayYoga;
        this.priceYoga = priceYoga;
        this.timeYoga = timeYoga;
        this.capacityYoga = capacityYoga;
        this.durationYoga = durationYoga;
        this.descriptionYoga = descriptionYoga;
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

    public String getTimeYoga() {
        return timeYoga;
    }

    public void setTimeYoga(String timeYoga) {
        this.timeYoga = timeYoga;
    }

    public String getCapacityYoga() {
        return capacityYoga;
    }

    public void setCapacityYoga(String capacityYoga) {
        this.capacityYoga = capacityYoga;
    }

    public String getDurationYoga() {
        return durationYoga;
    }

    public void setDurationYoga(String durationYoga) {
        this.durationYoga = durationYoga;
    }

    public String getDescriptionYoga() {
        return descriptionYoga;
    }

    public void setDescriptionYoga(String descriptionYoga) {
        this.descriptionYoga = descriptionYoga;
    }
}
