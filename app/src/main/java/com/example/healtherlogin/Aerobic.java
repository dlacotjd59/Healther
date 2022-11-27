package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Aerobic extends AppCompatActivity {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference= firebaseDatabase.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Calendar cal = Calendar.getInstance();
    private Date today = cal.getInstance().getTime();
    private String date = new SimpleDateFormat("yyyyMMdd").format(today);

    private Button start_pause, finish;
    private ImageView running_image;
    private ProgressBar Time_Bar;
    private TextView time_record;

    private String record;
    private CountDownTimer countDownTimer;
    private boolean isRunning;
    private boolean animate;
    private long Left_Time_ms = 100*1000; //시간이 지나는 것을 확인하기 위해 최대값을 100초로 설정 (기본은 60분)
    private long Init_Time_sec = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aerobic_running);

        start_pause = (Button) findViewById(R.id.Start_Pause_Running);
        finish = (Button) findViewById(R.id.Finish_Running);
        running_image = (ImageView) findViewById(R.id.running_image);
        Time_Bar = (ProgressBar) findViewById(R.id.time_bar);
        time_record = (TextView) findViewById(R.id.time);

        Time_Bar.setProgress(0);
        Time_Bar.setMax((int)Left_Time_ms/1000);
        time_record.setText("00분 00초");

        Glide.with(this).load(R.raw.running).into(running_image);


        start_pause.setOnClickListener(new View.OnClickListener() { //시작을 누르면 타임바가 시작
            @Override
            public void onClick(View view) {
                if(isRunning){ //달리고 있을 때 버튼을 누를면 달리기를 잠시 멈춤
                    countDownTimer.cancel();
                    isRunning = false;

                    start_pause.setText("다시 시작");
                }else{//달리고 있지 않을 때 버튼을 누를면 달리기를 다시 시작

                    Time_Bar.setProgress(Time_Bar.getMax()-(int)Left_Time_ms/1000);

                    countDownTimer = new CountDownTimer(Left_Time_ms,1000) {
                        @Override
                        public void onTick(long l) {
                            Left_Time_ms = (int) (l);
                            Time_Bar.setProgress(Time_Bar.getMax()-(int) Left_Time_ms/1000); // 프로그레스바를 왼쪽에서 오른쪽으로 가도록 설정
                            int min = ((int)Init_Time_sec - (int)Left_Time_ms/1000)/60;
                            int sec = ((int)Init_Time_sec - (int)Left_Time_ms/1000)%60;
                            record = String.format(Locale.getDefault(),"%02d"+"분 "+"%02d"+"초",min,sec);
                            time_record.setText(record);
                        }
                        @Override
                        public void onFinish() {
                            Left_Time_ms = 100*1000;
                            isRunning = false;
                            Toast.makeText(Aerobic.this, "설정한 시간이 끝났습니다.", Toast.LENGTH_SHORT).show(); // 설정한 시간이 모두 지나면 시간이 끝났음을 알림
                            Time_Bar.setProgress(0);
                        }
                    }.start();

                    isRunning = true;
                    start_pause.setText("일시 정지");

                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() { // 운동 종료 버튼을 눌렀을 때
            @Override
            public void onClick(View view) {

                Manage_Diary Today_Diary= new Manage_Diary(date, "런닝",time_record.getText().toString());
                databaseReference.child("User").child(user.getUid()).child("일지").child("유산소운동").child(date).setValue(Today_Diary); // 일지를 저장
                databaseReference.child("User").child(user.getUid()).child("일지").child("운동한 날").setValue(date); // 운동한 날짜만 저장
                time_record.setText("00분 00초");
                start_pause.setText("운동 시작");
                Intent Finish_Aerobic= new Intent(Aerobic.this, Diary_Home.class); // 운동이 끝나면 메인 홈 화면으로 이동
                Finish_Aerobic.putExtra("FinishAerobicDate",date);
                startActivity(Finish_Aerobic);
                Toast.makeText(Aerobic.this, "운동 완료", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

