package com.example.bego.api_mvvm_retrofit.GetTime;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DwnloadTask extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result=null;
        URL url;
        HttpURLConnection urlconnection;
        try {
            url=new URL(strings[0]);
            urlconnection=(HttpURLConnection) url.openConnection();

            InputStream in = urlconnection.getInputStream();
            InputStreamReader reader=new InputStreamReader(in);
            int data=reader.read();
            while (data!=-1)
            {
                char curd=(char)data;
                result+=curd;
                data=reader.read();

            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "failed";
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }

    }
}

