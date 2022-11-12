package com.example.healtherlogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class Program＿activity extends Activity implements View.OnClickListener {
    static ArrayList<String> programList;

    private Button[] program = new Button[6];
    String[][] exercise = new String[6][9];
    int[][] exerLayout = new int[6][3];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_activity);

        program[0] = (Button) findViewById(R.id.program_1);
        program[1] = (Button) findViewById(R.id.program_2);


        programList = new ArrayList<String>();
        programList.add(0, "맞춤형 다이어트");
        programList.add(1, "하루 15분 다이어트");


        for (int i = 0; i < 6; i++) {
            program[i].setText(programList.get(i));
            program[i].setTag(i);
            program[i].setOnClickListener(this);
        }

        exercise[0][0] = getString(R.string.buffy); exercise[0][1] = getString(R.string.buffyInfo);
        exercise[0][2] = getString(R.string.lungy); exercise[0][3] = getString(R.string.lungyInfo);
        exercise[0][4] = getString(R.string.pullup); exercise[0][5] = getString(R.string.pullupInfo);
        exerLayout[0][0] = R.layout.program1; exerLayout[0][1] = R.layout.program1; exerLayout[0][2] = R.layout.program1;

        exercise[1][0] = getString(R.string.buffy); exercise[1][1] = getString(R.string.buffyInfo);
        exercise[1][2] = getString(R.string.running); exercise[1][3] = getString(R.string.runningInfo);
        exercise[1][4] = getString(R.string.pushup); exercise[1][5] = getString(R.string.pushupInfo);
        exerLayout[1][0] = R.layout.program1; exerLayout[1][1] = R.layout.program1; exerLayout[1][2] = R.layout.program1;


    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;

        for (Button tmpBtn : program) {
            if (tmpBtn == btn) {
                final int s = (Integer) v.getTag();

                try {
                    Context mContext = getApplicationContext();
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                    //R.layout.program[번호]는 xml 파일명이고  R.id.popup[번호]는 보여줄 레이아웃 아이디
                    View layout = inflater.inflate(R.layout.program+s+1, (ViewGroup) findViewById(R.id.popup));
                    AlertDialog.Builder aDialog = new AlertDialog.Builder(Program＿activity.this);

                    aDialog.setTitle(programList.get(s));
                    aDialog.setView(layout); //program1.xml 파일을 뷰로 셋팅
                    //시작버튼을 위한 부분
                    aDialog.setNegativeButton("시작", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Program＿activity.this, AgainActivity.class);
                            intent.putExtra("msg", "exer" + (s + 1));
                            intent.putExtra("exerList", exercise[s]);
                            intent.putExtra("layoutList", exerLayout[s]);
                            startActivity(intent);
                        }
                    });
                    //팝업창 생성
                    AlertDialog ad = aDialog.create();
                    ad.show();//설정한 레이아웃 출력
                } catch (Exception e) {
                    Toast.makeText(getApplication(), "프로그램 없음", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}