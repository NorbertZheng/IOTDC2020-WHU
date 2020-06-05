package com.example.qingnang;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;


public class Medi extends LitePalSupport implements Serializable {
    private int year,month,day,time;
    private String name;
    private double dosage;
    private boolean uploaded;

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getDosage() {
        return dosage;
    }
    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    public int gettime() {
        return time;
    }
}
