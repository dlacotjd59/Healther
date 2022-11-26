package com.example.healtherlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Recommend_Exercise extends AppCompatActivity {

    private final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String UID = user.getUid();

    private TextView a,b;
    private TextView GoldenSix_Text, Squat_Text,Neck_Text,Bench_Text,Barbell_Text;
    private TextView Squat_Weight,Neck_Weight,Bench_Weight,Barbell_Weight;

    private EditText set_squ,set_ben,set_neck,set_barbell;
    private Button add_squ,sub_squ,add_ben,sub_ben,add_neck,sub_neck,add_barbell,sub_barbell,FixButton;
    private Button set,start,info;
    private ConstraintLayout dialogView;

    private ImageView imageView;

    private Double dou_squ= 0.0; // 무게 어떻게 할지 물어보고 수정하기
    private Double dou_ben= 0.0;
    private Double dou_neck= 0.0;
    private Double dou_barbell= 0.0;

    private String[] weights = new String[4];
    private String str_squ,str_ben,str_neck,str_barbell;

    private Manage_Weights manage_weights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_exercise);

        Intent intent = getIntent();
        String BMI_Type = intent.getExtras().getString("strBMI_Type");
        String Gender = intent.getExtras().getString("Gender");
        Double BMI = intent.getExtras().getDouble("BMI");


        String[] weights = new String[4];

        /* dou_squ = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_squ)) / 10); // 팀원들에게 물어보고 수정할 것
        dou_ben = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_ben) )/ 10);
        dou_neck = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_neck)) / 10);
        dou_barbell = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_barbell)) / 10); */

        a = (TextView) findViewById(R.id.BMI);
        b = (TextView) findViewById(R.id.Recommend_Exercise);

        set = (Button) findViewById(R.id.Setting_Weight);
        start = (Button) findViewById(R.id.Exercise_Start);
        info = (Button) findViewById(R.id.info);

        GoldenSix_Text = (TextView) findViewById(R.id.GoldenSIx_Weights);
        Squat_Text = (TextView) findViewById(R.id.SquatText);
        Neck_Text = (TextView) findViewById(R.id.NeckText);
        Bench_Text = (TextView) findViewById(R.id.BenchText);
        Barbell_Text = (TextView) findViewById(R.id.CurlText);

        Squat_Weight = (TextView) findViewById(R.id.Weight_Squat);
        Neck_Weight = (TextView) findViewById(R.id.Weight_Neck);
        Bench_Weight = (TextView) findViewById(R.id.Weight_Bench);
        Barbell_Weight = (TextView) findViewById(R.id.Weight_Curl);

        imageView = (ImageView) findViewById(R.id.imageView);

        a.setText(BMI_Type);
        if(BMI<25){
            b.setText("추천 운동: 골든 식스");

                databaseReference.child("User").child(user.getUid()).child("골든식스무게").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            manage_weights = dataSnapshot.getValue(Manage_Weights.class);
                            str_squ = manage_weights.getSquat();
                            str_ben = manage_weights.getBench();
                            str_neck = manage_weights.getNeck();
                            str_barbell = manage_weights.getCurl();
                            weights[0] = str_squ;
                            weights[1] = str_ben;
                            weights[2] = str_neck;
                            weights[3] = str_barbell;
                            Squat_Weight.setText(weights[0] + "kg");
                            Bench_Weight.setText(weights[1] + "kg");
                            Neck_Weight.setText(weights[2] + "kg");
                            Barbell_Weight.setText(weights[3] + "kg");
                        }catch (NullPointerException e){
                            Toast.makeText(Recommend_Exercise.this, "저장된 무게가 없으니 무게를 설정해 주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }

                });


            imageView.setImageResource(R.drawable.arnold);

            set.setOnClickListener(new View.OnClickListener() { // 골든식스의 무게를 수정하는 팝업창 생성
               @Override
                public void onClick(View view) {
                    dialogView= (ConstraintLayout) View.inflate(Recommend_Exercise.this,R.layout.setting_weight,null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Recommend_Exercise.this);
                    AlertDialog Set_Weight = builder.create();
                    Set_Weight.setView(dialogView);

                   set_squ = (EditText) dialogView.findViewById(R.id.Set_Squat);
                   set_ben = (EditText) dialogView.findViewById(R.id.Set_Bench);
                   set_neck = (EditText) dialogView.findViewById(R.id.Set_Neck);
                   set_barbell = (EditText) dialogView.findViewById(R.id.Set_Barbell);

                   add_squ = (Button) dialogView.findViewById(R.id.add_squat_weight);
                   add_ben = (Button) dialogView.findViewById(R.id.add_bench_weight);
                   add_neck = (Button) dialogView.findViewById(R.id.add_neck_weight);
                   add_barbell = (Button) dialogView.findViewById(R.id.add_barbell_weight);
                   sub_squ = (Button) dialogView.findViewById(R.id.substract_squat_weight);
                   sub_ben = (Button) dialogView.findViewById(R.id.substract_bench_weight);
                   sub_neck = (Button) dialogView.findViewById(R.id.substract_neck_weight);
                   sub_barbell = (Button) dialogView.findViewById(R.id.substract_barbell_weight);
                   FixButton =(Button) dialogView.findViewById(R.id.FIxButton);

                   if(manage_weights != null){
                       set_squ.setText(weights[0]);
                       set_ben.setText(weights[1]);
                       set_neck.setText(weights[2]);
                       set_barbell.setText(weights[3]);
                   }else{
                       set_squ.setText(String.format(Locale.getDefault(),"%.1f",dou_squ));
                       set_ben.setText(String.format(Locale.getDefault(),"%.1f",dou_ben));
                       set_neck.setText(String.format(Locale.getDefault(),"%.1f",dou_neck));
                       set_barbell.setText(String.format(Locale.getDefault(),"%.1f",dou_barbell));
                   }


                   //각 버튼별 중량 조절 기능 추가
                   add_squ.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_squ = set_squ.getText().toString();
                           dou_squ = Double.parseDouble(str_squ);
                           dou_squ +=2.5;
                           str_squ=String.format("%.1f",dou_squ);
                           set_squ.setText(str_squ);
                       }
                   });

                   sub_squ.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_squ = set_squ.getText().toString();
                           dou_squ = Double.parseDouble(str_squ);
                           dou_squ -=2.5;
                           if(dou_squ<0){
                               Toast.makeText(Recommend_Exercise.this, "더 이상 줄일 수 없습니다", Toast.LENGTH_SHORT).show();
                               dou_squ +=2.5;
                           }else {
                               str_squ = String.format(Locale.getDefault(),"%.1f", dou_squ);
                               set_squ.setText(str_squ);
                           }
                       }
                   });

                   add_ben.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_ben = set_ben.getText().toString();
                           dou_ben = Double.parseDouble(str_ben);
                           dou_ben +=2.5;
                           str_ben=String.format(Locale.getDefault(),"%.1f",dou_ben);
                           set_ben.setText(str_ben);
                       }
                   });

                   sub_ben.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_ben = set_ben.getText().toString();
                           dou_ben = Double.parseDouble(str_ben);
                           dou_ben -=2.5;
                           if(dou_ben<0){
                               Toast.makeText(Recommend_Exercise.this, "더 이상 줄일 수 없습니다", Toast.LENGTH_SHORT).show();
                               dou_ben +=2.5;
                           }else {
                               str_ben = String.format(Locale.getDefault(),"%.1f", dou_ben);
                               set_ben.setText(str_ben);
                           }
                       }
                   });

                   add_neck.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_neck = set_neck.getText().toString();
                           dou_neck = Double.parseDouble(str_neck);
                           dou_neck +=2.5;
                           str_neck=String.format(Locale.getDefault(),"%.1f",dou_neck);
                           set_neck.setText(str_neck);
                       }
                   });

                   sub_neck.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_neck = set_neck.getText().toString();
                           dou_neck = Double.parseDouble(str_neck);
                           dou_neck -=2.5;
                           if(dou_neck<0){
                               Toast.makeText(Recommend_Exercise.this, "더 이상 줄일 수 없습니다", Toast.LENGTH_SHORT).show();
                               dou_neck +=2.5;
                           }else {
                               str_neck = String.format(Locale.getDefault(),"%.1f", dou_neck);
                               set_neck.setText(str_neck);
                           }
                       }
                   });

                   add_barbell.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_barbell = set_barbell.getText().toString();
                           dou_barbell = Double.parseDouble(str_barbell);
                           dou_barbell +=2.5;
                           str_barbell=String.format(Locale.getDefault(),"%.1f",dou_barbell);
                           set_barbell.setText(str_barbell);
                       }
                   });

                   sub_barbell.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_barbell = set_barbell.getText().toString();
                           dou_barbell = Double.parseDouble(str_barbell);
                           dou_barbell -=2.5;
                           if(dou_barbell<0){
                               Toast.makeText(Recommend_Exercise.this, "더 이상 줄일 수 없습니다", Toast.LENGTH_SHORT).show();
                               dou_barbell +=2.5;
                           }else {
                               str_barbell = String.format(Locale.getDefault(),"%.1f", dou_barbell);
                               set_barbell.setText(str_barbell);
                           }
                       }
                   });


                   FixButton.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(str_squ.equals("0.0") || str_ben.equals("0.0") || str_neck.equals("0.0") || str_barbell.equals("0.0") ){
                               Toast.makeText(Recommend_Exercise.this, "무게가 0인 운동이 있습니다. 무게를 0보다 높게 설정하세요.", Toast.LENGTH_SHORT).show();
                           }else{
                               str_squ = set_squ.getText().toString();
                               str_ben = set_ben.getText().toString();
                               str_neck = set_neck.getText().toString();
                               str_barbell = set_barbell.getText().toString();

                               weights[0] = str_squ;
                               weights[1] = str_ben;
                               weights[2] = str_neck;
                               weights[3] = str_barbell;

                               Squat_Weight.setText(weights[0]+"kg");
                               Bench_Weight.setText(weights[1]+"kg");
                               Neck_Weight.setText(weights[2]+"kg");
                               Barbell_Weight.setText(weights[3]+"kg");

                               Manage_Weights Init_Weights = new Manage_Weights(weights[0],weights[1],weights[2],weights[3]);
                               databaseReference.child("User").child(UID).child("골든식스무게").setValue(Init_Weights);
                               Set_Weight.dismiss();
                               Toast.makeText(Recommend_Exercise.this, "수정됐습니다.", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

                    Set_Weight.show();

                }
            });


            /* 골든 식스를 바로 시작*/
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(manage_weights == null){
                        Toast.makeText(Recommend_Exercise.this, "무게를 설정해 주세요", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent start_ex = new Intent(Recommend_Exercise.this, Golden_Six.class);
                        start_ex.putExtra("Weights", weights);
                        startActivity(start_ex);
                    }
                }
            });


            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }


        else{ // 유산소 운동 부분
            b.setText("추천 운동: 유산소 운동");
            //imageView.setImageResource(R.drawable.arnold); 이미지 구해서 변경해야 할 부분

            GoldenSix_Text.setVisibility(View.INVISIBLE);
            Squat_Text.setVisibility(View.INVISIBLE);
            Neck_Text.setVisibility(View.INVISIBLE);
            Bench_Text.setVisibility(View.INVISIBLE);
            Barbell_Text.setVisibility(View.INVISIBLE);
            Squat_Weight.setVisibility(View.INVISIBLE);
            Neck_Weight.setVisibility(View.INVISIBLE);
            Bench_Weight.setVisibility(View.INVISIBLE);
            Barbell_Weight.setVisibility(View.INVISIBLE);;

            set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Recommend_Exercise.this, "수정없이 바로 시작하세요", Toast.LENGTH_LONG).show();
                }
            });

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent start_ex = new Intent(Recommend_Exercise.this,Aerobic.class);
                    startActivity(start_ex);
                }
            });

            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }



        //바텀 네비게이션
        BottomNavigationView bottom_navi = (BottomNavigationView) findViewById(R.id.bottom_navi);
        bottom_navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.Diary){
                    Intent Diary= new Intent(Recommend_Exercise.this, Diary_Home.class);
                    startActivity(Diary);
                }else if(item.getItemId()==R.id.Recommendation){
                    Intent Recommendation= new Intent(Recommend_Exercise.this, Program_BMI.class);
                    Recommendation.putExtra("Gender",Gender);
                    startActivity(Recommendation);
                }

                return false;

            }
        });


    }
}
