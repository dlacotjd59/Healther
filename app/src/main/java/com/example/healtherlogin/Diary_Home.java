package com.example.healtherlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Diary_Home extends AppCompatActivity {


    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String UID = user.getUid();

    private TextView Height, Weight, Age, Gender;
    private TextView SelectedDate, SelectedDate_Exercise, SelectedDate_Time;
    private EditText update_height, update_weight, update_age;
    private ConstraintLayout Update_MyPhysicalInformation, Show_Details;
    private CalendarView CalenderView;


    private String str_Height, str_Weight, str_Age, str_Gender;
    private String year, month, day, date;
    private Button Fix;
    private User og_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_home);

        Fix = (Button) findViewById(R.id.Fix);
        Gender = (TextView) findViewById(R.id.Gender);
        Height = (TextView) findViewById(R.id.Height);
        Weight = (TextView) findViewById(R.id.Weight);
        Age = (TextView) findViewById(R.id.Age);
        CalenderView = (CalendarView) findViewById(R.id.calendarView);


        databaseReference.child("User").child(UID).child("유저정보").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                og_user = snapshot.getValue(User.class);
                try {
                    Height.setText(og_user.getheight());
                    Weight.setText(og_user.getweight());
                    Age.setText(og_user.getage());
                    Gender.setText(og_user.getgender());
                } catch (NullPointerException e) {
                    Toast.makeText(Diary_Home.this, "정보를 가져올 수 없습니다", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


       CalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() { // 날짜 선택시 그 날 상세내용
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                year = Integer.toString(i);
                ++i1;
                if(i1<10&&i1>0){
                     month = "0"+i1;
                }else{
                    month = Integer.toString(i1);
                }
                if(i2<10&&i2>0){
                     day = "0"+i2;
                }else{
                    day = Integer.toString(i2);
                }


                date = year+month+day;

                databaseReference.child("User").child(UID).child(date).child("유산소운동").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Show_Details = (ConstraintLayout) View.inflate(Diary_Home.this,R.layout.diary_detail,null);
                        AlertDialog.Builder Diary = new AlertDialog.Builder(Diary_Home.this);
                        Diary.setView(Show_Details);

                            SelectedDate = (TextView) Show_Details.findViewById(R.id.Date);
                            SelectedDate_Exercise = (TextView) Show_Details.findViewById(R.id.Exercise);
                            SelectedDate_Time = (TextView) Show_Details.findViewById(R.id.TotalTime);

                            Manage_Diary Diary_SelectedDay = snapshot.getValue(Manage_Diary.class);
                            try {
                                SelectedDate.setText(date);
                                SelectedDate_Exercise.setText(Diary_SelectedDay.getexercise());
                                SelectedDate_Time.setText(Diary_SelectedDay.gettime());
                                Diary.show();
                             }catch (NullPointerException e){
                                Toast.makeText(Diary_Home.this, "이날에는 운동을 하지 않았습니다", Toast.LENGTH_SHORT).show();
                               }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            }
        });


        Fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update_MyPhysicalInformation = (ConstraintLayout) View.inflate(Diary_Home.this, R.layout.update_user, null);
                AlertDialog.Builder Update = new AlertDialog.Builder(Diary_Home.this);
                Update.setView(Update_MyPhysicalInformation);

                update_height = (EditText) Update_MyPhysicalInformation.findViewById(R.id.Update_Height);
                update_weight = (EditText) Update_MyPhysicalInformation.findViewById(R.id.Update_Weight);
                update_age = (EditText) Update_MyPhysicalInformation.findViewById(R.id.Update_Age);

                update_height.setText(og_user.getheight());
                update_weight.setText(og_user.getweight());
                update_age.setText(og_user.getage());

                Update.setPositiveButton("수정하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (update_height.getText().toString().equals("") ||
                                update_weight.getText().toString().equals("") ||
                                update_age.getText().toString().equals("")) {

                            Toast.makeText(Diary_Home.this, "빈칸이 없어야 합니다.", Toast.LENGTH_SHORT).show();

                        } else {
                            str_Height = update_height.getText().toString();
                            str_Weight = update_weight.getText().toString();
                            str_Age = update_age.getText().toString();

                            Height.setText(str_Height);
                            Weight.setText(str_Weight);
                            Age.setText(str_Age);

                            og_user.setheight(str_Height);
                            og_user.setweight(str_Weight);
                            og_user.setage(str_Age);

                            databaseReference.child("User").child(UID).child("유저정보").setValue(og_user);
                            dialogInterface.dismiss();
                            Toast.makeText(Diary_Home.this, "수정됐습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                Update.show();


            }
        });


        BottomNavigationView bottom_navi = findViewById(R.id.bottom_navi);
        bottom_navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.Diary) {
                    Toast.makeText(Diary_Home.this, "현재 화면입니다", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.Recommendation) {
                    str_Gender = Gender.getText().toString();
                    Intent Recommendation = new Intent(Diary_Home.this, Program_BMI.class);
                    Recommendation.putExtra("Gender", str_Gender);
                    startActivity(Recommendation);
                }

                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder off = new AlertDialog.Builder(this);
        off.setTitle("종료");
        off.setMessage("종료하시겠습니까?");

        off.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        off.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        off.show();

    }



}
