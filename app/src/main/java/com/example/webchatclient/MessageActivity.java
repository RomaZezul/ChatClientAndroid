package com.example.webchatclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;


public class MessageActivity extends AppCompatActivity {

    public static MessageActivity messageActivity;
    EditText message;
    ListView listView;
    Intent intentMainActivity;
    WorkingWithData workingWithData;
    Thread thread, updateMessages;
    Boolean updateMessages_flag;
    ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageActivity = this;
        mapper = new ObjectMapper();
        workingWithData = new WorkingWithData();
        message = findViewById(R.id.message);
        listView = findViewById(R.id.list);
        intentMainActivity = new Intent(this, MainActivity.class);

        updateMessages = new Thread(() -> {
            updateMessages_flag = true;
            while (updateMessages_flag) {
                try {
                    Message[] messages = workingWithData.POST_Json("/up", "key=" + WorkingWithData.key);
                    listView.post(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);
                        listView.setAdapter(adapter);
                        listView.setSelection(listView.getCount() - 1);
                    });
                    Thread.sleep(3000);
                } catch (Exception e) {
                    Log.d("TEG_updateMessages", e.toString());
                }
            }
        });
        updateMessages.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // updateMessages_flag = false;
    }

    @Override
    protected void onResume() {
        super.onResume();


        //if (updateMessages.isInterrupted() || !updateMessages.isAlive())
        //   updateMessages.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item2) {
            Intent intent = new Intent(this, Online.class);
            startActivity(intent);
        }
        return true;
    }

    public void onClick(View view) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                Message[] messages = workingWithData.POST_Json("/m", "nick=" + WorkingWithData.nick + "&message=" + message.getText());
                listView.post(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);
                    listView.setAdapter(adapter);
                    listView.setSelection(listView.getCount() - 1);
                    message.post(() -> {
                        message.setText("");
                    });
                });

            } catch (Exception e) {
                Log.d("TEG_sendMessages", e.toString());
            }
        });
        Log.d("TEG_nick", WorkingWithData.nick);

        if (WorkingWithData.nick.equals("") || WorkingWithData.nick == "test"){
            startActivity(intentMainActivity);
        }else {
            thread.start();
        }
    }
}