package com.example.healtherlogin;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;


public class AgainActivity extends AppCompatActivity implements View.OnClickListener {
    static String msg;
    static String[] exerList;
    static int[] layoutList;

    TextView exercise_view;
    Button Progarm_btn, Alarm_btn, Intro_btn;
    AlarmManager mAlarmMgr;

    Button[] exerBtn = new Button[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /* 버튼 선언 */
        Progarm_btn = (Button) findViewById(R.id.btn_program);
        Alarm_btn = (Button) findViewById(R.id.btn_alarm);
        Intro_btn = (Button) findViewById(R.id.btn_intro);
        exercise_view = (TextView) findViewById(R.id.main_text);

        exerBtn[0] = (Button) findViewById(R.id.btn_exerA);
        exerBtn[1] = (Button) findViewById(R.id.exerA_moer);

        exerBtn[2] = (Button) findViewById(R.id.btn_exerB);
        exerBtn[3] = (Button) findViewById(R.id.exerB_more);

        exerBtn[4] = (Button) findViewById(R.id.btn_exerC);
        exerBtn[5] = (Button) findViewById(R.id.exerC_more);

        /* 알람 매니저 사용을 위한 mAlarmMgr 선언 */
        mAlarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);

        /* 프로그램 액티비티 출력 */
        Progarm_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Program_BMI.class);
                startActivity(intent);
            }
        });


        Intent pintent = this.getIntent();  // 프로그램 선택에서 넘어온 intent 받기
        msg = pintent.getStringExtra("msg");

        // 프로그램별 다른 버튼 text 출력을 위한 기능
        if (msg != null) {
            exerList = getIntent().getStringArrayExtra("exerList");
            layoutList = getIntent().getIntArrayExtra("layoutList");
            for (int i = 0; i < 6; i++) {
                if ((i % 2) == 0)
                    exerBtn[i].setText(exerList[i]);
                exerBtn[i].setTag(i);
                exerBtn[i].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;

        for(Button tmpBtn : exerBtn) {
            if (tmpBtn == btn) {
                final int s = (Integer) v.getTag();

                try {
                    if( (s % 2) == 0) {
                        try {
                            exercise_view.setText(exerList[s + 1]);    // 운동버튼 A 클릭시 textView에 정보 출력
                        } catch (NumberFormatException e) {
                            exercise_view.setText("운동 정보 없음");
                        }
                    }
                    else {
                        try {
                            Context mContext = getApplicationContext(); // 현재 앱의 context를 가져온다

                            //레이아웃 플레터를 통해 팝업 창 구현
                            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                            // 팝업을 띄울 layout 설정
//                            View layout = inflater.inflate(layoutList[s / 2], (ViewGroup) findViewById(R.id.exerPopup));
                            AlertDialog.Builder aDialog = new AlertDialog.Builder(AgainActivity.this);

//                            aDialog.setView(layout);   // 출력할 뷰를 위에 설정한 layout으로 설정

                            AlertDialog ad = aDialog.create();
                            ad.show();      // 팝업 출력
                        } catch (Exception e) {
                            Toast.makeText(getApplication(), "부연설명 없음",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplication(), "click Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /* 알람 기능 구현 */
    public void onBtnAlarm1() {

        // 알람에 첨부할 File URL 인텐트
        File file = new File("http://dnip.co.kr/player/162893");

        // 수행할 동작을 생성
        Intent intent = new Intent(this, AgainActivity.class);

        // 휴대폰 상단 state 바에 알람 추가
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.putExtra("",file.toString());    // 인텐트에 file 추가

        PendingIntent pIntent = PendingIntent.getActivity(AgainActivity.this, 0, intent, 0);
        // 1회 알람 시작
        mAlarmMgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10000, pIntent);   // 원래 10분이지만 테스트를 위해 10초로 구현
        Toast.makeText(getApplicationContext(),"알람 등록 완료", Toast.LENGTH_SHORT).show();// 알람 등록이 성공하면 메세지 출력
    }
}

