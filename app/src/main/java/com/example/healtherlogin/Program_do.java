package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Program_do extends AppCompatActivity {
    TextView health1,health2,health3;
    CheckBox h1c1,h1c2,h1c3,h1c4,h1c5,h2c1,h2c2,h2c3,h2c4,h2c5,h3c1,h3c2,h3c3,h3c4,h3c5;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    String UID,type;
    double[] info= {0,0,0,0,0};
    Calendar cal = Calendar.getInstance();
    Date today = Calendar.getInstance().getTime();
    SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
    String date = yyyymmdd.format(today);
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.program_do);

        health1=(TextView)findViewById(R.id.health1);
        health2=(TextView)findViewById(R.id.health2);
        health3=(TextView)findViewById(R.id.health3);
        h1c1=(CheckBox)findViewById(R.id.h1c1);
        h1c2=(CheckBox)findViewById(R.id.h1c2);
        h1c3=(CheckBox)findViewById(R.id.h1c3);
        h1c4=(CheckBox)findViewById(R.id.h1c4);
        h1c5=(CheckBox)findViewById(R.id.h1c5);
        h2c1=(CheckBox)findViewById(R.id.h2c1);
        h2c2=(CheckBox)findViewById(R.id.h2c2);
        h2c3=(CheckBox)findViewById(R.id.h2c3);
        h2c4=(CheckBox)findViewById(R.id.h2c4);
        h2c5=(CheckBox)findViewById(R.id.h2c5);
        h3c1=(CheckBox)findViewById(R.id.h3c1);
        h3c2=(CheckBox)findViewById(R.id.h3c2);
        h3c3=(CheckBox)findViewById(R.id.h3c3);
        h3c4=(CheckBox)findViewById(R.id.h3c4);
        h3c5=(CheckBox)findViewById(R.id.h3c5);

        //데이터 가져오기
        Intent intent=getIntent();
        UID = intent.getStringExtra("UID");
        type = intent.getStringExtra("type");
        info= intent.getDoubleArrayExtra("info");

        if (type.equals("A")){
            health1.setText("스쿼트" + info[0]);
            health2.setText("오버헤드프레스"+info[3]);
            health3.setText("데드리프트"+info[4]);
        }
        else{
            health1.setText("스쿼트"+info[0]);
            health2.setText("벤치프레스"+info[1]);
            health3.setText("바벨로우"+info[2]);
        }


      //  txtText.setText(squat);
    }

    public void OnComplete(View v){
        if(h1c1.isChecked()){
            if(h1c2.isChecked()){
                if(h1c3.isChecked()){
                    if(h1c4.isChecked()){
                        if(h1c5.isChecked()){
                            if(type.equals("A")){
                                info[0]=info[0]+2.5;
                            }
                            else{
                                info[0]=info[0]+2.5;
                            }
                        }
                    }
                }
            }
        }
        if(h2c1.isChecked()){
            if(h2c2.isChecked()){
                if(h2c3.isChecked()){
                    if(h2c4.isChecked()){
                        if(h2c5.isChecked()){
                            if(type.equals("A")){
                                info[3]=info[3]+2.5;
                            }
                            else{
                                info[1]=info[1]+2.5;
                            }
                        }
                    }
                }
            }
        }
        if(h3c1.isChecked()){
            if(h3c2.isChecked()){
                if(h3c3.isChecked()){
                    if(h3c4.isChecked()){
                        if(h3c5.isChecked()){
                            if(type.equals("A")){
                                info[4]=info[4]+5;
                            }
                            else{
                                info[2]=info[2]+2.5;
                            }
                        }
                    }
                }
            }
        }
        if(type.equals("A")){
            type="B";
        }
        else{
            type="A";
        }
//        date="20221108";

        saveRM(String.valueOf(info[0]*2),String.valueOf(info[1]*2),String.valueOf(info[2]*2),String.valueOf(info[3]*2),String.valueOf(info[4]*2));
        savedinfo(type,date);

        Intent complete = new Intent(Program_do.this, Program.class);
        startActivity(complete);
    }
    public void saveRM(String squat, String bench, String babel, String over, String dead){
        RM_save saveRM = new RM_save(squat,bench,babel,over,dead);
        databaseReference.child("User").child(UID).child("5RM").setValue(saveRM);
    }
    public void savedinfo(String type, String date){
        Routine_save savedinfo = new Routine_save(type,date);
        databaseReference.child("User").child(UID).child(date).setValue(savedinfo);
    }
}
