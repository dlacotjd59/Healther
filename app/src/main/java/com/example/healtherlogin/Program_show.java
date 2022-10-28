package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Program_show extends AppCompatActivity {
    TextView week1,week2,week3,week4,week5;
    TextView week1txt1,week1txt2,week1txt3, week3txt1, week3txt2, week3txt3, week5txt1, week5txt2, week5txt3;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    double squat=0;
    double babel=0;
    double bench=0;
    double over=0;
    double dead=0;
    Calendar cal = Calendar.getInstance();
    int week = cal.get(Calendar.DAY_OF_WEEK);
    double info[]={0,0,0,0,0};
    String UID = user.getUid();
    String dinfo[] = {null,null};
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

        databaseReference.child("User").child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RM_save group = dataSnapshot.getValue(RM_save.class);
                info[0] = Double.parseDouble(group.getSquat())/2;
                info[1] = Double.parseDouble(group.getBench())/2;
                info[2] = Double.parseDouble(group.getBabel())/2;
                info[3] = Double.parseDouble(group.getOver())/2;
                info[4] = Double.parseDouble(group.getDead())/2;

                squat = info[0];
                bench = info[1];
                babel = info[2];
                over = info[3];
                dead = info[4];

                Routine_save type = dataSnapshot.getValue(Routine_save.class);
                dinfo[0] = type.getType();
                dinfo[1] = type.getDate();

                switch (week) {
                    case 1:
                        week1.setText("일");
                        if (dinfo[1] == "A") {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("오버헤드프레스" + over);
                            week1txt3.setText("데드리프트" + dead);
                        } else {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("벤치프레스" + bench);
                            week1txt3.setText("바벨로우" + babel);
                        }
                        week2.setText("월");
                        week3.setText("화");
                        if (dinfo[1] == "A") {
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("벤치프레스" + bench);
                            week3txt3.setText("바벨로우" + babel);
                        } else{
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("오버헤드프레스" + over);
                            week3txt3.setText("데드리프트" + dead);
                        }
                        week4.setText("수");
                        week5.setText("목");
                        if(dinfo[1]=="A"){
                            squat = squat + 2.5;
                            over = over + 2.5;
                            dead = dead + 5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("오버헤드프레스" + over);
                            week5txt3.setText("데드리프트" + dead);
                        }
                        else {
                            squat = squat + 2.5;
                            bench = bench + 2.5;
                            babel = babel + 2.5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("벤치프레스" + bench);
                            week5txt3.setText("바벨로우" + babel);
                        }
                        break;
                    case 2:
                        week1.setText("월");
                        if (dinfo[1] == "A") {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("오버헤드프레스" + over);
                            week1txt3.setText("데드리프트" + dead);
                        } else {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("벤치프레스" + bench);
                            week1txt3.setText("바벨로우" + babel);
                        }
                        week2.setText("화");
                        week3.setText("수");
                        if (dinfo[1] == "A") {
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("벤치프레스" + bench);
                            week3txt3.setText("바벨로우" + babel);
                        } else{
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("오버헤드프레스" + over);
                            week3txt3.setText("데드리프트" + dead);
                        }
                        week4.setText("목");
                        week5.setText("금");
                        if(dinfo[1]=="A"){
                            squat = squat + 2.5;
                            over = over + 2.5;
                            dead = dead + 5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("오버헤드프레스" + over);
                            week5txt3.setText("데드리프트" + dead);
                        }
                        else {
                            squat = squat + 2.5;
                            bench = bench + 2.5;
                            babel = babel + 2.5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("벤치프레스" + bench);
                            week5txt3.setText("바벨로우" + babel);
                        }
                        break;
                    case 3:
                        week1.setText("화");
                        if (dinfo[1] == "A") {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("오버헤드프레스" + over);
                            week1txt3.setText("데드리프트" + dead);
                        } else {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("벤치프레스" + bench);
                            week1txt3.setText("바벨로우" + babel);
                        }
                        week2.setText("수");
                        week3.setText("목");
                        if (dinfo[1] == "A") {
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("벤치프레스" + bench);
                            week3txt3.setText("바벨로우" + babel);
                        } else{
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("오버헤드프레스" + over);
                            week3txt3.setText("데드리프트" + dead);
                        }
                        week4.setText("금");
                        week5.setText("토");
                        if(dinfo[1]=="A"){
                            squat = squat + 2.5;
                            over = over + 2.5;
                            dead = dead + 5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("오버헤드프레스" + over);
                            week5txt3.setText("데드리프트" + dead);
                        }
                        else {
                            squat = squat + 2.5;
                            bench = bench + 2.5;
                            babel = babel + 2.5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("벤치프레스" + bench);
                            week5txt3.setText("바벨로우" + babel);
                        }
                        break;
                    case 4:
                        week1.setText("수");
                        if (dinfo[1] == "A") {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("오버헤드프레스" + over);
                            week1txt3.setText("데드리프트" + dead);
                        } else {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("벤치프레스" + bench);
                            week1txt3.setText("바벨로우" + babel);
                        }
                        week2.setText("목");
                        week3.setText("금");
                        if (dinfo[1] == "A") {
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("벤치프레스" + bench);
                            week3txt3.setText("바벨로우" + babel);
                        } else{
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("오버헤드프레스" + over);
                            week3txt3.setText("데드리프트" + dead);
                        }
                        week4.setText("토");
                        week5.setText("일");
                        if(dinfo[1]=="A"){
                            squat = squat + 2.5;
                            over = over + 2.5;
                            dead = dead + 5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("오버헤드프레스" + over);
                            week5txt3.setText("데드리프트" + dead);
                        }
                        else {
                            squat = squat + 2.5;
                            bench = bench + 2.5;
                            babel = babel + 2.5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("벤치프레스" + bench);
                            week5txt3.setText("바벨로우" + babel);
                        }
                        break;
                    case 5:
                        week1.setText("목");
                        if (dinfo[1] == "A") {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("오버헤드프레스" + over);
                            week1txt3.setText("데드리프트" + dead);
                        } else {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("벤치프레스" + bench);
                            week1txt3.setText("바벨로우" + babel);
                        }
                        week2.setText("금");
                        week3.setText("토");
                        if (dinfo[1] == "A") {
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("벤치프레스" + bench);
                            week3txt3.setText("바벨로우" + babel);
                        } else{
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("오버헤드프레스" + over);
                            week3txt3.setText("데드리프트" + dead);
                        }
                        week4.setText("일");
                        week5.setText("월");
                        if(dinfo[1]=="A"){
                            squat = squat + 2.5;
                            over = over + 2.5;
                            dead = dead + 5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("오버헤드프레스" + over);
                            week5txt3.setText("데드리프트" + dead);
                        }
                        else {
                            squat = squat + 2.5;
                            bench = bench + 2.5;
                            babel = babel + 2.5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("벤치프레스" + bench);
                            week5txt3.setText("바벨로우" + babel);
                        }
                        break;
                    case 6:
                        week1.setText("금");
                        if (dinfo[1] == "A") {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("오버헤드프레스" + over);
                            week1txt3.setText("데드리프트" + dead);
                        } else {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("벤치프레스" + bench);
                            week1txt3.setText("바벨로우" + babel);
                        }
                        week2.setText("토");
                        week3.setText("일");
                        if (dinfo[1] == "A") {
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("벤치프레스" + bench);
                            week3txt3.setText("바벨로우" + babel);
                        } else{
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("오버헤드프레스" + over);
                            week3txt3.setText("데드리프트" + dead);
                        }
                        week4.setText("월");
                        week5.setText("화");
                        if(dinfo[1]=="A"){
                            squat = squat + 2.5;
                            over = over + 2.5;
                            dead = dead + 5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("오버헤드프레스" + over);
                            week5txt3.setText("데드리프트" + dead);
                        }
                        else {
                            squat = squat + 2.5;
                            bench = bench + 2.5;
                            babel = babel + 2.5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("벤치프레스" + bench);
                            week5txt3.setText("바벨로우" + babel);
                        }
                        break;
                    case 7:
                        week1.setText("토");
                        if (dinfo[1] == "A") {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("오버헤드프레스" + over);
                            week1txt3.setText("데드리프트" + dead);
                        } else {
                            week1txt1.setText("스쿼트" + squat);
                            week1txt2.setText("벤치프레스" + bench);
                            week1txt3.setText("바벨로우" + babel);
                        }
                        week2.setText("일");
                        week3.setText("월");
                        if (dinfo[1] == "A") {
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("벤치프레스" + bench);
                            week3txt3.setText("바벨로우" + babel);
                        } else{
                            squat = squat + 2.5;
                            week3txt1.setText("스쿼트" + squat);
                            week3txt2.setText("오버헤드프레스" + over);
                            week3txt3.setText("데드리프트" + dead);
                        }
                        week4.setText("화");
                        week5.setText("수");
                        if(dinfo[1]=="A"){
                            squat = squat + 2.5;
                            over = over + 2.5;
                            dead = dead + 5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("오버헤드프레스" + over);
                            week5txt3.setText("데드리프트" + dead);
                        }
                        else {
                            squat = squat + 2.5;
                            bench = bench + 2.5;
                            babel = babel + 2.5;
                            week5txt1.setText("스쿼트" + squat);
                            week5txt2.setText("벤치프레스" + bench);
                            week5txt3.setText("바벨로우" + babel);
                        }
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });







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

