package com.example.webchatclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity;
    WorkingWithData workingWithData;
    Intent intentRegistration, intentMessage;
    TextView textView;
    Button button;

    String uriVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button3);
        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriVersion));
                startActivity(browserIntent);
                Log.d("TEG_version_url", uriVersion);

            }
        });
        mainActivity = this;
        workingWithData = new WorkingWithData();
        intentRegistration = new Intent(this, RegistrationActivity.class);
        intentMessage = new Intent(this, MessageActivity.class);
        WorkingWithData.key = workingWithData.getKey();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread(() -> {
            try {
                String s = workingWithData.POST("/android", "version=" + WorkingWithData.version);
                if (s.equals(WorkingWithData.version)) {
                    start();
                } else {
                    textView.post(()->{
                       textView.setText("Ваше приложение устарело обновите!");
                    });
                    uriVersion = workingWithData.GET("/android");
                    button.post(()->{
                        button.setVisibility(View.VISIBLE);
                    });
                }
            } catch (InterruptedException e) {
                Log.d("TEG_version", e.toString());
            }
        });
        thread.start();
    }

    void start() {
        if (WorkingWithData.key == null) {
            startActivity(intentRegistration);
        }
        try {
            Log.d("TEG_key", WorkingWithData.key.toString());
        } catch (Exception e) {
            Log.d("TEG_key", "null");
        }

        Thread thread = new Thread(() -> {
            try {
                String s = workingWithData.POST("/l", "key=" + WorkingWithData.key);
                if (s.equals("")) {
                    Thread.sleep(3000);
                    s = workingWithData.POST("/l", "key=" + WorkingWithData.key);
                    if (s.equals("")) {
                        startActivity(intentRegistration);
                    }
                } else {
                    WorkingWithData.nick = s;
                    startActivity(intentMessage);
                }
            } catch (InterruptedException e) {
                Log.d("TEG", e.toString());
            }
        });
        thread.start();
    }
}