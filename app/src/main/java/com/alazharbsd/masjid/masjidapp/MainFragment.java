package com.alazharbsd.masjid.masjidapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Minami on 11/05/2017.
 */

public class MainFragment extends Fragment {

    TextView hikmah,hadist,ayat;
    ImageView imgsharehikmah,imgsharehadist,imgshareayat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,container,false);
        hikmah=(TextView) v.findViewById(R.id.hikmah);
        hadist=(TextView) v.findViewById(R.id.hadist);
        ayat=(TextView) v.findViewById(R.id.ayat);
        imgsharehikmah=(ImageView) v.findViewById(R.id.imgsharehikmah);
        imgsharehadist=(ImageView) v.findViewById(R.id.imgsharehadist);
        imgshareayat=(ImageView) v.findViewById(R.id.imgshareayat);
        loadata();
        loadinfo();
         imgsharehikmah.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                 sharingIntent.setType("text/plain");
                 sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, hikmah.getText());
                 startActivity(Intent.createChooser(sharingIntent,"Share Ke"));
             }
         });

        imgsharehadist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, hadist.getText());
                startActivity(Intent.createChooser(sharingIntent,"Share Ke"));
            }
        });

        imgshareayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, ayat.getText());
                startActivity(Intent.createChooser(sharingIntent,"Share Ke"));
            }
        });


        return v;
    }


    public void loadata(){
        RequestQueue rq= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/kata-harian.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                hikmah.setText(jo.getString("hikmah_hari_ini"));
                                hadist.setText(jo.getString("hadist_hari_ini"));
                                ayat.setText(jo.getString("ayat_hari_ini"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }

    public void loadinfo(){
        RequestQueue rq= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/infomasjid.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                Config.infomasjid=jo.getString("nama")+"\n\n"+
                                "Email  : "+jo.getString("email")+"\n"+
                                "Kontak : "+jo.getString("nohp")+"\n"+
                                "Alamat : "+jo.getString("alamat")+"\n\n"+
                                jo.getString("keterangan");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }
}
