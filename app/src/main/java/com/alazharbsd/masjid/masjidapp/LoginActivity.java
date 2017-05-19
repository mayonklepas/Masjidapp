package com.alazharbsd.masjid.masjidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    String resemail="",respassword="";
    TextView daftarbaru,lupapassword;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        daftarbaru=(TextView) findViewById(R.id.daftarbaru);
        lupapassword=(TextView) findViewById(R.id.lupapassword);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        lupapassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        daftarbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
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


    private void login() {
        RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "/masjidapp/rest/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONArray ja = new JSONArray(response);
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                resemail = jo.getString("email");
                                respassword = jo.getString("password");
                                Config.iduser=jo.getString("id");
                                Config.namauser=jo.getString("nama");
                                Config.emailuser=jo.getString("email");
                                Config.nohpuser=jo.getString("nohp");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map m = new HashMap();
                m.put("login", "login");
                m.put("email", email.getText().toString());
                m.put("password", password.getText().toString());
                return m;
            }
        };
        rq.add(sr);
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                if (resemail.equals(email.getText().toString()) &&
                        respassword.equals(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Anda Telah Login", Toast.LENGTH_LONG).show();
                    Config.statuslogin = 1;
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Maaf Email Atau Password Salah", Toast.LENGTH_LONG).show();
                    Config.statuslogin = 0;
                }
            }
        });
    }
}
