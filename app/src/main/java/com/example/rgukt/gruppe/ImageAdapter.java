package com.example.rgukt.gruppe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Image> imageList;
    private Context context;

    public ImageAdapter(List<Image> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_liked_images,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = imageList.get(position);

        holder.liked.setText(image.getMliked());
        holder.timestamp.setText(image.getMtimestamp());


        Picasso.with(context).load(image.getMurl()).into(holder.imageView,
                new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView liked;
        public TextView timestamp;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            liked = (TextView)itemView.findViewById(R.id.likedtext);
            timestamp = (TextView)itemView.findViewById(R.id.timeStamp);

            imageView = (ImageView)itemView.findViewById(R.id.imageView);

        }
    }

}
