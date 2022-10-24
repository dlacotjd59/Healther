package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Program_show extends AppCompatActivity {
    TextView week1,week2,week3,week4,week5;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    Calendar cal = Calendar.getInstance();
    int week = cal.get(Calendar.DAY_OF_WEEK);

    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.program_show);
        Intent intent = getIntent();
        UID = intent.getStringExtra("UID");
//        int squat, bench,babel,over,dead;

        databaseReference.child("User").child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RM_save group = dataSnapshot.getValue(RM_save.class);
                int info[];
                info = new int[]{Integer.parseInt(group.getSquat())/2, Integer.parseInt(group.getBabel())/2,
                        Integer.parseInt(group.getBabel())/2,Integer.parseInt(group.getBench())/2,Integer.parseInt(group.getOver())/2,
                        Integer.parseInt(group.getDead())/2};


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        //UI 객체생성
        week1=(TextView)findViewById(R.id.week1);
        week2=(TextView)findViewById(R.id.week2);
        week3=(TextView)findViewById(R.id.week3);
        week4=(TextView)findViewById(R.id.week4);
        week5=(TextView)findViewById(R.id.week5);
        //데이터 가져오기
        switch (week){
            case 1:
                week1.setText("일");
                week2.setText("월");
                week3.setText("화");
                week4.setText("수");
                week5.setText("목");
                break;
            case 2:
                week1.setText("월");
                week2.setText("화");
                week3.setText("수");
                week4.setText("목");
                week5.setText("금");
                break;
            case 3:
                week1.setText("화");
                week2.setText("수");
                week3.setText("목");
                week4.setText("금");
                week5.setText("토");
                break;
            case 4:
                week1.setText("수");
                week2.setText("목");
                week3.setText("금");
                week4.setText("토");
                week5.setText("일");
                break;
            case 5:
                week1.setText("목");
                week2.setText("금");
                week3.setText("토");
                week4.setText("일");
                week5.setText("월");
                break;
            case 6:
                week1.setText("금");
                week2.setText("토");
                week3.setText("일");
                week4.setText("월");
                week5.setText("화");
                break;
            case 7:
                week1.setText("토");
                week2.setText("일");
                week3.setText("월");
                week4.setText("화");
                week5.setText("수");
                break;

        }

    }


    //확인버튼클릭
    public void mOnClose(View v) {
        //데이터 전달하기
        Intent do1 = new Intent(Program_show.this,Program_do.class);
        do1.putExtra("UID", UID);
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

