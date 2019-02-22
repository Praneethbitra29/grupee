package com.example.rgukt.gruppe;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowLikedImages extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ArrayList<Image> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_liked_images);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Liked Profiles");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageList = new ArrayList<>();

        final DatabaseHelper mydb = new DatabaseHelper(this);
        Cursor result = mydb.getAllData();
        if(result.getCount() != 0) {
            while (result.moveToNext()) {
                String timestamp,url;
                timestamp = result.getString(1);
                url = result.getString(0);
                Image image = new Image(timestamp,"Liked",url);
                imageList.add(image);
            }
        }else{

        }



        adapter = new ImageAdapter(imageList,this);
        recyclerView.setAdapter(adapter);
    }
}
