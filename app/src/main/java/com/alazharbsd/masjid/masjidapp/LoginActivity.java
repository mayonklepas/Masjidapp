package com.alazharbsd.masjid.masjidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    String resemail,respassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        login();
    }


    private void login(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RequestQueue rq= Volley.newRequestQueue(LoginActivity.this);
                    StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/login.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray ja=new JSONArray(response);
                                        for (int i = 0; i < ja.length() ; i++) {
                                            JSONObject jo = ja.getJSONObject(i);
                                            resemail=jo.getString("email");
                                            respassword=jo.getString("password");
                                        }
                                        if (resemail.equals(email.getText().toString()) &&
                                                password.equals(password.getText().toString())){
                                            Toast.makeText(LoginActivity.this, "Anda Telah Login", Toast.LENGTH_LONG).show();
                                                Config.statuslogin=1;
                                        }else {
                                            Toast.makeText(LoginActivity.this, "Maaf Email Atau Password Salah", Toast.LENGTH_LONG).show();
                                            Config.statuslogin=0;
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
                            });
                    rq.add(sr);

            }
        });
    }
}
