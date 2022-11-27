package com.example.healtherlogin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Manage_Diary {

    public String date;
    public String time;
    public String exercise;

    public String s_or_f_squat;
    public String s_or_f_bench;
    public String s_or_f_curl;
    public String s_or_f_chin;
    public String s_or_f_situp;
    public String s_or_f_neck;

    //getter
    public String getdate(){
        return date;
    }
    public String gettime(){
        return time;
    }
    public String getexercise(){
        return exercise;
    }

    public String getS_or_f_squat() {
        return s_or_f_squat;
    }

    public String getS_or_f_bench() {
        return s_or_f_bench;
    }

    public String getS_or_f_curl() {
        return s_or_f_curl;
    }

    public String getS_or_f_chin() {
        return s_or_f_chin;
    }

    public String getS_or_f_situp() {
        return s_or_f_situp;
    }

    public String getS_or_f_neck() {
        return s_or_f_neck;
    }


    //setter
    public void setdate(String date){
        this.date = date;
    }

    public void settime(String time){
        this.time = time;
    }

    public void setexercise(String exercise){
        this.exercise = exercise;
    }

    public void setS_or_f_squat(String s_or_f_squat) {
        this.s_or_f_squat = s_or_f_squat;
    }


    public void setS_or_f_bench(String s_or_f_bench) {
        this.s_or_f_bench = s_or_f_bench;
    }


    public void setS_or_f_curl(String s_or_f_curl) {
        this.s_or_f_curl = s_or_f_curl;
    }


    public void setS_or_f_chin(String s_or_f_chin) {
        this.s_or_f_chin = s_or_f_chin;
    }


    public void setS_or_f_situp(String s_or_f_situp) {
        this.s_or_f_situp = s_or_f_situp;
    }


    public void setS_or_f_neck(String s_or_f_neck) {
        this.s_or_f_neck = s_or_f_neck;
    }


    public Manage_Diary(){

    }

    public Manage_Diary(String date, String exercise, String time){
        this.date = date;
        this.exercise = exercise;
        this.time = time;
    }

    public Manage_Diary(String date,String exercise,String s_or_f_squat,String s_or_f_bench, String s_or_f_chin, String s_or_f_neck, String s_or_f_curl, String s_or_f_situp,String time){
        this.date=date;
        this.exercise=exercise;
        this.s_or_f_squat=s_or_f_squat;
        this.s_or_f_bench=s_or_f_bench;
        this.s_or_f_chin=s_or_f_chin;
        this.s_or_f_neck=s_or_f_neck;
        this.s_or_f_curl=s_or_f_curl;
        this.s_or_f_situp=s_or_f_situp;
        this.time=time;
    }

}
