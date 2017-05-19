package com.alazharbsd.masjid.masjidapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText email,password,respassword,nohp,nama;
    Button register;
    String hasilresponse;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        respassword=(EditText) findViewById(R.id.repassword);
        nohp=(EditText) findViewById(R.id.nohp);
        nama=(EditText) findViewById(R.id.nama);
        register=(Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
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



    private void register() {
        RequestQueue rq = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "/masjidapp/rest/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    hasilresponse=response;

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map m = new HashMap();
                m.put("simpan", "simpan");
                m.put("email", email.getText().toString());
                m.put("password", password.getText().toString());
                m.put("nohp", nohp.getText().toString());
                m.put("nama", nama.getText().toString());
                return m;
            }
        };
        rq.add(sr);
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
               // Toast.makeText(RegisterActivity.this, hasilresponse, Toast.LENGTH_LONG).show();
                AlertDialog.Builder adb=new AlertDialog.Builder(RegisterActivity.this);
                adb.setTitle("Informasi");
                adb.setMessage(hasilresponse);
                adb.setPositiveButton("Kembali Ke Menu Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(i);
                    }
                });
                adb.show();
            }
        });
    }
}
