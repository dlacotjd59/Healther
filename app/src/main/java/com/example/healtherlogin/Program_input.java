package com.example.healtherlogin;

import static java.sql.DriverManager.println;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Program_input extends AppCompatActivity {
    EditText input_s,input_be,input_ba,input_o,input_d;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String squ,ben,bab,ove,dea;
    String UID = user.getUid();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.program_input);
        Intent intent = getIntent();

        input_s = (EditText)findViewById(R.id.input_squ);
        input_be = (EditText)findViewById(R.id.input_ben);
        input_ba = (EditText)findViewById(R.id.input_bab);
        input_o = (EditText)findViewById(R.id.input_ove);
        input_d = (EditText)findViewById(R.id.input_dea);

    }

    public void mNext(View view){
        squ = input_s.getText().toString();
        ben = input_be.getText().toString();
        bab = input_ba.getText().toString();
        ove = input_o.getText().toString();
        dea = input_d.getText().toString();
        saveRM(squ,ben,bab,ove,dea);
        Intent input1 = new Intent(Program_input.this, Program_show.class);
        input1.putExtra("UID",UID);
        startActivity(input1);
        finish();
    }

    public void saveRM(String squat, String bench, String babel, String over, String dead){
        RM_save saveRM = new RM_save(squat,bench,babel,over,dead);
        databaseReference.child("User").child(UID).setValue(saveRM);
    }

//    public void saveRM(boolean add){
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        Map<String, Object> childUpdates = new HashMap<>();
//        Map<String, Object> postValues = null;
//        if(add){
//            RM_save saveRM = new RM_save(squ,ben,bab,ove,dea);
//            postValues = saveRM.toMap();
//        }
//        childUpdates.put("/user/" + UID,postValues);
//        databaseReference.updateChildren(childUpdates);
//    }

    public void mClose(View v){
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed(){
        //안드로이드 백버튼 막기
        return;
    }


}
