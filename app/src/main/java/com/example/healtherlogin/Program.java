package com.example.healtherlogin;

import static java.sql.DriverManager.println;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

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
}








