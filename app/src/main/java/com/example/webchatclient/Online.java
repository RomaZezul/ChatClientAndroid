package com.example.webchatclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Online extends AppCompatActivity {



    ListView listView;
    WorkingWithData workingWithData;
    ObjectMapper mapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        listView = findViewById(R.id.listOnl);
        workingWithData = new WorkingWithData();
        mapper  = new ObjectMapper();

        Thread thread = new Thread(() -> {
            try {
                User[] users = mapper.readValue(workingWithData.GET("/o"), User[].class);
                listView.post(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);
                    listView.setAdapter(adapter);
                });
            } catch (Exception e) {
                Log.d("TEG", e.toString());
            }
        });
        thread.start();
    }
}
