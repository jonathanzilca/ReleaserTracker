package com.jzindenstries.releasertracker;

public class GoToAction {
    String type;
    int id;
    String actionName;
    String takerName;
    String takerDate;
    String inspectorName;
    String inspectorDate ;

    public GoToAction(String type, int id, String actionName, String takerName, String takerDate, String inspectorName, String inspectorDate) {
        this.type = type;
        this.id = id;
        this.actionName = actionName;
        this.takerName = takerName;
        this.takerDate = takerDate;
        this.inspectorName = inspectorName;
        this.inspectorDate = inspectorDate;
    }

    @Override
    public String toString() {
        return "GoToAction{" +
                "type='" + type + '\'' +
                ", id=" + id +
                ", actionName='" + actionName + '\'' +
                ", takerName='" + takerName + '\'' +
                ", takerDate='" + takerDate + '\'' +
                ", inspectorName='" + inspectorName + '\'' +
                ", inspectorDate='" + inspectorDate + '\'' +
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
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getTakerName() {
        return takerName;
    }

    public void setTakerName(String takerName) {
        this.takerName = takerName;
    }

    public String getTakerDate() {
        return takerDate;
    }

    public void setTakerDate(String takerDate) {
        this.takerDate = takerDate;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public String getInspectorDate() {
        return inspectorDate;
    }

    public void setInspectorDate(String inspectorDate) {
        this.inspectorDate = inspectorDate;
    }
}