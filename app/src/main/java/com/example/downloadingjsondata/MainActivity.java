package com.example.downloadingjsondata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    public class DownloadJsonTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... urls) {
            URL url=null;
            HttpURLConnection httpURLConnection=null;
            InputStreamReader inputStreamReader=null;
            int data = 0;
            String res="";


            try {
                url=new URL(urls[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                httpURLConnection= (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                data=inputStreamReader.read();

                while (data!=-1)
                {
                    char curr=(char) data;
                    res+=curr;
                    data=inputStreamReader.read();

                }
                // Log.i("URL",urls[0]+" "+urls[1]);
                return res;


            } catch (Exception e) {
                e.printStackTrace();
            }






            return "FAILED";
        }
    }


    public void Enter(View view)
    {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}