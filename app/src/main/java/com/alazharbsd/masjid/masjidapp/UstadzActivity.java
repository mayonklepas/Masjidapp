package com.alazharbsd.masjid.masjidapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class UstadzActivity extends AppCompatActivity {
    TextView info, nohp;
    CircleImageView imgperson;
    Toolbar cta;
    String id;
    FloatingActionButton btncall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustadz);
        info = (TextView) findViewById(R.id.info);
        nohp = (TextView) findViewById(R.id.nohp);
        imgperson = (CircleImageView) findViewById(R.id.imgperson);
        btncall = (FloatingActionButton) findViewById(R.id.btncall);
        cta = (Toolbar) findViewById(R.id.toolbar);
        Bundle ex = getIntent().getExtras();
        id = ex.getString("id");
        loadata();
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+nohp.getText()));
                startActivity(callIntent);
            }
        });
    }


    public void loadata(){
        RequestQueue rq= Volley.newRequestQueue(UstadzActivity.this);
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/ustadz.php?id="+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                info.setText(jo.getString("info"));
                                nohp.setText(jo.getString("nohp"));
                                cta.setTitle(jo.getString("nama"));
                                Glide.with(UstadzActivity.this).
                                        load(Config.url+"/masjidapp/src/gambar/"+jo.getString("gambar")).
                                        diskCacheStrategy(DiskCacheStrategy.ALL).
                                        centerCrop().
                                        into(imgperson);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UstadzActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }
}
