package com.jzindenstries.releasertracker;

public class PieClass {
    String Type;
    int Amount;

    public PieClass(String type, int amount) {
        Type = type;
        Amount = amount;
    }

    @Override
    public String toString() {
        return "PieClass{" +
                "Type='" + Type + '\'' +
                ", Amount=" + Amount +
                '}';
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}