package com.blucor.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {

            //check network connection
           if(isNetworkConnected())
            {
                //call webservice
                new UpdateTask().execute();
            }
           else
           {
               Toast.makeText(this,"Check your internet connection" ,Toast.LENGTH_SHORT).show();
           }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private class UpdateTask extends AsyncTask<String, String,String> {
        protected String doInBackground(String... urls) {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://codejudge-artifacts.s3.amazonaws.com/images/q-110/data.json")
                    .method("GET", null)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("resss",  ""+response.message());

            return null;
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    }