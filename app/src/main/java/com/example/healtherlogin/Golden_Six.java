package com.example.healtherlogin;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private CheckBox setcount1,setcount2,setcount3,setcount4,plus_weight;
    private ProgressBar circle_progressbar;
    private TextView time, time_unit;
    private TextView weight_set1,weight_set2,weight_set3,weight_set4;
    private EditText last_set_count;

    private int countcycle = 0;
    private int countset = 0;
    private int[] setconfirm={0,0,0,1,0,0,2,0,0,3,0,0,0,4,0,0,5,0,0,0,6};

    private String [] weights = new String[4];
    private final String[] strS_or_F = new String[6];

    private ConstraintLayout dialogView;
    Date date = Calendar.getInstance().getTime();
    private String strDate = new SimpleDateFormat("yyyyMMdd").format(date);

    private long time_ms = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.golden_six_program_do_squ);
        Next = (Button) findViewById(R.id.Next);

        setcount1= (CheckBox) findViewById(R.id.setcount1);
        setcount2= (CheckBox)findViewById(R.id.setcount2);
        setcount3= (CheckBox)findViewById(R.id.setcount3);
        setcount4= (CheckBox)findViewById(R.id.setcount4);
        plus_weight = (CheckBox) findViewById(R.id.plus_weight);

        weight_set1 = (TextView) findViewById(R.id.weight_set1);
        weight_set2 = (TextView) findViewById(R.id.weight_set2);
        weight_set3 = (TextView) findViewById(R.id.weight_set3);
        weight_set4 = (TextView) findViewById(R.id.weight_set4);

        last_set_count = (EditText) findViewById(R.id.last_set_count) ;

        Intent intent = getIntent();
        weights = intent.getStringArrayExtra("Weights"); // 설정한 무게 값을 가져옴

        weight_set1.setText(weights[0]);
        weight_set2.setText(weights[0]);
        weight_set3.setText(weights[0]);
        weight_set4.setText(weights[0]);


        time_ms = 120*1000;
    }

    public void Next(View v) {

        Double tmp_weight = 0.0;
        switch(setconfirm[countcycle]) {
            case 0:
                countcycle++;
                dialogView= (ConstraintLayout) View.inflate(Golden_Six.this,R.layout.timer,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Golden_Six.this);
                AlertDialog Rest = builder.create();
                Rest.setView(dialogView);
                Rest.setCancelable(false); // 백버튼 및 다른 영역 버튼 클릭시 팝업창 닫음을 막음

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

                Skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Rest.dismiss();
                        countDownTimer.cancel();
                    }
                });

                Rest.show();
                break;
            case 1:
                if(setcount1.isChecked() && setcount2.isChecked() && setcount3.isChecked() && (setcount4.isChecked()||plus_weight.isChecked())) {
                    strS_or_F[0] = "스쿼트 성공";
                }
                else{
                    strS_or_F[0] = "스쿼트 실패";
                }

                if(Integer.parseInt(last_set_count.getText().toString())>=13){
                    tmp_weight = Double.parseDouble(weights[0]);
                    tmp_weight +=2.5;
                    weights[0] = String.format(Locale.getDefault(),"%.1f", tmp_weight);
                }

                time_ms = 90*1000;
                setContentView(R.layout.golden_six_program_do_ben);
                weight_set1 = (TextView) findViewById(R.id.weight_set1);
                weight_set2 = (TextView) findViewById(R.id.weight_set2);
                weight_set3 = (TextView) findViewById(R.id.weight_set3);

                weight_set1.setText(weights[1]);
                weight_set2.setText(weights[1]);
                weight_set3.setText(weights[1]);

                countcycle=countcycle+1;
                countset=0;
                break;
            case 2:
                if(setcount1.isChecked() && setcount2.isChecked() && (setcount3.isChecked()||plus_weight.isChecked())) {
                    strS_or_F[1] = "벤치프레스 성공";
                }
                else{
                    strS_or_F[1] = "벤치프레스 실패";
                }

                if(Integer.parseInt(last_set_count.getText().toString())>=13){
                    tmp_weight = Double.parseDouble(weights[1]);
                    tmp_weight +=2.5;
                    weights[1] = String.format(Locale.getDefault(),"%.1f", tmp_weight);
                }

                setContentView(R.layout.golden_six_program_do_chin);
                countcycle=countcycle+1;
                countset=0;
                break;
            case 3:
                strS_or_F[2] = "친업 수행";
                setContentView(R.layout.golden_six_program_do_neck);
                weight_set1 = (TextView) findViewById(R.id.weight_set1);
                weight_set2 = (TextView) findViewById(R.id.weight_set2);
                weight_set3 = (TextView) findViewById(R.id.weight_set3);
                weight_set4 = (TextView) findViewById(R.id.weight_set4);
                weight_set1.setText(weights[2]);
                weight_set2.setText(weights[2]);
                weight_set3.setText(weights[2]);
                weight_set4.setText(weights[2]);
                countcycle=countcycle+1;
                countset=0;
                break;
            case 4:
                if(setcount1.isChecked() && setcount2.isChecked() && setcount3.isChecked() && (setcount4.isChecked()||plus_weight.isChecked())) {
                    strS_or_F[3] = "비하인드 넥 프레스 성공";
                } else{
                    strS_or_F[3] = "비하인드 넥 프레스 실패";
                }

                if(Integer.parseInt(last_set_count.getText().toString())>=13){
                    tmp_weight = Double.parseDouble(weights[2]);
                    tmp_weight +=2.5;
                    weights[2] = String.format(Locale.getDefault(),"%.1f", tmp_weight);
                }

                setContentView(R.layout.golden_six_program_do_curl);
                weight_set1 = (TextView) findViewById(R.id.weight_set1);
                weight_set2 = (TextView) findViewById(R.id.weight_set2);
                weight_set3 = (TextView) findViewById(R.id.weight_set3);

                weight_set1.setText(weights[3]);
                weight_set2.setText(weights[3]);
                weight_set3.setText(weights[3]);

                countcycle=countcycle+1;
                countset=0;

                break;
            case 5:
                if(setcount1.isChecked() && setcount2.isChecked() && setcount3.isChecked() && (setcount4.isChecked()||plus_weight.isChecked())) {
                    strS_or_F[4] = "바벨컬 성공";
                } else{
                    strS_or_F[4] = "바벨컬 실패";
                }

                if(Integer.parseInt(last_set_count.getText().toString())>=13){
                    tmp_weight = Double.parseDouble(weights[3]);
                    tmp_weight +=2.5;
                    weights[3] = String.format(Locale.getDefault(),"%.1f", tmp_weight);
                }

                setContentView(R.layout.golden_six_program_do_situp);
                countcycle=countcycle+1;
                countset=0;
                break;
            case 6:
                strS_or_F[5] = "싯업 수행";
                Manage_Diary Diary = new Manage_Diary(strDate, strS_or_F[0],strS_or_F[1],strS_or_F[2],strS_or_F[3],strS_or_F[4],strS_or_F[5]);
                databaseReference.child("User").child(user.getUid()).child("일지").child("근력운동").child(strDate).setValue(Diary);
                databaseReference.child("User").child(user.getUid()).child("일지").child("운동한 날").setValue(strDate);
                Manage_Weights AfterGoldenSix = new Manage_Weights(weights[0],weights[1],weights[2],weights[3]);
                databaseReference.child("User").child(user.getUid()).child("골든식스무게").setValue(AfterGoldenSix);

                Toast.makeText(Golden_Six.this, "운동 완료", Toast.LENGTH_SHORT).show();
                Intent end = new Intent(Golden_Six.this, Diary_Home.class);
                end.putExtra("FinishGoldenSixDate",strDate);
                startActivity(end);
                finish();
                break;
        }
    }

}
