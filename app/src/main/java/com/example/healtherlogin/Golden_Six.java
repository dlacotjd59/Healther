package com.example.healtherlogin;



import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Golden_Six extends AppCompatActivity {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference= firebaseDatabase.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Button Next,Skip;
    private CheckBox setcount1,setcount2,setcount3,final_set,plus_weight;
    private ProgressBar circle_progressbar;
    private TextView time, time_unit;
    private TextView weight_set1,weight_set2,weight_set3,weight_set4;

    private int countcycle = 0;
    private final int[] setconfirm={0,0,0,1,0,0,2,0,0,3,0,0,0,4,0,0,5,0,0,0,6};

    private String [] weights = new String[4];
    private final String[] strS_or_F = new String[6];

    private ConstraintLayout dialogView;
    Date date = Calendar.getInstance().getTime();
    private String strDate = new SimpleDateFormat("yyyy,MM,dd").format(date);

    private long time_ms = 0;
    long starttime,endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        starttime=System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.golden_six_squat);
        Next = (Button) findViewById(R.id.Next);

        setcount1= (CheckBox) findViewById(R.id.setcount1);
        setcount2= (CheckBox)findViewById(R.id.setcount2);
        setcount3= (CheckBox)findViewById(R.id.setcount3);
        final_set= (CheckBox)findViewById(R.id.final_set);
        plus_weight = (CheckBox) findViewById(R.id.plus_weight);

        weight_set1 = (TextView) findViewById(R.id.weight_set1);
        weight_set2 = (TextView) findViewById(R.id.weight_set2);
        weight_set3 = (TextView) findViewById(R.id.weight_set3);
        weight_set4 = (TextView) findViewById(R.id.weight_set4);

        Intent intent = getIntent();
        weights = intent.getStringArrayExtra("Weights"); // ????????? ?????? ?????? ?????????

        weight_set1.setText(weights[0]);
        weight_set2.setText(weights[0]);
        weight_set3.setText(weights[0]);
        weight_set4.setText(weights[0]);


        time_ms = 120*1000;
    }
    public void plus_weight(View v){
        if(plus_weight.isChecked()) {
            final_set.setChecked(true);
        }
        else{
            final_set.setChecked(false);
        }
    }

    public void Next(View v) {
        switch(setconfirm[countcycle]) {
            case 0:
                if(countcycle==2 || countcycle==5 || countcycle==8 || countcycle==12 || countcycle==15){
                    Next.setText("?????? ??????");
                }
                else if(countcycle==19){
                    Next.setText("?????? ??????");
                }
                countcycle++;
                dialogView= (ConstraintLayout) View.inflate(Golden_Six.this,R.layout.timer,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Golden_Six.this);
                AlertDialog Rest = builder.create();
                Rest.setView(dialogView);
                Rest.setCancelable(false); // ????????? ??? ?????? ?????? ?????? ????????? ????????? ????????? ??????

                time = (TextView) dialogView.findViewById(R.id.Time);
                time_unit = (TextView) dialogView.findViewById(R.id.time_unit);
                circle_progressbar = (ProgressBar) dialogView.findViewById(R.id.Timer_ProgressBar);
                Skip = (Button) dialogView.findViewById(R.id.Skip);

                circle_progressbar.setProgress((int)time_ms/1000);
                circle_progressbar.setMax((int)time_ms/1000);

                Animation anima = new RotateAnimation(0.0f, 90.0f);
                anima.setFillAfter(false);
                circle_progressbar.startAnimation(anima);

                CountDownTimer countDownTimer = new CountDownTimer(time_ms,1000) {

                    @Override
                    public void onTick(long l) {
                        time.setText(String.format(Locale.getDefault(),"%d", TimeUnit.MILLISECONDS.toSeconds(l)));
                        circle_progressbar.setProgress(circle_progressbar.getMax()-(int)l/1000);
                    }
                    @Override
                    public void onFinish() {
                        time.setText(String.format(Locale.getDefault(),"%d",TimeUnit.MILLISECONDS.toSeconds(time_ms)));
                        circle_progressbar.setMax( (int)time_ms/1000);
                        Rest.dismiss();
                    }
                }.start();

                countDownTimer.start();

                Skip.setOnClickListener(view -> {
                    Rest.dismiss();
                    countDownTimer.cancel();
                });

                Rest.show();
                break;
            case 1:
                if(setcount1.isChecked() && setcount2.isChecked() && setcount3.isChecked() && final_set.isChecked()) {
                    strS_or_F[0] = "??????";
                    if(plus_weight.isChecked()){
                        strS_or_F[0] = "??????(+2.5kg)";
                        weights[0] = plus_minus_weight.plus(weights[0]);
                    }
                }
                else{
                    strS_or_F[0] = "??????";
                }

                time_ms = 90*1000;
                setContentView(R.layout.golden_six_bench);
                weight_set1 = (TextView) findViewById(R.id.weight_set1);
                weight_set2 = (TextView) findViewById(R.id.weight_set2);
                weight_set3 = (TextView) findViewById(R.id.weight_set3);

                weight_set1.setText(weights[1]);
                weight_set2.setText(weights[1]);
                weight_set3.setText(weights[1]);
                final_set = (CheckBox) findViewById(R.id.final_set);
                plus_weight=(CheckBox) findViewById(R.id.plus_weight);
                Next=(Button) findViewById(R.id.Next);
                countcycle=countcycle+1;
                break;
            case 2:
                if(setcount1.isChecked() && setcount2.isChecked() && final_set.isChecked()) {
                    strS_or_F[1] = "??????";
                    if(plus_weight.isChecked()){
                        strS_or_F[1] = "??????(+2.5kg)";
                        weights[1] = plus_minus_weight.plus(weights[1]);
                    }
                }
                else{
                    strS_or_F[1] = "??????";
                }

                setContentView(R.layout.golden_six_chin);
                Next=(Button) findViewById(R.id.Next);
                countcycle=countcycle+1;
                break;
            case 3:
                strS_or_F[2] = "??????";
                setContentView(R.layout.golden_six_neck);
                weight_set1 = (TextView) findViewById(R.id.weight_set1);
                weight_set2 = (TextView) findViewById(R.id.weight_set2);
                weight_set3 = (TextView) findViewById(R.id.weight_set3);
                weight_set4 = (TextView) findViewById(R.id.weight_set4);

                weight_set1.setText(weights[2]);
                weight_set2.setText(weights[2]);
                weight_set3.setText(weights[2]);
                weight_set4.setText(weights[2]);
                final_set = (CheckBox) findViewById(R.id.final_set);
                plus_weight=(CheckBox) findViewById(R.id.plus_weight);
                Next=(Button) findViewById(R.id.Next);
                countcycle=countcycle+1;
                break;
            case 4:
                if(setcount1.isChecked() && setcount2.isChecked() && setcount3.isChecked() && final_set.isChecked()) {
                    strS_or_F[3] = "??????";
                    if(plus_weight.isChecked()){
                        strS_or_F[3] = "??????(+2.5kg)";
                        weights[2] = plus_minus_weight.plus(weights[2]);
                    }

                } else{
                    strS_or_F[3] = "??????";
                }
                setContentView(R.layout.golden_six_curl);
                weight_set1 = (TextView) findViewById(R.id.weight_set1);
                weight_set2 = (TextView) findViewById(R.id.weight_set2);
                weight_set3 = (TextView) findViewById(R.id.weight_set3);

                weight_set1.setText(weights[3]);
                weight_set2.setText(weights[3]);
                weight_set3.setText(weights[3]);
                final_set = (CheckBox) findViewById(R.id.final_set);
                plus_weight=(CheckBox) findViewById(R.id.plus_weight);
                Next=(Button) findViewById(R.id.Next);

                countcycle=countcycle+1;
                break;
            case 5:
                if(setcount1.isChecked() && setcount2.isChecked() && setcount3.isChecked()&&final_set.isChecked()) {
                    strS_or_F[4] = "??????";
                    if(plus_weight.isChecked()){
                        strS_or_F[4] = "??????(+2.5kg)";
                        weights[3] = plus_minus_weight.plus(weights[3]);
                    }
                }
                else{
                    strS_or_F[4] = "??????";
                }

                setContentView(R.layout.golden_six_situp);
                Next=(Button) findViewById(R.id.Next);
                countcycle=countcycle+1;
                break;
            case 6:
                strS_or_F[5] = "??????";
                endtime=System.currentTimeMillis();
                int hour = (((int)endtime - (int)starttime)/1000)/3600;
                int min = (((int)endtime - (int)starttime)/1000-hour*3600)/60;
                int sec = (((int)endtime - (int)starttime)/1000)%60;
                String record = String.format(Locale.getDefault(),"%02d"+"??? "+"%02d"+"??? "+"%02d"+"???",hour,min,sec);
                Manage_Diary Diary = new Manage_Diary(strDate,"????????????", strS_or_F[0],strS_or_F[1],strS_or_F[2],strS_or_F[3],strS_or_F[4],strS_or_F[5],record);
                databaseReference.child(user.getUid()).child("??????").child(strDate).setValue(Diary);

                Manage_Weights AfterGoldenSix = new Manage_Weights(weights[0],weights[1],weights[2],weights[3]);
                databaseReference.child(user.getUid()).child("??????????????????").setValue(AfterGoldenSix);

                Toast.makeText(Golden_Six.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                Intent end = new Intent(Golden_Six.this, Diary_Home.class);

                startActivity(end);
                finish();
                break;
        }
    }

}
