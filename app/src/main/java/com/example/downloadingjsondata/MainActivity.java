package com.example.downloadingjsondata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    EditText editText;
    TextView weatherView,temperatureView;


    private class DownloadJsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                Log.i("URL",url.toString());


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            try {
                JSONObject reader = new JSONObject(result);
                JSONArray weather=reader.getJSONArray("weather");

                weatherView.setText(weather.getJSONObject(0).getString("main")+":"+weather.getJSONObject(0).getString("description"));
                JSONObject main  = reader.getJSONObject("main");
                temperatureView.setText("Temperature:"+main.getString("temp")+":,feels like:"+main.getString("feels_like"));


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }







    public void Enter(View view)
    {

        String cityName=editText.getText().toString();
        Log.i("CITY NAME",editText.getText().toString());
        String API_KEY="5f4c020a3e284f0737295bf52f97286b";

        String url="https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid="+API_KEY;


        DownloadJsonTask task=new DownloadJsonTask();
        String result=null;
        try {
            result= task.execute(url).get();
         //   Log.i("RESULT",result+"");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editText);

        weatherView=findViewById(R.id.weather_textView);
        temperatureView=findViewById(R.id.temperature_textView);



    }
}