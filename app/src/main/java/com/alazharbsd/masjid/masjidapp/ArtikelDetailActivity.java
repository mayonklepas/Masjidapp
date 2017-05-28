package com.alazharbsd.masjid.masjidapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArtikelDetailActivity extends AppCompatActivity {

    ImageView img_app_bar;
    TextView judul,tanggal,konten;
    String id;
    FloatingActionButton appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel_detail);
        img_app_bar=(ImageView) findViewById(R.id.app_bar_image);
        judul=(TextView) findViewById(R.id.judul);
        tanggal=(TextView) findViewById(R.id.tanggal);
        konten=(TextView) findViewById(R.id.konten);
        appbar=(FloatingActionButton) findViewById(R.id.btshare);
        Bundle ex=getIntent().getExtras();
        id=String.valueOf(ex.getInt("id"));
        loadata();
        appbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, judul.getText()+"\n"+tanggal.getText()+"\n\n"+konten.getText()+
                        ".\n\nDi Share dari Aplikasi Asy-Syarif, Bagikan dan Tebar Manfaaf." +
                        "\nDownload Aplikasinya Di Playstore," +
                        " \"Masjid Asysyarif\" ");
                startActivity(Intent.createChooser(sharingIntent,"Share Ke"));
            }
        });
    }


    public void loadata(){
        RequestQueue rq= Volley.newRequestQueue(ArtikelDetailActivity.this);
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/artikel.php?id="+id,
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
                                    date=new SimpleDateFormat("yyyy/MM/dd").parse(jo.getString("tanggal").replace("-","/"));
                                    hari=new SimpleDateFormat("EEEE, d MMM yyyy").format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(hari.split(",")[0].equals("Minggu")){
                                    hari="Ahad, "+hari.split(",")[1];
                                }
                                judul.setText(jo.getString("judul"));
                                tanggal.setText(hari);
                                konten.setText(jo.getString("konten"));
                                Glide.with(ArtikelDetailActivity.this).
                                        load(Config.url+"/masjidapp/src/gambar/"+jo.getString("gambar")).
                                        crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(img_app_bar);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ArtikelDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }


}
