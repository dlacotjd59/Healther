package com.example.healtherlogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

/*
 * 아직 미완성
 */


public class Diary_Home extends AppCompatActivity {


    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String UID = user.getUid();

    private TextView Height, Weight, Age, Gender;
    private TextView SelectedDate, SelectedDate_Exercise, SelectedDate_Time;
    private EditText update_height, update_weight, update_age;
    private ConstraintLayout Update_MyPhysicalInformation, Show_Details;
    private MaterialCalendarView materialCalendarView;
    private Button Fix, Fix_user;

    private String str_Height, str_Weight, str_Age, str_Gender;
    private String year, month, day, strDate;
    private String FinishGoldenSixDate,FinishAerobicDate;
    private final ArrayList<String> CompleteExerciseDates = new ArrayList<String>();
    private User og_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_home);

        Intent intent = getIntent(); // 아직 미구현
        try {
            FinishAerobicDate = intent.getStringExtra(FinishAerobicDate);
            FinishGoldenSixDate = intent.getStringExtra(FinishGoldenSixDate);
            CompleteExerciseDates.add(FinishAerobicDate);
            CompleteExerciseDates.add(FinishGoldenSixDate);
        }catch (NullPointerException e){

        }


        Fix = (Button) findViewById(R.id.Fix);
        Gender = (TextView) findViewById(R.id.Gender);
        Height = (TextView) findViewById(R.id.Height);
        Weight = (TextView) findViewById(R.id.Weight);
        Age = (TextView) findViewById(R.id.Age);
        materialCalendarView =  (MaterialCalendarView) findViewById(R.id.calendarView);

        databaseReference.child("User").child(UID).child("유저정보").addValueEventListener(new ValueEventListener() { // 파이어베이스에서 유저 정보를 가져와 메인화면에 전시
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

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1970, 0, 1))
                .setMaximumDate(CalendarDay.from(2040, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit(); // 달력의 최소 최대 일자를 설정, 시작 요일은 일요일

        databaseReference.child("User").child(UID).child("일지").child("운동한날짜").addValueEventListener(new ValueEventListener() { //운동한 날짜만 가져오기, 아직 미완성
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String addDate  = snapshot.getValue(String.class);
                CompleteExerciseDates.add(addDate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        new ApiSimulator(CompleteExerciseDates).executeOnExecutor(Executors.newSingleThreadExecutor()); // database에서 가져온 날짜 arraylist를 전달

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) { //선택된 날짜의 일지를 불러옴
                year = Integer.toString(date.getYear());
                if(date.getMonth()+1>0&&date.getMonth()+1<10){
                    month = "0"+Integer.toString(date.getMonth() + 1);
                }else{
                    month = Integer.toString(date.getMonth() + 1);
                }

                if(date.getDay()>0&&date.getMonth()<10){
                    day = "0"+Integer.toString(date.getDay());
                }else{
                    day = Integer.toString(date.getDay());
                }

                strDate = year+month +day; // 선택된 날자를 yyyymmdd 형식으로 변환
                materialCalendarView.clearSelection();

                databaseReference.child("User").child(user.getUid()).child("일지").child("유산소운동").child(strDate).addValueEventListener(new ValueEventListener() { // 데이터베이스에 저장된 일지를 불러옴, 아직 미완성
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Show_Details = (ConstraintLayout) View.inflate(Diary_Home.this,R.layout.diary_detail,null);
                        AlertDialog.Builder Diary = new AlertDialog.Builder(Diary_Home.this); // 팝업창으로 일지의 상세정보를 보여줌
                        Diary.setView(Show_Details);

                        SelectedDate = (TextView) Show_Details.findViewById(R.id.Date);
                        SelectedDate_Exercise = (TextView) Show_Details.findViewById(R.id.Exercise);
                        SelectedDate_Time = (TextView) Show_Details.findViewById(R.id.TotalTime);

                        Manage_Diary Diary_SelectedDay = snapshot.getValue(Manage_Diary.class); //
                        try {
                            SelectedDate.setText(strDate);
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



        Fix.setOnClickListener(new View.OnClickListener() { // 자신의 신체정보 수정
            @Override
            public void onClick(View view) {
                Update_MyPhysicalInformation = (ConstraintLayout) View.inflate(Diary_Home.this, R.layout.update_user, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Diary_Home.this); // 팝업창을 사용해 신체정볼를 수정
                AlertDialog Update = builder.create();
                Update.setView(Update_MyPhysicalInformation);

                update_height = (EditText) Update_MyPhysicalInformation.findViewById(R.id.Update_Height);
                update_weight = (EditText) Update_MyPhysicalInformation.findViewById(R.id.Update_Weight);
                update_age = (EditText) Update_MyPhysicalInformation.findViewById(R.id.Update_Age);
                Fix_user = (Button) Update_MyPhysicalInformation.findViewById(R.id.Fix_user);

                update_height.setText(og_user.getheight());
                update_weight.setText(og_user.getweight());
                update_age.setText(og_user.getage());

                Fix_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                            Update.dismiss();
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
    public void onBackPressed() { //메인 화면이므로 뒤로 가기버튼을 누르면 앱을 종료
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


    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> { // 운동한 날짜들을 가져와 달려에 표시해주는 부분

        ArrayList<String> Time_Result;

        ApiSimulator(ArrayList<String> Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            for(int i = 0 ; i < Time_Result.size() ; i ++){
                try {
                    CalendarDay Day = CalendarDay.from(calendar);
                    String time = Time_Result.get(i);
                    int year,month,day;
                    year = Integer.parseInt(time.substring(0,3));

                    if(time.substring(4).equals("0")){
                        month = Integer.parseInt(time.substring(5));
                    }else{
                        month = Integer.parseInt(time.substring(4,5));
                    }

                    if(time.substring(6).equals("0")){
                        day = Integer.parseInt(time.substring(7));
                    }else{
                        day = Integer.parseInt(time.substring(6,7));
                    }

                    dates.add(Day);
                    calendar.set(year, month - 1, day);
                }catch (NullPointerException e){

                }
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays,Diary_Home.this));
        }
    }






}
