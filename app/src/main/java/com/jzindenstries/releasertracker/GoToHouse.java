package com.jzindenstries.releasertracker;

public class GoToHouse {
    String type;
    int id;
    String name;
    String date;
    boolean isInspected;

    public GoToHouse(String type, int id, String name, String date, boolean isInspected) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.date = date;
        this.isInspected = isInspected;
    }

    public GoToHouse(String type, int id, String name, String date) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public GoToHouse(String type, int id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReleaserClass{" +
                "type='" + type + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", isInspected=" + isInspected +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isInspected() {
        return isInspected;
    }

    public void setInspected(boolean inspected) {
        isInspected = inspected;
    }
}