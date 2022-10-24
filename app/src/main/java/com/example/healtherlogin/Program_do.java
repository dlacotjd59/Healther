package com.example.healtherlogin;

import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Program_do extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.program_do);
        //UI 객체생성
       // txtText=(TextView)findViewById(R.id.txtText);
        //데이터 가져오기
        Intent intent=getIntent();
        String squat=intent.getStringExtra("result_squat");
      //  txtText.setText(squat);
    }

    public void OnComplete(View v){
        Intent complete = new Intent(Program_do.this, MainActivity.class);
        startActivity(complete);
    }
}
