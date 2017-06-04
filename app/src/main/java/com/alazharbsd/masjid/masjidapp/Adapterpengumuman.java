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

public class Adapterpengumuman extends RecyclerView.Adapter<Adapterpengumuman.Holder>{


    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> url=new ArrayList<>();
    ArrayList<String> header=new ArrayList<>();
    ArrayList<String> detail=new ArrayList<>();
    Context ct;

    public Adapterpengumuman(ArrayList<Integer> id, ArrayList<String> url, ArrayList<String> header,
                             ArrayList<String> detail, PengumumanFragment ct){
        this.id=id;
        this.url=url;
        this.header=header;
        this.detail=detail;
        this.ct=ct.getActivity().getApplicationContext();
    }


    public class Holder extends RecyclerView.ViewHolder{
        public TextView header;
        public TextView detail;
        public ImageView imgartikel;
        public ImageView imgdetail;
        public Holder(View itemView) {
            super(itemView);
            header=(TextView) itemView.findViewById(R.id.header);
            detail=(TextView) itemView.findViewById(R.id.detail);
            imgartikel=(ImageView) itemView.findViewById(R.id.imgartikel);
            imgdetail=(ImageView) itemView.findViewById(R.id.imgdetail);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_pengumuman,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.header.setText(header.get(position));
        String detailkata="";
        if(detail.get(position).length() < 100){
            detailkata=detail.get(position);
        }else{
            detailkata=detail.get(position).substring(0,100)+"...";
        }
        holder.detail.setText(detailkata);
        Glide.with(ct).
                load(url.get(position)).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(holder.imgartikel);
        holder.imgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ct.getApplicationContext(),PengumumanDetailActivity.class);
                i.putExtra("id",id.get(position));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ct.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }






}
