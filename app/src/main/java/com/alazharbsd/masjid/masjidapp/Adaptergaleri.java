package com.alazharbsd.masjid.masjidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Minami on 11/05/2017.
 */

public class Adaptergaleri extends RecyclerView.Adapter<Adaptergaleri.Holder>{


    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> url=new ArrayList<>();
    ArrayList<String> header=new ArrayList<>();
    Context ct;

    public Adaptergaleri(ArrayList<Integer> id, ArrayList<String> url, ArrayList<String> header,GaleriActivity ct){
        this.id=id;
        this.url=url;
        this.header=header;
        this.ct=ct;
    }


    public class Holder extends RecyclerView.ViewHolder{
        public TextView header;
        public ImageView imggaleri;
        public Holder(View itemView) {
            super(itemView);
            header=(TextView) itemView.findViewById(R.id.header);
            imggaleri=(ImageView) itemView.findViewById(R.id.imggaleri);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_galeri,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.header.setText(header.get(position));
        Glide.with(ct).
                load(url.get(position)).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(holder.imggaleri);
        /*holder.imgartikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ct.getApplicationContext(),ArtikelDetailActivity.class);
                i.putExtra("id",id.get(position));
                ct.startActivity(i);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return id.size();
    }






}
