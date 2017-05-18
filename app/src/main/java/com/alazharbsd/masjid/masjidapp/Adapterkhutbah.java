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

public class Adapterkhutbah extends RecyclerView.Adapter<Adapterkhutbah.Holder>{


    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> header=new ArrayList<>();
    Context ct;

    public Adapterkhutbah(ArrayList<Integer> id, ArrayList<String> header,KhutbahActivity ct){
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
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_khutbah,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Date date=new Date();
        String hari="";
        try {
            date=new SimpleDateFormat("yyyy/MM/dd").parse(header.get(position).replace("-","/"));
            hari=new SimpleDateFormat("EEEE, d MMM yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(hari.split(",")[0].equals("Minggu")){
            hari="Ahad, "+hari.split(",")[1];
        }
        holder.header.setText(hari);
        holder.imgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),KhutbahdetailActivity.class);
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
