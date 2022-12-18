package com.example.healtherlogin;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Signup extends AppCompatActivity {

    private EditText Email_join;
    private EditText Pwd_join;
    private EditText Age_join,Weight_join,Height_join;
    private Button btn;
    private RadioButton radioButton_man,radioButton_woman;
    private RadioGroup radioGroup_gender;
    private String gender;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference= firebaseDatabase.getInstance().getReference();
    Manage_Weights weights = new Manage_Weights();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_ui);
        Email_join = (EditText) findViewById(R.id.save_ID);
        Pwd_join = (EditText) findViewById(R.id.save_PW);
        Age_join=(EditText)findViewById(R.id.save_AGE);
        Height_join=(EditText)findViewById(R.id.save_HEI);
        Weight_join=(EditText)findViewById(R.id.save_WEI);
        btn = (Button) findViewById(R.id.signup_but2);
        radioGroup_gender = (RadioGroup) findViewById(R.id.radiogroup_gender);
        radioButton_man = (RadioButton) findViewById(R.id.btn_man);
        radioButton_woman = (RadioButton) findViewById(R.id.btn_woman);

        radioGroup_gender.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i==R.id.btn_man){
                gender = radioButton_man.getText().toString();
                weights.setSquat("32.0");
                weights.setBench("35.0");
                weights.setCurl("20.0");
                weights.setNeck("20.0");
            } else if(i==R.id.btn_woman){
                gender = radioButton_woman.getText().toString();
                weights.setSquat("20.0");
                weights.setBench("20.0");
                weights.setCurl("10.0");
                weights.setNeck("10.0");
            }
        });



        btn.setOnClickListener(v -> {
            if (gender !=null) {
                final String Email = Email_join.getText().toString().trim();
                final String Age = Age_join.getText().toString().trim();
                final String Height = Height_join.getText().toString().trim();
                final String Weight = Weight_join.getText().toString().trim();
                final double BMI= Double.parseDouble(Weight) / Math.pow(Double.parseDouble(Height)/100.0,2.0);
                final String strBMI = String.format(Locale.getDefault(),"%.2f",BMI);
                //공백인 부분을 제거하고 보여주는 trim();

                firebaseAuth.createUserWithEmailAndPassword(Email, Pwd_join.getText().toString().trim())
                        .addOnCompleteListener(Signup.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                User_information new_userInformation = new User_information(user.getEmail(), Age, Height, Weight, gender,strBMI);
                                databaseReference.child(user.getUid()).child("골든식스무게").setValue(weights);
                                databaseReference.child(user.getUid()).child("유저정보").setValue(new_userInformation);
                                Toast.makeText(Signup.this, "가입 성공", Toast.LENGTH_SHORT).show();
                                Intent complete = new Intent(Signup.this, Login.class);
                                startActivity(complete);
                                finish();

                            } else {
                                Toast.makeText(Signup.this, "등록 에러", Toast.LENGTH_SHORT).show();
                            }
                        });

            }

            else{
                Toast.makeText(Signup.this, "해당하는 성별 버튼을 눌러주세요!", Toast.LENGTH_SHORT).show();
            }

        });


    }


}