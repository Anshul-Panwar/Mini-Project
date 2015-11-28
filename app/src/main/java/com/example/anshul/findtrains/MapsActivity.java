package com.example.anshul.findtrains;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import static com.example.anshul.findtrains.MainActivity.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void getLatLong(String number) {




        List<String> lat = new ArrayList<String>();
        List<String> code = new ArrayList<String>();
        List<String> state = new ArrayList<String>();
        List<String> lng = new ArrayList<String>();
        List<String> fullname = new ArrayList<String>();

        try{// start
            String response = new Task().execute("http://api.railwayapi.com/route/train/" + number + "/apikey/cfpwq4935/").get();
            JSONObject jsonObject = new JSONObject(response);
            StringBuilder sb1 = new StringBuilder();
            JSONArray jsonArray = jsonObject.getJSONArray("route");
            JSONObject jsonObject2 = jsonObject.getJSONObject("train");
            JSONArray jsonArray2 = jsonObject2.getJSONArray("days");
            for (int x = 0; x < jsonArray.length(); x++) {

                lat.add(jsonArray.getJSONObject(x).getString("lat"));
                state.add(jsonArray.getJSONObject(x).getString("state"));
                lng.add(jsonArray.getJSONObject(x).getString("lng"));
                fullname.add(jsonArray.getJSONObject(x).getString("fullname"));



            }
        }
        catch(Exception e)
        {





        }



        for(int i=0;i<lat.size();i++) {
            Toast.makeText(MapsActivity.this, "" + lat.get(i), Toast.LENGTH_LONG
            ).show();
            if(Double.parseDouble(lat.get(i))!=0.0&&!fullname.get(i).contains("NEW DELHI")) {
                if (i == 0) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lng.get(i))), 10));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lng.get(i)))).title(fullname.get(i)).snippet(state.get(i)));
                } else {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lng.get(i)))).title(fullname.get(i)).snippet(state.get(i)));

                }
            }
            else if(fullname.get(i).contains("NEW DELHI"))
            {
                lat.set(i,"28.643");
                lng.set(i,"77.222");
                state.set(i,"Delhi");
                mMap.addMarker(new MarkerOptions().position(new LatLng(28.643,77.222)).title(fullname.get(i)).snippet(state.get(i)));

            }


        }





        for(int i=0;i<lat.size()-1;)
        {

            int x=i;
            int y;
            if(Double.parseDouble(lat.get(i+1))!=0.0) {

                y=i+1;
            }
            else
            {
                y=i+2;
            }




            mMap.addPolyline(new PolylineOptions().add(new LatLng(Double.parseDouble(lat.get(x)),Double.parseDouble(lng.get(x))),new LatLng(Double.parseDouble(lat.get(y)),Double.parseDouble(lng.get(y)))));
            i=y;

        }
    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String response = getIntent().getExtras().getString("response");
       // Toast.makeText(MapsActivity.this, response, Toast.LENGTH_SHORT).show();
        getLatLong(response);

    }
    public String getRoute(String tnumber) {
        try {

            URL url = new URL(tnumber);
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

    public class Task extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String response = getRoute(params[0]);
            return response;
        }
    }
}
