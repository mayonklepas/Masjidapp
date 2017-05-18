package com.alazharbsd.masjid.masjidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Minami on 11/05/2017.
 */

public class Adapterkegiatandetail extends RecyclerView.Adapter<Adapterkegiatandetail.Holder>{


    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> tanggal=new ArrayList<>();
    Context ct;

    public Adapterkegiatandetail(ArrayList<Integer> id, ArrayList<String> tanggal, KegiatanDetailActivity ct){
        this.id=id;
        this.tanggal=tanggal;
        this.ct=ct;
    }


    public class Holder extends RecyclerView.ViewHolder{
        public TextView tanggal;
        public ImageView imgdetail;
        public Holder(View itemView) {
            super(itemView);
            tanggal=(TextView) itemView.findViewById(R.id.tanggal);
            imgdetail=(ImageView) itemView.findViewById(R.id.imgdetail);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_kegiatan_detail,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Date date=new Date();
        String hari="";
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
        holder.imgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),KegiatanDetailDetailActivity.class);
                i.putExtra("id",id.get(position));
                ct.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }






}
