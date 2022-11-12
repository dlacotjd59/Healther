package com.example.healtherlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Program extends AppCompatActivity {

    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
        Intent intent = getIntent();

    }


    public void input5RM(View v) {
        Intent input = new Intent(Program.this, Program_input.class);
        startActivity(input);
    }

    public void user(View v) {
        Intent show = new Intent(Program.this, Program_show.class);
        startActivity(show);

    }

    public void bmi(View v) {
        Intent bmi = new Intent(Program.this,Program_BMI.class);
        startActivity(bmi);
    }
}








