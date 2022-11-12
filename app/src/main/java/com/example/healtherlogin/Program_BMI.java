package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Program_BMI extends AppCompatActivity {
    private TextView BMI_view;
    private EditText e_age, e_tall, e_weight;
    private ToggleButton tgl_sex;
    private ImageView Image;
    private final FirebaseDatabase database= FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference= database.getInstance().getReference();

    Calendar cal = Calendar.getInstance();
    Button BMI_count, show_program;
    String age;
    String tall, weight;

    double temp_tall, temp_weight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);

        BMI_view = (TextView) findViewById(R.id.BMI_view);
        e_age = (EditText) findViewById(R.id.bmi_edit_age);
        e_tall = (EditText) findViewById(R.id.bmi_edit_tall);
        e_weight = (EditText) findViewById(R.id.bmi_edit_weight);
        tgl_sex = (ToggleButton) findViewById(R.id.bmi_toggle);
        BMI_count = (Button) findViewById(R.id.bmi_count);
        show_program = (Button) findViewById(R.id.btn_bmi);
        Image = (ImageView) findViewById(R.id.bmi_image);

        BMI_count.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    age = e_age.getText().toString();   // 입력한 나이를 string형식으로 가져온다
                    tall = e_tall.getText().toString(); // 입력한 키를 string형식으로 가져온다
                    weight = e_weight.getText().toString(); // 입력한 몸무게를 string형식으로 가져온다

                    temp_tall = Double.valueOf(tall).doubleValue(); // 키를 Double 형식으로 변형
                    temp_weight = Double.valueOf(weight).doubleValue(); // 몸무게를 Double 형식으로 변형

                    double temp_BMI = bmi_calcul(temp_tall, temp_weight);
                    String BMI = String.format("%.2f", temp_BMI);   // BMI 소수점 2자리까지 출력

                    /* BMI에 따른 이미지 출력 */
                    if(temp_BMI < 18.5) {
                        Image.setImageResource(R.drawable.body_small04); // drawable에 있는 이미지 출력
                    }
                    else if(temp_BMI >= 18.5 && temp_BMI <25.0) {
                        Image.setImageResource(R.drawable.body_small03);
                    }
                    else if(temp_BMI >= 25.0 && temp_BMI <30.0) {
                        Image.setImageResource(R.drawable.body_small02);
                    }
                    else {
                        Image.setImageResource(R.drawable.body_small01);
                    }

                    BMI_view.setText(BMI);  // BMI 결과창에 입력한 신체 정보의 BMI 계산 결과 출력
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "계산할 수 없습니다", Toast.LENGTH_SHORT).show();
                    BMI_view.setText("계산 불가");
                }

            }
        });


        /* 프로그램 선택 창 출력 */
        show_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent btn_bmi = new Intent(Program_BMI.this, Program＿activity.class);
                    startActivity(btn_bmi);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /* BMI 계산 메서드 */
    public double bmi_calcul(double tall_, double weight_) {
        double bmi_ = weight_ /tall_ / tall_ * 10000;
        return bmi_;
    }
}