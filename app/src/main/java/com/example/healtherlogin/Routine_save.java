package com.example.healtherlogin;

public class Routine_save {
    public String date;
    public String type;

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Routine_save(){}

    public Routine_save(String type,String date){
        this.date=date;
        this.type=type;
    }

}
