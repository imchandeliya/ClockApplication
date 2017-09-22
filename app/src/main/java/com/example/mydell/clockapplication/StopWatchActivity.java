package com.example.mydell.clockapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StopWatchActivity extends AppCompatActivity {

    TextView tv_time;
    ImageView iv_start, iv_lap;
    ImageView iv_seconds;

    int buttonState;
    String laps;
    int lapsCount;

    private Handler mHandler;
    private boolean mStarted;

    long start_time;
    long old_degree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        tv_time=(TextView) findViewById(R.id.tv_time);
        iv_start=(ImageView) findViewById(R.id.iv_start);
        iv_lap=(ImageView) findViewById(R.id.iv_lap);
        iv_seconds=(ImageView) findViewById(R.id.iv_seconds);

        mHandler=new Handler();

        iv_start.setEnabled(true);
        buttonState=1;
        laps="";
        lapsCount=0;

        iv_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(buttonState==1){
                    mStarted=true;
                    mHandler.postDelayed(mRunnable, 10L);
                    start_time=System.currentTimeMillis();
                    laps="";

                    iv_start.setImageResource(R.drawable.stop);
                    buttonState=2;
                }else if(buttonState==2){
                    mStarted=false;
                    mHandler.removeCallbacks(mRunnable);

                    iv_start.setImageResource(R.drawable.a);
                    buttonState=3;
                }else if(buttonState==3){
                    rotate(old_degree, 360);
                    old_degree=0;
                    tv_time.setText(String.format("%02d:%02d:%02d", 0, 0, 0));
                    laps="";
                    lapsCount=1;

                    iv_start.setImageResource(R.drawable.play);
                    buttonState=1;
                }
            }
        });

        iv_lap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mStarted){
                    lapsCount++;
                    laps=laps+String.valueOf(lapsCount)+". "+tv_time.getText().toString() + "\n";
                    Toast.makeText(StopWatchActivity.this, "lap!", Toast.LENGTH_SHORT).show();
                } else{
                    if(laps.equalsIgnoreCase("")){
                        Toast.makeText(StopWatchActivity.this, "Empty!", Toast.LENGTH_SHORT).show();
                    } else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StopWatchActivity.this);
                        alertDialogBuilder.setMessage(laps);
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog=alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
            }
        });
    }
    private void rotate(float from_degree, float to_degree){
        RotateAnimation rotateAnimation= new RotateAnimation(from_degree,to_degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        iv_seconds.startAnimation(rotateAnimation);
    }
    private final Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            if(mStarted){
                long millis=(System.currentTimeMillis() - start_time);
                long seconds= millis/1000;
                tv_time.setText(String.format("%02d:%02d:%02d",seconds/60,seconds%60,millis%100));
                rotate(old_degree, millis*3/500);
                mHandler.postDelayed(mRunnable,101);
                old_degree= millis*3/500;
            }
        }
    };
}
