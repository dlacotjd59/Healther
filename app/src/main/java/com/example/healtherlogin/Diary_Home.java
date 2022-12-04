package com.example.healtherlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class Diary_Home extends AppCompatActivity {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String UID = user.getUid();

    private TextView Height, Weight, Age, Gender;
    private TextView SelectedDate, SelectedDate_Exercise, SelectedDate_Time; // 상세정보_런닝 팝업창의 텍스트뷰
    private TextView Squat_SorF,Bench_SorF,Chin_SorF,Neck_SorF,Curl_SorF,Situp_SorF;
    private EditText update_height, update_weight, update_age;
    private ConstraintLayout Update_MyPhysicalInformation, Show_Details_Running,Show_Details_Golden;

    private MaterialCalendarView materialCalendarView;


    private String str_Height, str_Weight, str_Age, str_Gender, selectday,strBMI,BMI_Type,BMI_Image;
    private double BMI;
    private ArrayList<String> listdate = new ArrayList<String>();
    private Button Fix, Fix_user;
    private User_information og_userInformation;

    Query qdate = databaseReference.child(UID).child("일지");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_home_ui);

        Fix = (Button) findViewById(R.id.Fix);
        Gender = (TextView) findViewById(R.id.Gender);
        Height = (TextView) findViewById(R.id.Height);
        Weight = (TextView) findViewById(R.id.Weight);
        Age = (TextView) findViewById(R.id.Age);

        databaseReference.child(UID).child("유저정보").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                og_userInformation = snapshot.getValue(User_information.class);
                try {
                    Height.setText(og_userInformation.getheight());
                    Weight.setText(og_userInformation.getweight());
                    Age.setText(og_userInformation.getage());
                    Gender.setText(og_userInformation.getgender());
                    BMI=Double.parseDouble(og_userInformation.getBMI());
                    if (BMI < 18.5) {
                        BMI_Type="저체중";
                        BMI_Image="male1";
                        if(og_userInformation.getgender().equals("여자")){
                            BMI_Image="fe"+BMI_Image;
                        }
                    } else if (BMI >= 18.5 && BMI < 23) {
                        BMI_Type="정상";
                        BMI_Image="male2";
                        if(og_userInformation.getgender().equals("여자")){
                            BMI_Image="fe"+BMI_Image;
                        }
                    } else if (BMI >= 23 && BMI < 25) {
                        BMI_Type="과체중";
                        BMI_Image="male3";
                        if(og_userInformation.getgender().equals("여자")){
                            BMI_Image="fe"+BMI_Image;
                        }
                    } else if (BMI >= 25 && BMI < 30) {
                        BMI_Type="1단계 비만";
                        BMI_Image="male4";
                        if(og_userInformation.getgender().equals("여자")){
                            BMI_Image="fe"+BMI_Image;
                        }
                    } else if (BMI >= 30 && BMI < 35) {
                        BMI_Type="2단계 비만";
                        BMI_Image="male5";
                        if(og_userInformation.getgender().equals("여자")){
                            BMI_Image="fe"+BMI_Image;
                        }
                    } else {
                        BMI_Type="3단계 비만";
                        BMI_Image="male5";
                        if(og_userInformation.getgender().equals("여자")){
                            BMI_Image="fe"+BMI_Image;
                        }
                    }

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

                selectday = String.format(Locale.getDefault(),"%02d"+","+"%02d"+","+"%02d",Year,Month,Day);

                databaseReference.child(UID).child("일지").child(selectday).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Manage_Diary Diary_SelectedDay = snapshot.getValue(Manage_Diary.class);
                        try {
                            String ex = Diary_SelectedDay.getexercise();
                            switch(ex){
                                case "골든식스":
                                    Show_Details_Golden = (ConstraintLayout) View.inflate(Diary_Home.this,R.layout.diary_check,null);
                                    AlertDialog.Builder Diary_Golden = new AlertDialog.Builder(Diary_Home.this);
                                    Diary_Golden.setView(Show_Details_Golden);
                                    SelectedDate = (TextView) Show_Details_Golden.findViewById(R.id.Date);
                                    SelectedDate_Time = (TextView) Show_Details_Golden.findViewById(R.id.time_golden);

                                    Squat_SorF = (TextView) Show_Details_Golden.findViewById(R.id.squ_sf);
                                    Bench_SorF = (TextView) Show_Details_Golden.findViewById(R.id.ben_sf);
                                    Chin_SorF = (TextView) Show_Details_Golden.findViewById(R.id.chin_sf);
                                    Neck_SorF = (TextView) Show_Details_Golden.findViewById(R.id.neck_sf);
                                    Curl_SorF = (TextView) Show_Details_Golden.findViewById(R.id.curl_sf);
                                    Situp_SorF = (TextView) Show_Details_Golden.findViewById(R.id.situp_sf);

                                    Squat_SorF.setText(Diary_SelectedDay.getS_or_f_squat());
                                    Bench_SorF.setText(Diary_SelectedDay.getS_or_f_bench());
                                    Chin_SorF.setText(Diary_SelectedDay.getS_or_f_chin());
                                    Neck_SorF.setText(Diary_SelectedDay.getS_or_f_neck());
                                    Curl_SorF.setText(Diary_SelectedDay.getS_or_f_curl());
                                    Situp_SorF.setText(Diary_SelectedDay.getS_or_f_situp());

                                    SelectedDate.setText(selectday);
                                    SelectedDate_Time.setText(Diary_SelectedDay.gettime());

                                    Diary_Golden.show();
                                    break;
                                case "런닝":
                                    Show_Details_Running = (ConstraintLayout) View.inflate(Diary_Home.this,R.layout.diary_detail,null);
                                    AlertDialog.Builder Diary_Running = new AlertDialog.Builder(Diary_Home.this);
                                    Diary_Running.setView(Show_Details_Running);
                                    SelectedDate = (TextView) Show_Details_Running.findViewById(R.id.Date);
                                    SelectedDate_Exercise = (TextView) Show_Details_Running.findViewById(R.id.Exercise);
                                    SelectedDate_Time = (TextView) Show_Details_Running.findViewById(R.id.TotalTime);
                                    SelectedDate.setText(selectday);
                                    SelectedDate_Exercise.setText(Diary_SelectedDay.getexercise());
                                    SelectedDate_Time.setText(Diary_SelectedDay.gettime());
                                    Diary_Running.show();
                                    break;
                            }
                        }catch(NullPointerException e){
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

                update_height.setText(og_userInformation.getheight());
                update_weight.setText(og_userInformation.getweight());
                update_age.setText(og_userInformation.getage());

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

                            og_userInformation.setheight(str_Height);
                            og_userInformation.setweight(str_Weight);
                            og_userInformation.setage(str_Age);

                            BMI= Double.parseDouble(str_Weight) / Math.pow(Double.parseDouble(str_Height)/100.0,2.0);
                            strBMI = String.format(Locale.getDefault(),"%.2f",BMI);
                            og_userInformation.setBMI(strBMI);

                            databaseReference.child(UID).child("유저정보").setValue(og_userInformation);
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
                    Intent Recommendation = new Intent(Diary_Home.this, Recommend.class);
                    Recommendation.putExtra("Gender", str_Gender);
                    Recommendation.putExtra("BMI",BMI);
                    Recommendation.putExtra("BMI_Type",BMI_Type);
                    Recommendation.putExtra("BMI_Image",BMI_Image);
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

        off.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        off.setNegativeButton("네", new DialogInterface.OnClickListener() {
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
