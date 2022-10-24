package com.example.healtherlogin;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DowonYoon on 2017-07-11.
 */

//@IgnoreExtraProperties
public class RM_save {
   public String squat;
   public String bench;
   public String babel;
   public String over;
   public String dead;

    public RM_save(){
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

//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("스쿼트", squat);
//        result.put("벤치프레스", bench);
//        result.put("바벨로우", babel);
//        result.put("오버헤드프레스", over);
//        result.put("데드리프트", dead);
//        return result;
//    }
}
