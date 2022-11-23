package com.example.healtherlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Golden_Six_Input extends AppCompatActivity {

    private EditText input_squ,input_wide,input_neck,input_barbell;
    private String strBMI,strWeight;
    private String str_Squat,str_Wide,str_Neck,str_Barbell;
    private double aDouble_Squat,aDouble_Wide,aDouble_Neck,aDouble_Barbell;
    private Button cal, set_complete;

    Calendar calender = Calendar.getInstance();
    Date today = calender.getInstance().getTime();
    SimpleDateFormat weekdayFormat =  new SimpleDateFormat("EE", Locale.getDefault());
    String weekday = weekdayFormat.format(today);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.golden_six_program_input);

        cal = (Button) findViewById(R.id.calculate_weight);
        set_complete=(Button) findViewById(R.id.set_complete);
        input_squ = (EditText)findViewById(R.id.input_squat_gs);
        input_wide = (EditText)findViewById(R.id.input_wide_gs);
        input_neck = (EditText)findViewById(R.id.input_neck_gs);
        input_barbell= (EditText)findViewById(R.id.input_barbell_gs);

        Intent intent = getIntent();
        strBMI = intent.getStringExtra("BMI");
        strWeight = intent.getStringExtra("Weight");


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_Squat = input_squ.getText().toString();
                str_Wide = input_wide.getText().toString();
                str_Neck = input_neck.getText().toString();
                str_Barbell = input_barbell.getText().toString();

                try{
                    aDouble_Squat = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_Squat)) / 10); // 팀원들에게 물어보고 수정할 것
                    aDouble_Wide = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_Wide) )/ 10);
                    aDouble_Neck = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_Neck)) / 10);
                    aDouble_Barbell = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_Barbell)) / 10);

                    AlertDialog.Builder Start_Weight = new AlertDialog.Builder(Golden_Six_Input.this);
                    Start_Weight.setIcon(R.mipmap.ic_launcher);
                    Start_Weight.setTitle("추천 시작 중량");
                    Start_Weight.setMessage("스쿼트: " + String.format("%.1f", aDouble_Squat) + "(kg) \n" +
                            "와이드 그립 벤치프렐스: " + String.format("%.1f", aDouble_Wide) + "(kg) \n" +
                            "비하인드 넥 프레스: " + String.format("%.1f", aDouble_Neck) + "(kg) \n" +
                            "바벨 컬: " + String.format("%.1f", aDouble_Barbell) + "(kg) \n" +
                            "\n이 중량으로 설정 하시겠습니까?");

                    Start_Weight.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            str_Squat = String.format("%.1f", aDouble_Squat);
                            str_Wide = String.format("%.1f", aDouble_Wide);
                            str_Neck = String.format("%.1f", aDouble_Neck);
                            str_Barbell = String.format("%.1f", aDouble_Barbell);

                            input_squ.setText(str_Squat);
                            input_wide.setText(str_Wide);
                            input_neck.setText(str_Neck);
                            input_barbell.setText(str_Barbell);

                        }
                    });

                    Start_Weight.setNegativeButton("수정할래요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    Start_Weight.show();

                }catch(NumberFormatException e){
                    Toast.makeText(Golden_Six_Input.this, "원하는 중량을 입력해주세요", Toast.LENGTH_LONG).show();
                }
        }
        });

        set_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder Ru_ready = new AlertDialog.Builder(Golden_Six_Input.this);
                Ru_ready.setIcon(R.mipmap.ic_launcher);
                Ru_ready.setTitle("일주일 운동 일정 알림");
                Ru_ready.setMessage("오늘은 "+weekday+"요일 입니다.\n" +
                        "오늘 운동을 끝내면 내일은 휴식 이 방식을 두 번 반복하고 세번 째로 운동을 끝내면 그 다음에는 이틀을 쉽니다.\n\n"+
                        "예를 들어 오늘이 월요일이면 월, 수, 금은 운동하는 날, 화, 목, 토,일(주말)은 쉬는 날이 되는겁니다.\n\n" +
                        "되도록이면 월요일부터 처음 시작하는 것월 권장합니다\n\n"+
                        "운동을 시작하시겠습니까?\n");


                Ru_ready.setPositiveButton("바로 시작할래요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent Golden_Six_Show= new Intent( Golden_Six_Input.this,Golden_Six.class);
                        Golden_Six_Show.putExtra("str_Squat",str_Squat);
                        Golden_Six_Show.putExtra("str_Wide",str_Wide);
                        Golden_Six_Show.putExtra("str_Neck",str_Neck);
                        Golden_Six_Show.putExtra("str_Barbell",str_Barbell);
                        startActivity(Golden_Six_Show);

                    }
                });

                Ru_ready.setNegativeButton("지금은 하지 않을겁니다", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                Ru_ready.show();

            }
        });


    }



}
