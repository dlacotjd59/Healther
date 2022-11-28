package com.example.healtherlogin;

import android.app.DownloadManager;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.checkerframework.checker.units.qual.A;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.Executors;

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


    private String str_Height, str_Weight, str_Age, str_Gender, selectday;
    private ArrayList<String> listdate = new ArrayList<String>();
    private Button Fix, Fix_user;
    private User og_user;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    Query qdate = databaseReference.child("User").child(UID).child("일지");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_home);

        Fix = (Button) findViewById(R.id.Fix);
        Gender = (TextView) findViewById(R.id.Gender);
        Height = (TextView) findViewById(R.id.Height);
        Weight = (TextView) findViewById(R.id.Weight);
        Age = (TextView) findViewById(R.id.Age);

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



        materialCalendarView =  (MaterialCalendarView) findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2010, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        qdate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listdate.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Manage_Diary manage_diary = dataSnapshot.getValue(Manage_Diary.class);
                    listdate.add(manage_diary.getdate());
                }
                Log.i("test", String.valueOf(listdate.size()));
                new ApiSimulator(listdate).executeOnExecutor(Executors.newSingleThreadExecutor());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();
                if(Month<10) {
                    selectday = Year + ",0" + Month + "," + Day;
                }
                else{
                    selectday = Year + "," + Month + "," + Day;
                }
                databaseReference.child("User").child(UID).child("일지").child(selectday).addValueEventListener(new ValueEventListener() {
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
                            SelectedDate.setText(selectday);
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

                materialCalendarView.clearSelection();
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


    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

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
            dates.clear();
            /*특정날짜 달력에 점표시해주는곳*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.size() ; i ++){
                String[] time = Time_Result.get(i).split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);
                calendar.set(year,month-1,dayy);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                Log.i("dates",String.valueOf(dates.get(i)));

                Log.i("test", String.valueOf(dates.size()));
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
