package com.example.healtherlogin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Aerobic extends AppCompatActivity implements LocationListener {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference= firebaseDatabase.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Calendar cal = Calendar.getInstance();
    private Date today = cal.getInstance().getTime();
    private String date = new SimpleDateFormat("yyyy,MM,dd").format(today);

    private Button start_pause, finish;
    private ImageView running_image;
    private ProgressBar Time_Bar;
    private TextView Speed,Distance;
    private LocationManager locationManager;
    private Location L_location = null;


    private String record;
    private CountDownTimer countDownTimer;
    private boolean isRunning;
    private long Left_Time_ms = 100*1000;  // 100초로 임의로 설정
    private final long Init_Time_sec = 100; // 100초로 임의로 설정


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aerobic_running);

        start_pause = (Button) findViewById(R.id.Start_Pause_Running);
        finish = (Button) findViewById(R.id.Finish_Running);
        running_image = (ImageView) findViewById(R.id.running_image);
        Time_Bar = (ProgressBar) findViewById(R.id.time_bar);
        Speed = (TextView) findViewById(R.id.speed);
        Distance= (TextView) findViewById(R.id.distance);

        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck1 == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0); //GPS를 이용한 위치 권한 요청
        }

        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        if(permissionCheck2 == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},0); // 백그라운드 위치 권한 요청
        }

        int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if(permissionCheck3 == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},0); // 백그라운드 위치 권한 요청
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);




        Glide.with(this).load(R.raw.run).into(running_image);
        Time_Bar.setProgress(0);
        Time_Bar.setMax((int)Left_Time_ms/1000);


        start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRunning){ //달리기 멈춤
                    countDownTimer.cancel();
                    isRunning = false;

                    ((GifDrawable)running_image.getDrawable()).stop();
                    start_pause.setText("다시 시작");
                }else{//달리기 시작

                    ((GifDrawable)running_image.getDrawable()).start();
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
                            Left_Time_ms = Init_Time_sec;
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

    @Override
    public void onLocationChanged(@NonNull Location location) {

        String speed = String.format("%.3f",location.getSpeed());
        Speed.setText(speed);


        if(L_location != null){
            Distance.setText((int) L_location.distanceTo(location));
        }
        L_location = location;
    }


}

