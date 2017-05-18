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

public class AdapterImam extends RecyclerView.Adapter<AdapterImam.Holder>{


    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> hari=new ArrayList<>();
    ArrayList<String> id_ustadz=new ArrayList<>();
    ArrayList<String> imam=new ArrayList<>();
    Context ct;

    public AdapterImam(ArrayList<Integer> id, ArrayList<String> hari,ArrayList<String> id_ustadz,ArrayList<String> imam, ImamActivity ct){
        this.id=id;
        this.hari=hari;
        this.id_ustadz=id_ustadz;
        this.imam=imam;
        this.ct=ct;
    }


    public class Holder extends RecyclerView.ViewHolder{
        public TextView hari;
        public TextView imam;
        public ImageView imgdetail;
        public Holder(View itemView) {
            super(itemView);
            hari=(TextView) itemView.findViewById(R.id.hari);
            imam=(TextView) itemView.findViewById(R.id.imam);
            imgdetail=(ImageView) itemView.findViewById(R.id.detailimam);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_imam,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.hari.setText(hari.get(position));
        holder.imam.setText(imam.get(position));
        holder.imgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ct.getApplicationContext(),UstadzActivity.class);
                i.putExtra("id",id_ustadz.get(position));
                ct.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }






}
