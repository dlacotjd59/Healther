package com.example.healtherlogin;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

//@IgnoreExtraProperties
public class RM_save {
    public String squat;
    public String bench;
    public String babel;
    public String over;
    public String dead;

    public RM_save() {
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public String getSquat() {
        return squat;
    }

    public String getDead() {
        return dead;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public void setBabel(String babel) {
        this.babel = babel;
    }

    public void setBench(String bench) {
        this.bench = bench;
    }

    public void setSquat(String squat) {
        this.squat = squat;
    }

    public String getBabel() {
        return babel;
    }

    public String getBench() {
        return bench;
    }

    public RM_save(String squat, String bench, String babel, String over, String dead) {
        this.squat = squat;
        this.bench = bench;
        this.babel = babel;
        this.over = over;
        this.dead = dead;
    }
}

