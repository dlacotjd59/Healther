package com.example.healtherlogin;

import java.util.Locale;

public class plus_minus_weight {

    public static String minus(String weight){
        double dweight = Double.parseDouble(weight);
        dweight -=2.5;
        if(dweight<0) {
            dweight = 0.0;
        }
        weight = String.format(Locale.getDefault(),"%.1f", dweight);

        return weight;
    }

    public static String plus(String weight){
        double dweight = Double.parseDouble(weight);
        dweight +=2.5;
        weight = String.format(Locale.getDefault(),"%.1f", dweight);

        return weight;
    }

}