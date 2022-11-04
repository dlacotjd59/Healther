package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class Program_show extends AppCompatActivity {
    TextView week1,week2,week3,week4,week5;
    TextView week1txt1,week1txt2,week1txt3, week3txt1, week3txt2, week3txt3, week5txt1, week5txt2, week5txt3;

    double info[]={0,0,0,0,0};
    String dinfo[] = {null,null};

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String UID = user.getUid();
    Query date1 = databaseReference.child("User").child(UID).orderByChild("date");
    ArrayList<Routine_save> rsaver = new ArrayList<>();

    double squat=0;
    double babel=0;
    double bench=0;
    double over=0;
    double dead=0;
    Calendar cal = Calendar.getInstance();
    int week = cal.get(Calendar.DAY_OF_WEEK);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.program_show);
//        int squat, bench,babel,over,dead;
        week1=(TextView)findViewById(R.id.week1);
        week1txt1=(TextView)findViewById(R.id.week1txt1);
        week1txt2=(TextView)findViewById(R.id.week1txt2);
        week1txt3=(TextView)findViewById(R.id.week1txt3);
        week2=(TextView)findViewById(R.id.week2);
        week3=(TextView)findViewById(R.id.week3);
        week3txt1=(TextView)findViewById(R.id.week3txt1);
        week3txt2=(TextView)findViewById(R.id.week3txt2);
        week3txt3=(TextView)findViewById(R.id.week3txt3);
        week4=(TextView)findViewById(R.id.week4);
        week5=(TextView)findViewById(R.id.week5);
        week5txt1=(TextView)findViewById(R.id.week5txt1);
        week5txt2=(TextView)findViewById(R.id.week5txt2);
        week5txt3=(TextView)findViewById(R.id.week5txt3);

        databaseReference.child("User").child(UID).child("5RM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RM_save group = dataSnapshot.getValue(RM_save.class);
                info[0] = Double.parseDouble(group.getSquat()) / 2;
                info[1] = Double.parseDouble(group.getBench()) / 2;
                info[2] = Double.parseDouble(group.getBabel()) / 2;
                info[3] = Double.parseDouble(group.getOver()) / 2;
                info[4] = Double.parseDouble(group.getDead()) / 2;

                squat = info[0];
                bench = info[1];
                babel = info[2];
                over = info[3];
                dead = info[4];
                date1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        rsaver.clear();
                        int i = -1;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Routine_save type = dataSnapshot.getValue(Routine_save.class);
                            rsaver.add(type);
                            i = i + 1;
                        }
                        Routine_save pass = rsaver.get(i);
                        dinfo[0] = pass.getDate();
                        dinfo[1] = pass.getType();
                        week1.setText(weekcal(week % 7));
                        if (dinfo[1].equals("A")) {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("오버헤드프레스" + over);
                            week1txt3.setText("데드리프트" + dead);
                        } else {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("벤치프레스" + bench);
                            week1txt3.setText("바벨로우" + babel);
                        }
                        week2.setText(weekcal((week + 1) % 7));
                        week3.setText(weekcal((week + 2) % 7));
                        if (dinfo[1].equals("A")) {
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("벤치프레스" + bench);
                            week3txt3.setText("바벨로우" + babel);
                        } else {
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("오버헤드프레스" + over);
                            week3txt3.setText("데드리프트" + dead);
                        }
                        week4.setText(weekcal((week + 3) % 7));
                        week5.setText(weekcal((week + 4) % 7));
                        if (dinfo[1].equals("A")) {
                            squat = squat + 2.5;
                            over = over + 2.5;
                            dead = dead + 5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("오버헤드프레스" + over);
                            week5txt3.setText("데드리프트" + dead);
                        } else {
                            squat = squat + 2.5;
                            bench = bench + 2.5;
                            babel = babel + 2.5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("벤치프레스" + bench);
                            week5txt3.setText("바벨로우" + babel);
                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public String weekcal(int week){
        switch (week){
            case 0:
                return "일";
            case 1:
                return "월";
            case 2:
                return "화";
            case 3:
                return "수";
            case 4:
                return "목";
            case 5:
                return "금";
            case 6:
                return "토";

        }
        return null;
    }


    //확인버튼클릭
    public void mOnClose(View v) {
        //데이터 전달하기
        Intent do1 = new Intent(Program_show.this,Program_do.class);
        do1.putExtra("info",info);
        do1.putExtra("UID", UID);
        do1.putExtra("type",dinfo[1]);
        //액티비티(팝업) 닫기
        startActivity(do1);
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed(){
        //안드로이드 백버튼 막기
        return;
    }
}

