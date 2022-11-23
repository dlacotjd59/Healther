package com.example.healtherlogin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Golden_Six_Home extends AppCompatActivity {

    private String strBMI,strWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.golden_six_program);

        Intent intent = getIntent();
        strBMI = intent.getStringExtra("BMI");
        strWeight = intent.getStringExtra("Weight");

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Input_Weight(View v) {
        Intent input = new Intent(Golden_Six_Home.this,Golden_Six_Input.class);
        input.putExtra("BMI",strBMI);
        input.putExtra("Weight",strWeight);
        startActivity(input);
    }

    public void Continue(View v) {
        Intent Continue = new Intent(Golden_Six_Home.this,Golden_Six.class);
        startActivity(Continue);
    }

}
