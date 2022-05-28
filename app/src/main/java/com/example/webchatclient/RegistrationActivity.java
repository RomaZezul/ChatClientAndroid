package com.example.webchatclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegistrationActivity extends AppCompatActivity {

    EditText nick;
    Button reg;
    Intent intent;
    WorkingWithData workingWithData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        intent = new Intent(this, MainActivity.class);
        nick = findViewById(R.id.nick);
        reg = findViewById(R.id.button2);
        workingWithData = new WorkingWithData();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(() -> {
                    try {
                        workingWithData.setKey(workingWithData.POST("/r", "nick="+nick.getText()));
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }
        });
    }

}