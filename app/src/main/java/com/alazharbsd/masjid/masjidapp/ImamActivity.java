
package com.alazharbsd.masjid.masjidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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

public class ImamActivity extends AppCompatActivity {

    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> hari=new ArrayList<>();
    ArrayList<String> id_ustadz=new ArrayList<>();
    ArrayList<String> imam=new ArrayList<>();
    RecyclerView recdata;
    RecyclerView.LayoutManager layman;
    RecyclerView.Adapter adapter;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imam);
        recdata=(RecyclerView) findViewById(R.id.recdata);
        layman=new LinearLayoutManager(this);
        recdata.setLayoutManager(layman);
        recdata.setHasFixedSize(true);
        recdata.setItemAnimator(new DefaultItemAnimator());
        adapter=new AdapterImam(id,hari,id_ustadz,imam,this);
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
        RequestQueue rq= Volley.newRequestQueue(ImamActivity.this);
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/jadwal-imam.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                id.add(jo.getInt("id"));
                                hari.add(jo.getString("kategori"));
                                id_ustadz.add(jo.getString("id_ustadz"));
                                imam.add(jo.getString("imam"));
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
                        Toast.makeText(ImamActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }

}


