package com.alazharbsd.masjid.masjidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RamadhanDetailActivity extends AppCompatActivity {

    TextView judul,imam,bacaan,penceramah,imamqiyamullail;
    ImageView detailimam,detailpenceramah,detailimamqiyamullail;
    String id,id_ustadz1,id_ustadz2,id_ustadz3;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramadhan_detail);
        judul=(TextView) findViewById(R.id.judul);
        imam=(TextView) findViewById(R.id.imam);
        bacaan=(TextView) findViewById(R.id.bacaan);
        penceramah=(TextView) findViewById(R.id.penceramah);
        imamqiyamullail=(TextView) findViewById(R.id.imamqiyamullail);
        detailimam=(ImageView) findViewById(R.id.detailimam);
        detailpenceramah=(ImageView) findViewById(R.id.detailpenceramah);
        detailimamqiyamullail=(ImageView) findViewById(R.id.detailqiyamul);
        Bundle ex=getIntent().getExtras();
        id=String.valueOf(ex.getInt("id"));
        loadata();
        detailimam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RamadhanDetailActivity.this,UstadzActivity.class);
                i.putExtra("id",id_ustadz1);
                startActivity(i);
            }
        });

        detailpenceramah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RamadhanDetailActivity.this,UstadzActivity.class);
                i.putExtra("id",id_ustadz2);
                startActivity(i);
            }
        });

        detailimamqiyamullail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RamadhanDetailActivity.this,UstadzActivity.class);
                i.putExtra("id",id_ustadz3);
                startActivity(i);
            }
        });
        imgback=(ImageView) findViewById(R.id.imgback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void loadata(){
        RequestQueue rq= Volley.newRequestQueue(RamadhanDetailActivity.this);
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/jadwal-ramadhan.php?id="+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                Date date=new Date();
                                String hari="";
                                try {
                                    date=new SimpleDateFormat("yyyy/MM/dd").parse(jo.getString("waktu").replace("-","/"));
                                    hari=new SimpleDateFormat("EEEE, d MMM yyyy").format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(hari.split(",")[0].equals("Minggu")){
                                    hari="Ahad, "+hari.split(",")[1];
                                }
                                judul.setText("Info Petugas, "+hari);
                                imam.setText(jo.getString("imam"));
                                bacaan.setText(jo.getString("bacaan"));
                                penceramah.setText(jo.getString("penceramah"));
                                imamqiyamullail.setText(jo.getString("imam_qiyamul_lail"));
                                id_ustadz1=jo.getString("id_ustadz1");
                                id_ustadz2=jo.getString("id_ustadz2");
                                id_ustadz3=jo.getString("id_ustadz3");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RamadhanDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }


}
