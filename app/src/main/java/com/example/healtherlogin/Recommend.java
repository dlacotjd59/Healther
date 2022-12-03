package com.example.healtherlogin;

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

public class Recommend extends AppCompatActivity {

    private final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String UID = user.getUid();

    private TextView bmi_type, recommend;
    private TextView GoldenSix_Text, Squat_Text,Neck_Text,Bench_Text,Barbell_Text;
    private TextView Squat_Weight,Neck_Weight,Bench_Weight,Barbell_Weight;

    private EditText set_squ,set_ben,set_neck,set_barbell;
    private Button add_squ,sub_squ,add_ben,sub_ben,add_neck,sub_neck,add_barbell,sub_barbell,FixButton;
    private Button set,start,info;
    private ConstraintLayout dialogView;

    private ImageView imageView;

    private String str_squ,str_ben,str_neck,str_barbell;

    private Manage_Weights manage_weights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_ui);

        Intent intent = getIntent();
        String BMI_Type = intent.getExtras().getString("BMI_Type");
        String Gender = intent.getExtras().getString("Gender");
        Double BMI = intent.getExtras().getDouble("BMI");
        String BMI_Image = intent.getExtras().getString("BMI_Image");

        String[] weights = new String[4];

        /* dou_squ = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_squ)) / 10); // 팀원들에게 물어보고 수정할 것
        dou_ben = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_ben) )/ 10);
        dou_neck = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_neck)) / 10);
        dou_barbell = Math.ceil((Double.parseDouble(strWeight)+Double.parseDouble(str_barbell)) / 10); */

        bmi_type = (TextView) findViewById(R.id.BMI);
        recommend = (TextView) findViewById(R.id.Recommend_Exercise);

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
        switch (BMI_Image) {
            case "male1":
                imageView.setImageResource(R.drawable.male1);
                break;
            case "female1":
                imageView.setImageResource(R.drawable.female1);
                break;
            case "male2":
                imageView.setImageResource(R.drawable.male2);
                break;
            case "female2":
                imageView.setImageResource(R.drawable.female2);
                break;
            case "male3":
                imageView.setImageResource(R.drawable.male3);
                break;
            case "female3":
                imageView.setImageResource(R.drawable.female3);
                break;
            case "male4":
                imageView.setImageResource(R.drawable.male4);
                break;
            case "female4":
                imageView.setImageResource(R.drawable.female4);
                break;
            case "male5":
                imageView.setImageResource(R.drawable.male5);
                break;
            case "female5":
                imageView.setImageResource(R.drawable.female5);
                break;
        }

        bmi_type.setText(BMI_Type);
        if (BMI >= 25) { // 유산소 운동 부분
            recommend.setText("추천 운동: 유산소 운동");
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
                    Toast.makeText(Recommend.this, "수정없이 바로 시작하세요", Toast.LENGTH_LONG).show();
                }
            });

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent start_ex = new Intent(Recommend.this,Aerobic.class);
                    startActivity(start_ex);
                }
            });

            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        } else {
            recommend.setText("추천 운동: 골든 식스");

                databaseReference.child(user.getUid()).child("골든식스무게").addValueEventListener(new ValueEventListener() {
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
                            Toast.makeText(Recommend.this, "저장된 무게가 없으니 무게를 설정해 주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }

                });

            set.setOnClickListener(new View.OnClickListener() { // 골든식스의 무게를 수정하는 팝업창 생성
               @Override
                public void onClick(View view) {
                    dialogView= (ConstraintLayout) View.inflate(Recommend.this,R.layout.setting_weight,null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Recommend.this);
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
                       set_squ.setText(String.format(Locale.getDefault(),"%.1f",0.0));
                       set_ben.setText(String.format(Locale.getDefault(),"%.1f",0.0));
                       set_neck.setText(String.format(Locale.getDefault(),"%.1f",0.0));
                       set_barbell.setText(String.format(Locale.getDefault(),"%.1f",0.0));
                   }


                   //각 버튼별 중량 조절 기능 추가
                   add_squ.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_squ = plus_minus_weight.plus(set_squ.getText().toString());

                           set_squ.setText(str_squ);
                       }
                   });

                   sub_squ.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_squ = plus_minus_weight.minus(set_squ.getText().toString());
                           if(str_squ.equals("0.0")) {
                               Toast.makeText(Recommend.this, "더 이상 줄일 수 없습니다", Toast.LENGTH_SHORT).show();
                           }
                           set_squ.setText(str_squ);
                       }
                   });

                   add_ben.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_ben = plus_minus_weight.plus(set_ben.getText().toString());

                           set_ben.setText(str_ben);
                       }
                   });

                   sub_ben.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_ben = plus_minus_weight.minus(set_ben.getText().toString());
                           if(str_ben.equals("0.0")) {
                               Toast.makeText(Recommend.this, "더 이상 줄일 수 없습니다", Toast.LENGTH_SHORT).show();
                           }
                           set_ben.setText(str_ben);
                       }
                   });

                   add_neck.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_neck = plus_minus_weight.plus(set_neck.getText().toString());

                           set_neck.setText(str_neck);
                       }
                   });

                   sub_neck.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_neck = plus_minus_weight.minus(set_neck.getText().toString());
                           if (str_neck.equals("0.0")) {
                               Toast.makeText(Recommend.this, "더 이상 줄일 수 없습니다", Toast.LENGTH_SHORT).show();
                           }
                           set_neck.setText(str_neck);
                       }

                   });

                   add_barbell.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_barbell = plus_minus_weight.plus(set_barbell.getText().toString());

                           set_barbell.setText(str_barbell);
                       }
                   });

                   sub_barbell.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           str_barbell = plus_minus_weight.minus(set_barbell.getText().toString());
                           if (str_barbell.equals("0.0")) {
                               Toast.makeText(Recommend.this, "더 이상 줄일 수 없습니다", Toast.LENGTH_SHORT).show();
                           }
                           set_barbell.setText(str_barbell);
                       }
                   });


                   FixButton.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(str_squ.equals("0.0") || str_ben.equals("0.0") || str_neck.equals("0.0") || str_barbell.equals("0.0") ){
                               Toast.makeText(Recommend.this, "무게가 0인 운동이 있습니다. 무게를 0보다 높게 설정하세요.", Toast.LENGTH_SHORT).show();
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
                               databaseReference.child(UID).child("골든식스무게").setValue(Init_Weights);
                               Set_Weight.dismiss();
                               Toast.makeText(Recommend.this, "수정됐습니다.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Recommend.this, "무게를 설정해 주세요", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent start_ex = new Intent(Recommend.this, Golden_Six.class);
                        start_ex.putExtra("Weights", weights);
                        startActivity(start_ex);
                    }
                }
            });


        }
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //바텀 네비게이션
        BottomNavigationView bottom_navi = (BottomNavigationView) findViewById(R.id.bottom_navi);
        bottom_navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.Diary){
                    Intent Diary= new Intent(Recommend.this, Diary_Home.class);
                    startActivity(Diary);
                }else if(item.getItemId()==R.id.Recommendation){
                    Toast.makeText(Recommend.this, "현재 화면입니다", Toast.LENGTH_SHORT).show();
                }

                return false;

            }
        });


    }
}
