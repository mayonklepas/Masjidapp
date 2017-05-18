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

public class KhutbahdetailActivity extends AppCompatActivity {

    TextView judul,khotib,imam,muadzin,mc,tema;
    ImageView detailkhotib,detailimam,detailmuadzin,detailmc;
    String id,id_ustadz1,id_ustadz2,id_ustadz3,id_ustadz4;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khutbahdetail);
        judul=(TextView) findViewById(R.id.judul);
        khotib=(TextView) findViewById(R.id.khotib);
        imam=(TextView) findViewById(R.id.imam);
        muadzin=(TextView) findViewById(R.id.muadzin);
        mc=(TextView) findViewById(R.id.mc);
        tema=(TextView) findViewById(R.id.tema);
        detailkhotib=(ImageView) findViewById(R.id.detailkhotib);
        detailimam=(ImageView) findViewById(R.id.detailimam);
        detailmuadzin=(ImageView) findViewById(R.id.detailmuadzin);
        detailmc=(ImageView) findViewById(R.id.detailmc);
        Bundle ex=getIntent().getExtras();
        id=String.valueOf(ex.getInt("id"));
        loadata();
        detailkhotib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(KhutbahdetailActivity.this,UstadzActivity.class);
                i.putExtra("id",id_ustadz1);
                startActivity(i);
            }
        });

        detailimam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(KhutbahdetailActivity.this,UstadzActivity.class);
                i.putExtra("id",id_ustadz2);
                startActivity(i);
            }
        });

        detailmuadzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(KhutbahdetailActivity.this,UstadzActivity.class);
                i.putExtra("id",id_ustadz3);
                startActivity(i);
            }
        });

        detailmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(KhutbahdetailActivity.this,UstadzActivity.class);
                i.putExtra("id",id_ustadz4);
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
        RequestQueue rq= Volley.newRequestQueue(KhutbahdetailActivity.this);
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/jadwal-khutbah.php?id="+id,
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
                                khotib.setText(jo.getString("khotib"));
                                imam.setText(jo.getString("imam"));
                                muadzin.setText(jo.getString("muadzin"));
                                mc.setText(jo.getString("mc"));
                                tema.setText(jo.getString("tema"));
                                id_ustadz1=jo.getString("id_ustadz1");
                                id_ustadz2=jo.getString("id_ustadz2");
                                id_ustadz3=jo.getString("id_ustadz3");
                                id_ustadz4=jo.getString("id_ustadz4");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KhutbahdetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }
}
