package com.jzindenstries.releasertracker;

public class GoToProgress {
    String type;
    int id;
    String ActionName;
    String returnDate;

    public GoToProgress(String type, int id, String actionName, String returnDate) {
        this.type = type;
        this.id = id;
        ActionName = actionName;
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "GoToProgress{" +
                "type='" + type + '\'' +
                ", id=" + id +
                ", ActionName='" + ActionName + '\'' +
                ", returnDate='" + returnDate + '\'' +
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

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        ActionName = actionName;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}