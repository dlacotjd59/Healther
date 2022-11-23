package com.example.healtherlogin;

public class User {

    public String email;
    public String password;
    public String age;
    public String height;
    public String weight;
    public String gender;

    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public String getemail(){return email;}
    public String getpassword(){return password;}
    public String getage(){return age;}
    public String getheight(){return height;}
    public String getweight(){return weight;}
    public String getgender(){return gender;}


    public void setemail(String email){this.email = email;}
    public void setpassword(String password){this.password = password;}
    public void setage(String age){this.age = age;}
    public void setheight(String height){this.height = height;}
    public void setweight(String weight){this.weight = weight;}
    public void setgender(String gender){this.gender = gender;}

    public User(String email, String password,String age, String height, String weight,String gender){
        this.email=email;
        this.password=password;
        this.age=age;
        this.height=height;
        this.weight=weight;
        this.gender=gender;
    }
}
