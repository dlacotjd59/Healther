package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button join;
    private Button login;
    private EditText email_login;
    private EditText pwd_login;
    private final FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        join = (Button) findViewById(R.id.signup_but);
        login = (Button) findViewById(R.id.login_but);
        email_login = (EditText) findViewById(R.id.input_email);
        pwd_login = (EditText) findViewById(R.id.input_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_login.getText().toString().trim();
                String pwd = pwd_login.getText().toString().trim();
                //String 형 변수 email.pwd(edittext에서 받오는 값)으로 로그인하는것
                firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {//성공했을때
                                    Intent intent = new Intent(LoginActivity.this, Diary_Home.class);
                                    startActivity(intent);
                                    finish();
                                } else {//실패했을때
                                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}