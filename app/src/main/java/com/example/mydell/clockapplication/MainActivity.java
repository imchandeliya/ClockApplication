package com.example.mydell.clockapplication;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
    Button button31;
    Button button21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        final Context context=this;
        button31=(Button) findViewById(R.id.button3);
        button21=(Button) findViewById(R.id.button2);
        button21.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(context, AlarmActivity.class);
                startActivity(intent1);
            }
        });
        button31.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, StopWatchActivity.class);
                startActivity(intent);
            }
        });
    }

}
