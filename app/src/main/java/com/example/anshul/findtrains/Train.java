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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Train extends Activity {
    StringBuilder sb;
    EditText et,et1;
    Button a, b;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        a = (Button) findViewById(R.id.button3);
        et=(EditText)findViewById(R.id.editText2);
        et1=(EditText)findViewById(R.id.editText3);

        a.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     try {
                                         String res=new Task().execute(et.getText().toString(),et1.getText().toString()).get();



                                     }catch(Exception e)
                                     {

                                     }

                                 }
                             }


        );



    }


    public String getRoute(String source,String Destination) {
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
            String response = getRoute(params[0],params[1]);
            return response;
        }
    }
}
