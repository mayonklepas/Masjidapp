package com.alazharbsd.masjid.masjidapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Minami on 11/05/2017.
 */

public class Adaptervideo extends RecyclerView.Adapter<Adaptervideo.Holder>{


    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> url=new ArrayList<>();
    ArrayList<String> header=new ArrayList<>();
    ArrayList<String> tanggal=new ArrayList<>();
    ArrayList<String> sinopsis=new ArrayList<>();
    Context ct;
    String hari;

    public Adaptervideo(ArrayList<Integer> id, ArrayList<String> url, ArrayList<String> header, ArrayList<String> tanggal,
                        ArrayList<String> sinopsis,VideoActivity ct){
        this.id=id;
        this.url=url;
        this.header=header;
        this.tanggal=tanggal;
        this.sinopsis=sinopsis;
        this.ct=ct;
    }


    public class Holder extends RecyclerView.ViewHolder{
        public TextView header;
        public TextView tanggal;
        public YouTubeThumbnailView videothumb;
        //public YouTubePlayerView videos;
        public Holder(View itemView) {
            super(itemView);
            header=(TextView) itemView.findViewById(R.id.header);
            tanggal=(TextView) itemView.findViewById(R.id.tanggal);
            videothumb=(YouTubeThumbnailView) itemView.findViewById(R.id.videothumb);
            //videos=(YouTubePlayerView) itemView.findViewById(R.id.videos);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_video,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.header.setText(header.get(position));
        Date date=new Date();
        try {
            date=new SimpleDateFormat("yyyy/MM/dd").parse(tanggal.get(position).replace("-","/"));
            hari=new SimpleDateFormat("EEEE, d MMM yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(hari.split(",")[0].equals("Minggu")){
            hari="Ahad, "+hari.split(",")[1];
        }
        holder.tanggal.setText(hari);
        System.out.println(url.get(position).split("/")[4]);
        holder.videothumb.initialize("AIzaSyBK-6flB7-yTDUpVOSmJ0AFdNLm8nyfMLQ", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(url.get(position).split("/")[4]);

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        Toast.makeText(ct, errorReason.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        holder.videothumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ct.getApplicationContext(),VideoDetailActivity.class);
                i.putExtra("videoid",url.get(position).split("/")[4]);
                i.putExtra("tanggal",hari);
                i.putExtra("judul",header.get(position));
                i.putExtra("sinopsis",sinopsis.get(position));
                ct.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }






}
