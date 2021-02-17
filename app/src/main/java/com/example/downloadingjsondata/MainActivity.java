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
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    //TextView txtJson;
   // ProgressDialog pd;

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

            arrayList.add(result);


            arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
            listView.setAdapter(arrayAdapter);
        }
    }




//    public class DownloadJsonTask extends AsyncTask<String,Void,String>
//    {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            URL url=null;
//            HttpURLConnection httpURLConnection=null;
//            InputStreamReader inputStreamReader=null;
//            int data = 0;
//            String res="";
//
//
//            try {
//                url=new URL(urls[0]);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                httpURLConnection= (HttpURLConnection) url.openConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                data=inputStreamReader.read();
//
//                while (data!=-1)
//                {
//                    char curr=(char) data;
//                    res+=curr;
//                    data=inputStreamReader.read();
//
//                }
//                // Log.i("URL",urls[0]+" "+urls[1]);
//                return res;
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//
//
//
//
//            return "FAILED";
//        }
//    }


    public void Enter(View view)
    {

        arrayList=new ArrayList<>();

        String cityName=editText.getText().toString();
        Log.i("CITY NAME",editText.getText().toString());
        String API_KEY="5f4c020a3e284f0737295bf52f97286b";

        String url="https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid="+API_KEY;


        DownloadJsonTask task=new DownloadJsonTask();
        String result=null;
        try {
            result= task.execute(url).get();
            Log.i("RESULT",result+"");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        arrayList.add(result);
//
//
//         arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
//        listView.setAdapter(arrayAdapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editText);
        listView=findViewById(R.id.listview);

    }
}