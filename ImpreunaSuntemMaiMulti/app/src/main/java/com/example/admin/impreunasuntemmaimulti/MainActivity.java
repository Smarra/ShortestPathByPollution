package com.example.admin.impreunasuntemmaimulti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    //TextView txtStatus;
    LoginButton loginButton;
    CallbackManager callbackManager;
    String id_user;
    String aux = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        initializeControls();
        loginWithFB();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //to do
    }

    private void initializeControls() {
        callbackManager = CallbackManager.Factory.create();
        //txtStatus = (TextView) findViewById(R.id.txtStatus);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile", "user_friends", "user_events");
    }

    private void loginWithFB() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               // txtStatus.setText("Login Success\n");

                //TextView idUser = (TextView) findViewById(R.id.idUser);
                id_user = loginResult.getAccessToken().getUserId();
                //idUser.setText("ID User:" + id_user);
                //Log.i("Tag", loginResult.getClass().getName();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayUserInfo(object);
                    }
                });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "name, email, events");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();


                //finish();

            }

            @Override
            public void onCancel() {
                //txtStatus.setText("Login Failed\n");
            }

            @Override
            public void onError(FacebookException error) {
                //txtStatus.setText("Login Error:" + error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void displayUserInfo(JSONObject object) {
        String name_user = "EMPTY";
        String email = "EMPTY";
        String user_events = "EMPTY";

        Date date=new Date();
        String time = new SimpleDateFormat("yyyy-MM-dd").format(date);

        try {
            name_user = object.getString("name");
            email = object.getString("email");
            user_events = object.getString("events");


            JSONObject json_object = new JSONObject(user_events);
            JSONArray evenimente = json_object.getJSONArray("data");


            ArrayList arr = new ArrayList();
            for(int i = 0; i < evenimente.length(); i++) {


                JSONObject eveniment = evenimente.getJSONObject(i);
                String name = eveniment.getString("name");
                String start_time = eveniment.getString("start_time");
                start_time = start_time.substring(0,10);
                //start_time = start_time.toLowerCase().replace("-", ".");

                if (time.compareTo(start_time) > 0)
                    continue;

                String latitude = "";
                String longitude = "";


                /////////
                try {
                    String place = eveniment.getString("place");
                    JSONObject place_obj = new JSONObject(place);
                    String location = place_obj.getString("location");

                    JSONObject location_obj = new JSONObject(location);
                    latitude = location_obj.getString("latitude");
                    longitude = location_obj.getString("longitude");

                    Log.i("latitude", latitude);
                } catch (JSONException e) {
                    continue;
                    //Log.i("NU_MERGE", "nu");
                }


                name = name.replace(" ", "-");
                aux = aux + name + " " + latitude + " " + longitude +" ";

                //aux = "pere";

                /////////
                //String s = new String(name + " " + start_time + " " + latitude + " " + longitude);
                //b.add(s);
            }



            //txtStatus = (TextView) findViewById(R.id.txtStatus);

            //String names = "";

            //String name_user, email + arr array de array
            /*Iterator it = arr.iterator();
            while (it.hasNext()){
                ArrayList a = (ArrayList) it.next();
                //names = names + a.get(0) + " ";
                Log.i("primul", (String) a.get(0) + "  " + a.get(2) + "   " + a.get(3));

            }*/




           // txtStatus.setText(time);
            //txtStatus.setVisibility(1);

            //JSONArray array = (JSONArray) user_events;

            //Log.i("nunu", (String) array.get(1));

        } catch (JSONException e) {
            Log.i("CATCH JSON", "nasol");
            e.printStackTrace();
        }

        //String facebookImage = "https://graph.facebook.com/"+id_user+"/picture?type=large";
        //ImageView image = (ImageView)findViewById(R.id.imageView);
        //Glide.with(getApplicationContext()).load(facebookImage).into(image);

        //Log.i("mytableee", email);

        //TextView infoUser = (TextView) findViewById(R.id.infoUser);
        //infoUser.setText(email);
        //infoUser.setVisibility(1);

        Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);

        Log.i("PULAMEA", aux);
        myIntent.putExtra("key", aux); //Optional parameters
        //myIntent.putExtra("image", facebookImage);
        MainActivity.this.startActivity(myIntent);
        //
    }
}

