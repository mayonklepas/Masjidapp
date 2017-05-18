package com.alazharbsd.masjid.masjidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Minami on 11/05/2017.
 */

public class Adapterkegiatan extends RecyclerView.Adapter<Adapterkegiatan.Holder>{


    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> header=new ArrayList<>();
    Context ct;

    public Adapterkegiatan(ArrayList<Integer> id,ArrayList<String> header,KegiatanActivity ct){
        this.id=id;
        this.header=header;
        this.ct=ct;
    }


    public class Holder extends RecyclerView.ViewHolder{
        public TextView header;
        public ImageView imgdetail;
        public Holder(View itemView) {
            super(itemView);
            header=(TextView) itemView.findViewById(R.id.header);
            imgdetail=(ImageView) itemView.findViewById(R.id.imgdetail);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_kegiatan,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.header.setText(header.get(position));
        holder.imgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),KegiatanDetailActivity.class);
                i.putExtra("kategori",header.get(position));
                ct.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }






}
