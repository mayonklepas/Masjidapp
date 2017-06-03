package com.alazharbsd.masjid.masjidapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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

import static android.R.anim.slide_in_left;
import static android.R.anim.slide_out_right;

/**
 * Created by Minami on 11/05/2017.
 */

public class MainFragment extends Fragment {

    TextView hikmah,hadist,ayat,artikel,detailartikel,detailhikmah,detailhadist,detailayat;
    ImageView imgsyar,imgartikel,
            imgscjadwalkegiatan,imgscjadwalkhutbah,imgscjadwalmasjid,imgscjadwalramadhan,imgscartikel,imgscvideo;
    ViewFlipper slider;
    int idartikel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,container,false);
        hikmah=(TextView) v.findViewById(R.id.hikmah);
        hadist=(TextView) v.findViewById(R.id.hadist);
        ayat=(TextView) v.findViewById(R.id.ayat);
        artikel=(TextView) v.findViewById(R.id.artikel);
        detailartikel=(TextView) v.findViewById(R.id.detailartikel);
        detailhikmah=(TextView) v.findViewById(R.id.detailhikmah);
        detailhadist=(TextView) v.findViewById(R.id.detailhadist);
        detailayat=(TextView) v.findViewById(R.id.detailayat);
        imgsyar=(ImageView) v.findViewById(R.id.imgsyar);
        imgartikel=(ImageView) v.findViewById(R.id.imgartikel);
        imgscjadwalkegiatan=(ImageView) v.findViewById(R.id.imgscjadwalkegiatan);
        imgscjadwalkhutbah=(ImageView) v.findViewById(R.id.imgscjadwalkhutbah);
        imgscjadwalmasjid=(ImageView) v.findViewById(R.id.imgscjadwalmasjid);
        imgscjadwalramadhan=(ImageView) v.findViewById(R.id.imgscjadwalramadhan);
        imgscartikel=(ImageView) v.findViewById(R.id.imgscartikel);
        imgscvideo=(ImageView) v.findViewById(R.id.imgscvideo);
        slider=(ViewFlipper) v.findViewById(R.id.slider);
        slider.setAutoStart(true);
        slider.setFlipInterval(5000);
        slider.setInAnimation(AnimationUtils.loadAnimation(getActivity(), slide_in_left));
        slider.startFlipping();
        slider.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                slider.showNext();
            }
        });
        loadata();
        loadartikel();
        loadinfo();
        loadsyar();
        shortcut();
        return v;
    }

    public void shortcut(){

        detailhikmah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),KataDetailActivity.class);
                i.putExtra("header","Hikmah Hari Ini");
                i.putExtra("detail",hikmah.getText());
                startActivity(i);
            }
        });

        detailhadist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),KataDetailActivity.class);
                i.putExtra("header","Hadist Hari Ini");
                i.putExtra("detail",hadist.getText());
                startActivity(i);
            }
        });

        detailayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),KataDetailActivity.class);
                i.putExtra("header","Ayat Hari Ini");
                i.putExtra("detail",ayat.getText());
                startActivity(i);
            }
        });

        detailartikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),ArtikelDetailActivity.class);
                i.putExtra("id",idartikel);
                startActivity(i);
            }
        });





        imgscjadwalkegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),KegiatanActivity.class);
                startActivity(i);
            }
        });

        imgscjadwalkhutbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),KhutbahActivity.class);
                startActivity(i);
            }
        });

        imgscjadwalmasjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),ImamActivity.class);
                startActivity(i);
            }
        });

        imgscjadwalramadhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),RamadhanActivity.class);
                startActivity(i);
            }
        });

        imgscartikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),ArtikelkategoriActivity.class);
                startActivity(i);
            }
        });


        imgscvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),VideoActivity.class);
                startActivity(i);
            }
        });


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

    public void loadartikel(){
        RequestQueue rq= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/home-artikel.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                String textartikel=jo.getString("konten");
                                String judul=jo.getString("judul");
                                idartikel=jo.getInt("id");
                                if(textartikel.length() < 200){
                                    artikel.setText(judul+"\n\n"+textartikel);
                                }else{
                                    artikel.setText(judul+"\n\n"+textartikel.substring(0,200)+"...");
                                }

                                Glide.with(getActivity()).
                                        load(Config.url+"/masjidapp/src/gambar/"+jo.getString("gambar")).
                                        centerCrop().crossFade().
                                        diskCacheStrategy(DiskCacheStrategy.NONE).
                                        into(imgartikel);
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

    public void loadsyar(){
        RequestQueue rq= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/home-syar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                Glide.with(getActivity()).
                                        load(Config.url+"/masjidapp/src/gambar/"+jo.getString("gambar")).
                                        centerCrop().crossFade().
                                        diskCacheStrategy(DiskCacheStrategy.NONE).
                                        into(imgsyar);
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
                                "Email   : "+jo.getString("email")+"\n"+
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
