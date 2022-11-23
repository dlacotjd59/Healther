package com.example.healtherlogin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Diary {

    private Calendar cal = Calendar.getInstance();
    private Date today = cal.getInstance().getTime();
    SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");


    private String date = yyyymmdd.format(today);
    private long Time;
    private String Memo;


    Diary(){

    }


}
