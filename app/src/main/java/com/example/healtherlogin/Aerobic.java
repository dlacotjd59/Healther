package com.example.healtherlogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Aerobic extends AppCompatActivity {

    private Button start, pause, finish;
    private ImageView running_image;
    private ProgressBar Time_Bar;

    private TextView time_record;
    private Chronometer tb;

    private Thread thread =null;
    private Boolean Running=true;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aerobic_running);

        start = (Button) findViewById(R.id.Start_Running);
        pause = (Button) findViewById(R.id.Pause_Running);
        finish = (Button) findViewById(R.id.Finish_Running);
        running_image = (ImageView) findViewById(R.id.running_image);
        Time_Bar = (ProgressBar) findViewById(R.id.time_bar);

        //time_record = (TextView) findViewById(R.id.textView11);

    }

    public void Start_Running(View v){

        thread = new Thread(new time_thread());
        thread.start();
    }

    public void Pause_Running(View v){

        Running = !Running;
        if(Running){
            pause.setText("일시정지");
        }else{
            pause.setText("계속하기");
        }
    }

    public void Finish_Running(View v){

        thread.interrupt();
        Intent Finish_Aerobic= new Intent(Aerobic.this, Diary_Home.class);
        startActivity(Finish_Aerobic);

    }


    public class time_thread implements Runnable{

        @Override
        public void run() {

            int tmp = 0;

            while (true){
                while (Running){
                    Message m = new Message();
                    m.arg1 = tmp++;
                    handler.sendMessage(m);

                    try{
                        Thread.sleep(50);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }


    public static Handler handler = new Handler(){
        @Override
            public void handleMessage(Message msg){
                int ms = msg.arg1%100;
                int sec= (msg.arg1/100)%60;
                int min = (msg.arg1/100)/60;
                String result = String.format("%03d:%02d:%02d",min,sec,ms);


             }

    };
}

