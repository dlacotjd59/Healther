package com.example.healtherlogin;

public class User_information {

    public String email;
    public String age;
    public String height;
    public String weight;
    public String gender;
    public String bmi;

    public User_information(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public String getBMI() {
        return bmi;
    }

    public void setBMI(String bmi) {
        this.bmi = bmi;
    }

    public String getemail(){return email;}
    public String getage(){return age;}
    public String getheight(){return height;}
    public String getweight(){return weight;}
    public String getgender(){return gender;}


    public void setemail(String email){this.email = email;}
    public void setage(String age){this.age = age;}
    public void setheight(String height){this.height = height;}
    public void setweight(String weight){this.weight = weight;}
    public void setgender(String gender){this.gender = gender;}

    public User_information(String email, String age, String height, String weight, String gender, String bmi){
        this.email=email;
        this.age=age;
        this.height=height;
        this.weight=weight;
        this.gender=gender;
        this.bmi=bmi;
    }
}