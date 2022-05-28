package com.example.webchatclient;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WorkingWithData {
    public static String url = "https://pochatimsya.azurewebsites.net/WebChatServer";
    //public static String url = "http://192.168.0.101:8080/WebChatServer";
    ObjectMapper mapper = new ObjectMapper();
    public static String nick = "test";
    public static String key = null;
    public static String version = "1.0";



    public Message[] POST_Json(String path, String param) throws InterruptedException {
        Message[] content = new Message[0];
        try {
            HttpURLConnection cn = (HttpURLConnection) new URL(url + path).openConnection();
            cn.setDoInput(true);
            cn.setDoOutput(true);
            cn.getOutputStream().write(param.getBytes(StandardCharsets.UTF_8));
            //InputStream IS = cn.getInputStream();

            content = mapper.readValue(cn.getInputStream(), Message[].class);


//            ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
//            byte[] b = new byte[1024];
//            while (true) {
//                int cnt = IS.read(b, 0, b.length);
//                if (cnt == -1) break;
//                BAOS.write(b, 0, cnt);
//            }
//            byte[] a = BAOS.toByteArray();
//            BAOS.reset();
//            content = new String(a, 0, a.length, "UTF8");
            cn.disconnect();
        } catch (Exception e) {
            Log.d("TEG_POST_e", e.toString());
        }
        return content;
    }

    public String POST(String path, String param) throws InterruptedException {
        String content = "";
        try {
            HttpURLConnection cn = (HttpURLConnection) new URL(url + path).openConnection();
            cn.setDoInput(true);
            cn.setDoOutput(true);
            cn.getOutputStream().write(param.getBytes(StandardCharsets.UTF_8));
            InputStream IS = cn.getInputStream();
            ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            while (true) {
                int cnt = IS.read(b, 0, b.length);
                if (cnt == -1) break;
                BAOS.write(b, 0, cnt);
            }
            byte[] a = BAOS.toByteArray();
            BAOS.reset();
            content = new String(a, 0, a.length, "UTF8");
            cn.disconnect();
        } catch (Exception e) {
            Log.d("TEG_POST_e", e.toString());
        }
        return content;
    }

    public String GET(String path) {
        String content = "";

        try {
            HttpURLConnection cn = (HttpURLConnection) new URL(url + path).openConnection();
            cn.setDoInput(true);
            cn.connect();
            InputStream IS = cn.getInputStream();
            ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            while (true) {
                int cnt = IS.read(b, 0, b.length);
                if (cnt == -1) break;
                BAOS.write(b, 0, cnt);
            }
            byte[] a = BAOS.toByteArray();
            BAOS.reset();
            content = new String(a, 0, a.length, "UTF8");
            cn.disconnect();
        } catch (Exception e) {
            Log.d("TEG_GET_e", e.toString());
        }
        return content;

    }

    public void setKey(String key){
        try(FileOutputStream outputStream = MainActivity.mainActivity.openFileOutput("USER_KEY", MainActivity.MODE_PRIVATE)) {
            // перевод строки в байты
            outputStream.write(key.getBytes());
        }
        catch(IOException e){
            Log.d("TEG_o", e.toString());
        }

    }

    public String getKey(){
        try(FileInputStream inputStream = MainActivity.mainActivity.openFileInput("USER_KEY"))
        {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return new String(bytes);
        }
        catch(IOException e){
            Log.d("TEG_i", e.toString());
            return null;
        }
    }


}
