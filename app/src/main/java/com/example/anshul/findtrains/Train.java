package com.example.anshul.findtrains;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Train extends Activity {
    StringBuilder sb;
    EditText et, et1;
    Button a, b;
    TextView tv;
    String res, num;
    List<String> number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        tv = (TextView) findViewById(R.id.textView);
        a = (Button) findViewById(R.id.button3);
        et = (EditText) findViewById(R.id.editText2);
        et1 = (EditText) findViewById(R.id.editText3);

        a.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     try {
                                         String res = new Task().execute("http://api.railwayapi.com/between/source/" + et.getText().toString() + "/dest/" + et1.getText().toString() + "/date/27-11-2015/apikey/makkf7604/").get();





                                         JSONObject jsonObject = new JSONObject(res);
                                         JSONArray jsonArray = jsonObject.getJSONArray("train");

                                         for (int i = 0; i < jsonArray.length(); i++) {
                                             JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                             StringBuilder sb = new StringBuilder();

                                             sb.append(jsonObject1.getString("name") + "\n" +
                                                             jsonObject1.getString("number") + "\n" +
                                                             jsonObject1.getString("dest_arrival_time") + "\n" +
                                                             jsonObject1.getString("src_departure_time") + "\n" +
                                                             jsonObject1.getJSONObject("to").getString("name") + "\n" +
                                                             jsonObject1.getJSONObject("from").getString("name") + "\n"
                                             );


                                         }


                                         tv.setText(sb.toString());

                                         //Toast.makeText(TrainBetween.this, "" + train, Toast.LENGTH_SHORT).show();


                                     } catch (Exception e) {

                                     }

                                 }
                             }


        );


    }


    public String getRoute(String source, String Destination) {
        try {
            URL url = new URL("");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.connect();
            InputStream is = huc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            sb = new StringBuilder();
            String brVal = " ";
            while ((brVal = br.readLine()) != null) {
                sb.append(brVal);
            }


        } catch (Exception e) {

        }
        return sb.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_train, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class Task extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String response = getRoute(params[0], params[1]);
            return response;
        }
    }
}
