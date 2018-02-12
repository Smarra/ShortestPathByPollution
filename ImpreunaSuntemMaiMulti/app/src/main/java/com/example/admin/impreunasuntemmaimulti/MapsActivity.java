package com.example.admin.impreunasuntemmaimulti;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText t1, t2;
    String n[], s[];
    public LatLng loc[];
    int i, k = 0;
    Button button;
    Bundle b;
    ArrayList<String> p;
    ArrayList<Integer> sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String a = intent.getStringExtra("key"); //if it's a string you stored.
        //String fbImage = intent.getStringExtra("image");
        Log.i("PRINT", a);

        StringTokenizer ss = new StringTokenizer(a);
        p = new ArrayList<>();
        while (ss.hasMoreTokens()) {
            p.add(ss.nextToken());
        }

        Log.i("SIZE", "" + p.size());


        ////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////

        b = savedInstanceState;
        setContentView(R.layout.activity_maps);

        final Button b1 = findViewById(R.id.buttonname1);
        final Button b2 = findViewById(R.id.buttonname2);
        final Button b3 = findViewById(R.id.buttonnam3);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);


        if(p.size() < 8 ) {
            b1.setVisibility(View.INVISIBLE);
            if (p.size() < 5) {
                b2.setVisibility(View.INVISIBLE);
                if(p.size() < 2 )
                {
                    b3.setVisibility(View.INVISIBLE);
                }
                else
                {
                    b3.setText(p.get(0).toString().substring(0,13));
                }
            }
            else
            {
                b3.setText(p.get(0).toString().substring(0,13));
                b2.setText(p.get(3).toString().substring(0,13));
            }
        }
        else
        {
            b3.setText(p.get(0).toString());
            b2.setText(p.get(3).toString());
            b1.setText(p.get(6).toString());
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        button = (Button) findViewById(R.id.buttonname);
        n = new String[3];
        s = new String[3];
        loc = new LatLng[3];
        final MapsActivity mps = this;

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = Toast.LENGTH_LONG;

                Button b = findViewById(R.id.buttonname2);


                String txt = "Ati ales evenimentul ";
                String txt1 = ". Urmariti culorile traseelor pentru o ruta curata si eficienta.";
                Toast toast = Toast.makeText(getBaseContext(), txt + b.getText() + txt1, duration);
                toast.show();
                //sub = new ArrayList<Integer>();
                sub = new ArrayList<Integer>();

                i = 0;
                k = -1;
                int ok = 1;
                b2.setVisibility(View.INVISIBLE);
                loc[1] = new LatLng(Double.parseDouble(p.get(4).toString()), Double.parseDouble(p.get(5).toString()));
                loc[0] = new LatLng(44.4377757, 26.0496109);

                String url = getDirectionsUrl(loc[0], loc[1]);
                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(mps);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sub = new ArrayList<Integer>();
                int duration = Toast.LENGTH_LONG;

                Button b = findViewById(R.id.buttonnam3);


                String txt = "Ati ales evenimentul ";
                String txt1 = ". Urmariti culorile traseelor pentru o ruta curata si eficienta.";
                Toast toast = Toast.makeText(getBaseContext(), txt + b.getText() + txt1, duration);
                toast.show();
                //sub = new ArrayList<Integer>();
                sub = new ArrayList<Integer>();
                sub = new ArrayList<Integer>();

                i = 0;
                k = -1;
                int ok = 1;
                b3.setVisibility(View.INVISIBLE);
                loc[1] = new LatLng(Double.parseDouble(p.get(7).toString()), Double.parseDouble(p.get(8).toString()));
                loc[0] = new LatLng(44.4377757, 26.0496109);

                String url = getDirectionsUrl(loc[0], loc[1]);
                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(mps);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sub = new ArrayList<Integer>();
                int duration = Toast.LENGTH_LONG;

                Button b = findViewById(R.id.buttonname1);

                String txt = "Ati ales evenimentul ";
                String txt1 = ". Urmariti culorile traseelor pentru o ruta curata si eficienta.";
                Toast toast = Toast.makeText(getBaseContext(), txt + b.getText() + txt1, duration);
                toast.show();
                sub = new ArrayList<Integer>();

                i = 0;
                k = -1;
                int ok = 1;
                b1.setVisibility(View.INVISIBLE);
                loc[1] = new LatLng(Double.parseDouble(p.get(1).toString()), Double.parseDouble(p.get(2).toString()));
                loc[0] = new LatLng(44.4377757, 26.0496109);

                String url = getDirectionsUrl(loc[0], loc[1]);
                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(mps);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                sub = new ArrayList<Integer>();

                int duration = Toast.LENGTH_LONG;

                Button b = findViewById(R.id.buttonname);

                Toast toast = Toast.makeText(getBaseContext(), b.getText() + "ing", duration);
                toast.show();

                i = 0;
                k = -1;
                int ok = 1;
                if (t1 != null && t2 != null) {

                    EditText t3 = (EditText) findViewById(R.id.editText1);
                    EditText t4 = (EditText) findViewById(R.id.editText2);
                    //EditText t3 = new EditText(findViewById(R.id.editText1));
                    //EditText t4 = (EditText) findViewById(R.id.editText2);
                    if (t1.getText().equals(t3.getText()) && t2.getText().equals(t4.getText())) {
                        ok = 0;
                    }
                }
                if(ok == 1) {
                    t1 = (EditText) findViewById(R.id.editText1);
                    t2 = (EditText) findViewById(R.id.editText2);
                    n[0] = new String(t1.getText().toString().replace(' ', '+'));
                    n[1] = new String(t2.getText().toString().replace(' ', '+'));
                    loc[0] = null;
                    loc[1] = null;


                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(mps);
                }
                button.setEnabled(true);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.clear();

        new GetCoordinates().execute(n[0]);
        boolean ok2 = true;
        while(ok2)
            if( loc[0]!= null ){
                ok2 = false;
                i = 1;
                new GetCoordinates().execute(n[1]);

                boolean ok = true;
                while (ok)
                    if (loc[1] != null) {
                        ok = false;
                        mMap.addMarker(new MarkerOptions().position(loc[0]).title("Start"));
                        mMap.addMarker(new MarkerOptions().position(loc[1]).title("Stop"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc[0], 12));

                        //LatLng loc1 = new LatLng(Double.parseDouble(t1.getText().toString()), Double.parseDouble(t2.getText().toString()));
                        //LatLng loc2 = new LatLng(Double.parseDouble(t3.getText().toString()), Double.parseDouble(t4.getText().toString()));

                        String url = getDirectionsUrl(loc[0], loc[1]);
                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
            }
    }

    private class DownloadTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object url[]) {

            String data = "";

            try {
                data = downloadUrl((String) url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return (String) data;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute((String) result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            Log.i("TAG", "result.size() = " + result.size());
            for (int i = result.size() - 1; i >= 0; i--) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat").toString());
                    double lng = Double.parseDouble(point.get("lng").toString());
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }
                sub.add(0);
                int dim;
                if(path.size()/10==0) {
                    dim=1 ;
                }
                else
                    dim=path.size()/10 ;
                for (int j = 0; j < path.size(); j+=dim ) {
                    HashMap point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat").toString());
                    double lng = Double.parseDouble(point.get("lng").toString());
                    LatLng position = new LatLng(lat, lng);

                    ////////////////////////////////////////////////////
                   try {
                       if( i == result.size() - 1 )
                            run1(position);
                       else if( i == result.size() - 2 )
                           run2(position);
                       else if( i == result.size() - 3 )
                           run3(position);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
            ////////////////////////////////////////////////////////////////////
                }

                int maxim = Collections.max(sub);
                int min = Collections.min(sub);



                lineOptions.addAll(points);
                if (i == result.size() - 1 - sub.indexOf(maxim)) {
                    lineOptions.width(10);
                    lineOptions.color(Color.RED);
                } else {
                    if (i == result.size() - 1 - sub.indexOf(min)) {
                        lineOptions.width(12);
                        lineOptions.color(Color.GREEN);
                    }
                    else
                    {
                        lineOptions.width(10);
                        lineOptions.color(Color.rgb(255, 165, 0));
                    }
                }
                lineOptions.geodesic(true);
                mMap.addPolyline(lineOptions);
            }

// Drawing polyline in the Google Map for the i-th route

        }
    }

    private void run1(LatLng position) throws IOException {
        String url = "https://api.breezometer.com/baqi/?lat=" + position.latitude +"&lon="
                + position.longitude + "&key=15f774a3ab514f2cae044998fe5a7bcd&fields=breezometer_aqi";
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String myResponse = response.body().string();

                    MapsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //txtString.setText(myResponse);
                            try {

                                JSONObject json = new JSONObject(myResponse);
                                //txtString.setText(json.getString("datetime"));
                                sub.set(0, sub.get(0) +  Integer.parseInt(json.getString("breezometer_aqi")));
                                Log.i("TAGGG", "" + 0);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });

        }

    private void run2(LatLng position) throws IOException {
        String url = "https://api.breezometer.com/baqi/?lat=" + position.latitude +"&lon="
                + position.longitude + "&key=15f774a3ab514f2cae044998fe5a7bcd&fields=breezometer_aqi";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                MapsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //txtString.setText(myResponse);
                        try {

                            JSONObject json = new JSONObject(myResponse);
                            //txtString.setText(json.getString("datetime"));
                            sub.set(1, sub.get(1) +  Integer.parseInt(json.getString("breezometer_aqi")));
                            Log.i("TAGGG", "" + 1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }
    private void run3(LatLng position) throws IOException {
        String url = "https://api.breezometer.com/baqi/?lat=" + position.latitude +"&lon="
                + position.longitude + "&key=15f774a3ab514f2cae044998fe5a7bcd&fields=breezometer_aqi";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                MapsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //txtString.setText(myResponse);
                        try {

                            JSONObject json = new JSONObject(myResponse);
                            //txtString.setText(json.getString("datetime"));
                            sub.set(2, sub.get(2) +  Integer.parseInt(json.getString("breezometer_aqi")));
                            Log.i("TAGGG", "" + 2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mod = new String("driving");
        String mode = "mode=" + mod;
        String extra = new String("alternatives=true");
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + '&' + extra;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    ///////////////////////////////////////////////////////////////

    private class GetCoordinates extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=AIzaSyD5QW7a9eaVXWcLuAaiibKHAgDgCJKakc4",address);
                response = http.getHTTPData(url);
                onPostExecute(response);
                return response;
            }
            catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);

                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();

                loc[i] = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

            } catch (JSONException e) {

                //startActivity(new Intent(String.valueOf(MapsActivity.class)));
                finish();
                e.printStackTrace();
            }
        }
    }
}