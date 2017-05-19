package com.alazharbsd.masjid.masjidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

public class ProfileActivity extends AppCompatActivity {

    TextView nama,email,nohp;
    Button logout;
    Toolbar tb;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nama=(TextView) findViewById(R.id.nama);
        email=(TextView) findViewById(R.id.email);
        nohp=(TextView) findViewById(R.id.nohp);
        logout=(Button) findViewById(R.id.logout);
        tb=(Toolbar) findViewById(R.id.toolbar);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.statuslogin=0;
                Toast.makeText(ProfileActivity.this, "Anda Telah Logout", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Bundle ex=getIntent().getExtras();
        id=ex.getString("iduser");
        nama.setText(ex.getString("namauser"));
        email.setText(ex.getString("emailuser"));
        nohp.setText(ex.getString("nohpuser"));
        tb.setTitle(ex.getString("namauser"));

    }


    public void loadata(){
        RequestQueue rq= Volley.newRequestQueue(ProfileActivity.this);
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/user.php?id="+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                email.setText(jo.getString("email"));
                                nohp.setText(jo.getString("nohp"));
                                tb.setTitle(jo.getString("nama"));
                                nama.setText(jo.getString("nama"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }
}
