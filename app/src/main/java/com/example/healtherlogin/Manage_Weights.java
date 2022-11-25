package com.example.healtherlogin;

public class Manage_Weights {
    public String squat;
    public String bench;
    public String curl;
    public String neck;


    public Manage_Weights(){

    }

    public String getSquat() {
        return squat;
    }

    public String getBench() {
        return bench;
    }

    public void setBench(String bench) {
        this.bench = bench;
    }

    public void setSquat(String squat) {
        this.squat = squat;
    }

    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public Manage_Weights(String squat, String bench, String curl, String neck) {
        this.squat = squat;
        this.bench = bench;
        this.curl = curl;
        this.neck = neck;
    }

}
