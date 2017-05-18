package com.alazharbsd.masjid.masjidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;

public class ArtikelActivity extends AppCompatActivity {

    RecyclerView recdata;
    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> url=new ArrayList<>();
    ArrayList<String> header=new ArrayList<>();
    ArrayList<String> detail=new ArrayList<>();
    RecyclerView.LayoutManager layman;
    RecyclerView.Adapter adapter;
    ProgressBar pb;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);
        pb=(ProgressBar) findViewById(R.id.pb);
        recdata=(RecyclerView) findViewById(R.id.recdata);
        layman=new LinearLayoutManager(this);
        recdata.setLayoutManager(layman);
        recdata.setHasFixedSize(true);
        recdata.setItemAnimator(new DefaultItemAnimator());
        adapter=new Adapterartikel(id,url,header,detail,this);
        loadata();
        imgback=(ImageView) findViewById(R.id.imgback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void loadata(){
        pb.setVisibility(View.VISIBLE);
        RequestQueue rq= Volley.newRequestQueue(ArtikelActivity.this);
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/artikel.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                id.add(jo.getInt("id"));
                                url.add(Config.url+"/masjidapp/src/gambar/"+jo.getString("gambar"));
                                header.add(jo.getString("judul"));
                                detail.add(jo.getString("konten"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recdata.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ArtikelActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                pb.setVisibility(View.GONE);
            }
        });
    }
}
