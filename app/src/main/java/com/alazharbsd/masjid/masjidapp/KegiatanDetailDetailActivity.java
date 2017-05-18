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

public class KegiatanDetailDetailActivity extends AppCompatActivity {
    TextView judul,penceramah,tema;
    ImageView detailpenceramah;
    String id,idustadz;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kegiatan_detail_detail);
        judul=(TextView) findViewById(R.id.judul);
        penceramah=(TextView) findViewById(R.id.penceramah);
        detailpenceramah=(ImageView) findViewById(R.id.detailpenceramah);
        tema=(TextView) findViewById(R.id.tema);
        Bundle ex=getIntent().getExtras();
        id=String.valueOf(ex.getInt("id"));
        loadata();
        detailpenceramah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(KegiatanDetailDetailActivity.this,UstadzActivity.class);
                i.putExtra("id",idustadz);
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
        RequestQueue rq= Volley.newRequestQueue(KegiatanDetailDetailActivity.this);
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/jadwal-kegiatan.php?id="+id,
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
                                idustadz=String.valueOf(jo.getInt("id_ustadz"));
                                judul.setText(jo.getString("kategori")+", "+hari);
                                penceramah.setText(jo.getString("penceramah"));
                                tema.setText(jo.getString("tema"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KegiatanDetailDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }
}
