package com.alazharbsd.masjid.masjidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Minami on 11/05/2017.
 */

public class SholatFragment extends Fragment {

    TextView subuh,dzuhur,ashar,maghrib,isya,judul;
    String ssubuh,sdzuhur,sashar,smaghrib,sisya;
    ProgressBar pbsubuh,pbdzuhur,pbashar,pbmaghrib,pbisya;
    String hari;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_sholat,container,false);
        subuh=(TextView) v.findViewById(R.id.subuh);
        dzuhur=(TextView) v.findViewById(R.id.dzuhur);
        ashar=(TextView) v.findViewById(R.id.ashar);
        maghrib=(TextView) v.findViewById(R.id.maghrib);
        isya=(TextView) v.findViewById(R.id.isya);
        judul=(TextView) v.findViewById(R.id.judul);
        pbsubuh=(ProgressBar) v.findViewById(R.id.pbsubuh);
        pbdzuhur=(ProgressBar) v.findViewById(R.id.pbdzuhur);
        pbashar=(ProgressBar) v.findViewById(R.id.pbashar);
        pbmaghrib=(ProgressBar) v.findViewById(R.id.pbmaghrib);
        pbisya=(ProgressBar) v.findViewById(R.id.pbisya);
        Date date=new Date();
        hari=new SimpleDateFormat("EEEE, d MMM yyyy").format(date);
        if(hari.split(",")[0].equals("Minggu")){
            hari="Ahad, "+hari.split(",")[1];
        }
        judul.setText("Jadwal Sholat , "+hari);
        loadata();
        return v;
    }


    public void loadata(){
        pbsubuh.setVisibility(View.VISIBLE);
        pbdzuhur.setVisibility(View.VISIBLE);
        pbashar.setVisibility(View.VISIBLE);
        pbmaghrib.setVisibility(View.VISIBLE);
        pbisya.setVisibility(View.VISIBLE);
        RequestQueue rq= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr=new StringRequest(Request.Method.GET, "http://muslimsalat.com/daily.json?key=276f474cf500d68dbda226020830e753",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jos=new JSONObject(response);
                            JSONArray ja=new JSONArray(jos.getString("items"));
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                ssubuh=jo.getString("fajr").toUpperCase();
                                sdzuhur=jo.getString("dhuhr").toUpperCase();
                                sashar=jo.getString("asr").toUpperCase();
                                smaghrib=jo.getString("maghrib").toUpperCase();
                                sisya=jo.getString("isha").toUpperCase();

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
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                pbsubuh.setVisibility(View.GONE);
                pbdzuhur.setVisibility(View.GONE);
                pbashar.setVisibility(View.GONE);
                pbmaghrib.setVisibility(View.GONE);
                pbisya.setVisibility(View.GONE);
                subuh.setText(ssubuh);
                dzuhur.setText(sdzuhur);
                ashar.setText(sashar);
                maghrib.setText(smaghrib);
                isya.setText(sisya);
            }
        });
    }
}
