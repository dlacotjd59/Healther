package com.example.healtherlogin;

import java.util.Locale;

public class Split_Date {
    public static int[] split(String calendarday){
        String[] sdate = calendarday.split(",");
        int[] date = {Integer.parseInt(sdate[0]),Integer.parseInt(sdate[1]),Integer.parseInt(sdate[2])};
        return date;
    }

    public static String restore(int year,int month, int day){
        String restore_date=String.format(Locale.getDefault(),"%02d"+","+"%02d"+","+"%02d",year,month,day);

        return restore_date;
    }
}
