package com.example.healtherlogin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Manage_Diary {

    public String date;
    public String time;
    public String exercise;

    public String getdate(){return date;}
    public String gettime(){return time;}
    public String getexercise(){return exercise;}

    public void setdate(String date){this.date = date;}
    public void settime(String time){this.time = time;}
    public void setexercise(String exercise){this.exercise = exercise;}

    Manage_Diary(){

    }

    Manage_Diary(String date, String exercise, String time){
        this.date = date;
        this.exercise = exercise;
        this.time = time;
    }

}
