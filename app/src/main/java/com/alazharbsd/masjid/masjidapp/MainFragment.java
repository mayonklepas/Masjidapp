package com.alazharbsd.masjid.masjidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Minami on 11/05/2017.
 */

public class MainFragment extends Fragment {

    TextView hikmah,hadist,ayat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,container,false);
        hikmah=(TextView) v.findViewById(R.id.hikmah);
        hadist=(TextView) v.findViewById(R.id.hadist);
        ayat=(TextView) v.findViewById(R.id.ayat);
        loadata();
        return v;
    }


    public void loadata(){
        RequestQueue rq= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/kata-harian.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                hikmah.setText(jo.getString("hikmah_hari_ini"));
                                hadist.setText(jo.getString("hadist_hari_ini"));
                                ayat.setText(jo.getString("ayat_hari_ini"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
    }
}
