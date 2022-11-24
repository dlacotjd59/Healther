package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Program_BMI extends AppCompatActivity {

    private final FirebaseDatabase database= FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference= database.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private ImageView BMI_Image;
    private TextView BMI_View, BMI_Type_View;
    Button  Recommendation;

    double Height, Weight, Age;
    double BMI;
    String strBMI,strGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_bmi);

        BMI_View = (TextView) findViewById(R.id.BMI_View);
        BMI_Type_View = (TextView)findViewById(R.id.BMI_View_Type);
        Recommendation = (Button) findViewById(R.id.Recommendation);
        BMI_Image = (ImageView) findViewById(R.id.BMI_Image);

        Intent intent = getIntent();

        strGender = intent.getExtras().getString("Gender");


        databaseReference.child("User").child(user.getUid()).child("유저정보").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Height= Double.parseDouble(user.getheight());
                Weight= Double.parseDouble(user.getweight());
                Age= Double.parseDouble(user.getage());
                BMI=Weight / Math.pow(Height/100.0,2.0);
                strBMI = String.format("%.2f",BMI);
                BMI_View.setText(strBMI);
                if (BMI < 18.5) {
                    BMI_Type_View.setText("저체중");
                    if(strGender.equals("남자")){
                        BMI_Image.setImageResource(R.drawable.male1);
                    }else if(strGender.equals("여자")){
                        BMI_Image.setImageResource(R.drawable.female1);
                    }
                } else if (BMI >= 18.5 && BMI < 23) {
                    BMI_Type_View.setText("정상");
                    if(strGender.equals("남자")){
                        BMI_Image.setImageResource(R.drawable.male2);
                    }else if(strGender.equals("여자")){
                        BMI_Image.setImageResource(R.drawable.female2);
                    }
                } else if (BMI >= 23 && BMI < 25) {
                    BMI_Type_View.setText("과체중");
                    if(strGender.equals("남자")){
                        BMI_Image.setImageResource(R.drawable.male3);
                    }else if(strGender.equals("여자")){
                        BMI_Image.setImageResource(R.drawable.female3);
                    }
                } else if (BMI >= 25 && BMI < 30) {
                    BMI_Type_View.setText("1단계 비만");
                    if(strGender.equals("남자")){
                        BMI_Image.setImageResource(R.drawable.male4);
                    }else if(strGender.equals("여자")){
                        BMI_Image.setImageResource(R.drawable.female4);
                    }
                } else if (BMI >= 30 && BMI < 35) {
                    BMI_Type_View.setText("2단계 비만");
                    if(strGender.equals("남자")){
                        BMI_Image.setImageResource(R.drawable.male5);
                    }else if(strGender.equals("여자")){
                        BMI_Image.setImageResource(R.drawable.female5);
                    }
                } else {
                    BMI_Type_View.setText("3단계 비만");
                    if(strGender.equals("남자")){
                        BMI_Image.setImageResource(R.drawable.male5);
                    }else if(strGender.equals("여자")){
                        BMI_Image.setImageResource(R.drawable.female5);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        //추천프로그램으로 이동
        Recommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent strength_exercise = new Intent(Program_BMI.this, Recommend_Exercise.class);
                strength_exercise.putExtra("strBMI_Type",BMI_Type_View.getText());
                strength_exercise.putExtra("BMI",BMI);
                strength_exercise.putExtra("Gender",strGender);
                startActivity(strength_exercise);
            }
        });



        //바텀 네비게이션
        BottomNavigationView bottom_navi = findViewById(R.id.bottom_navi);
        bottom_navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.Diary){
                    Intent Diary= new Intent(Program_BMI.this, Diary_Home.class);
                    startActivity(Diary);
                }else if(item.getItemId()==R.id.Recommendation){
                    Toast.makeText(Program_BMI.this, "현재 화면입니다", Toast.LENGTH_SHORT).show();
                }

                return false;

            }
        });


    }




}
