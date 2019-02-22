package com.example.rgukt.gruppe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ImageView dogImageView,crossImage,checkImage;
    private ProgressBar progressBar;
    private Button showLikedImages;
    private String text="";
    private static final String URL_data = "https://dog.ceo/api/breeds/image/random";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection();

        dogImageView = (ImageView)findViewById(R.id.image_dog);
        crossImage = (ImageView)findViewById(R.id.crossImage);
        checkImage = (ImageView)findViewById(R.id.checkImage);
        showLikedImages = (Button)findViewById(R.id.showLikedDogs);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        LoadImages();



        crossImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadImages();
            }
        });

        checkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateData(text);

                LoadImages();
            }
        });

        showLikedImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowLikedImages.class);
                startActivity(intent);
            }
        });
    }

    public void LoadImages() {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                                JSONObject jsonObject = new JSONObject(response);
                                text = jsonObject.getString("message");
                                Picasso.with(getApplicationContext()).load(text).into(dogImageView,
                                        new com.squareup.picasso.Callback(){

                                            @Override
                                            public void onSuccess() {
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }

                                            @Override
                                            public void onError() {
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }
                                        });
                            } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(dogImageView.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void CreateData(String text){

        Date objDate = new Date();
        String format = "E MMM d y hh:mm:ss";
        SimpleDateFormat objSDF = new SimpleDateFormat(format);
        String timeStamp = objSDF.format(objDate);
        final DatabaseHelper mydb = new DatabaseHelper(getApplicationContext());
        mydb.insertData(text,timeStamp);
        mydb.close();
    }

    public void connection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        //For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();


        if (!is3g && !isWifi) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("No Internet Access\nTurn on the Mobile Data or Connect to a wifi");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return;
    }

}

