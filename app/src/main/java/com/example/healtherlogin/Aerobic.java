package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
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

public class Aerobic extends AppCompatActivity {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference= firebaseDatabase.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Calendar cal = Calendar.getInstance();
    private Date today = cal.getInstance().getTime();
    private String date = new SimpleDateFormat("yyyy,MM,dd").format(today);

    private Button start_pause, finish;
    private ImageView running_image;
    private ProgressBar Time_Bar;
    private TextView time_record;

    private String record;
    private CountDownTimer countDownTimer;
    private boolean isRunning;
    private boolean animate;
    private long Left_Time_ms = 100*1000;  // 100초로 임의로 설정
    private long Init_Time_sec = 100; // 100초로 임의로 설정


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aerobic_running);

        start_pause = (Button) findViewById(R.id.Start_Pause_Running);
        finish = (Button) findViewById(R.id.Finish_Running);
        running_image = (ImageView) findViewById(R.id.running_image);
        Time_Bar = (ProgressBar) findViewById(R.id.time_bar);

        Time_Bar.setProgress(0);
        Time_Bar.setMax((int)Left_Time_ms/1000);

        Glide.with(this).load(R.raw.running).into(running_image);


        start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRunning){ //달리기 멈춤
                    countDownTimer.cancel();
                    isRunning = false;

                    start_pause.setText("다시 시작");
                }else{//달리기 시작

                    Time_Bar.setProgress(Time_Bar.getMax()-(int)Left_Time_ms/1000);

                    countDownTimer = new CountDownTimer(Left_Time_ms,1000) {
                        @Override
                        public void onTick(long l) {
                            Left_Time_ms = (int) (l);
                            Time_Bar.setProgress(Time_Bar.getMax()-(int) Left_Time_ms/1000);

                            int min = ((int)Init_Time_sec - (int)Left_Time_ms/1000)/60;
                            int sec = ((int)Init_Time_sec - (int)Left_Time_ms/1000)%60;
                            record = String.format(Locale.getDefault(),"%02d"+"분 "+"%02d"+"초",min,sec);
                        }
                        @Override
                        public void onFinish() {
                            Left_Time_ms = 100*1000;
                            isRunning = false;
                            Toast.makeText(Aerobic.this, "설정한 시간이 끝났습니다.", Toast.LENGTH_SHORT).show();
                            Time_Bar.setProgress(0);
                        }
                    }.start();

                    isRunning = true;
                    start_pause.setText("일시 정지");

                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Manage_Diary Today_Diary= new Manage_Diary(date, "런닝",record);
                databaseReference.child(user.getUid()).child("일지").child(date).setValue(Today_Diary);
                start_pause.setText("운동 시작");
                Intent Finish_Aerobic= new Intent(Aerobic.this, Diary_Home.class);
                startActivity(Finish_Aerobic);
                Toast.makeText(Aerobic.this, "운동 완료", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

