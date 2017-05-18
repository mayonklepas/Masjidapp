package com.alazharbsd.masjid.masjidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Minami on 13/05/2017.
 */

public class PengumumanFragment extends Fragment {

    RecyclerView recdata;
    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> url=new ArrayList<>();
    ArrayList<String> header=new ArrayList<>();
    ArrayList<String> detail=new ArrayList<>();
    RecyclerView.LayoutManager layman;
    RecyclerView.Adapter adapter;
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_pengumuman,container,false);
        pb=(ProgressBar) v.findViewById(R.id.pb);
        recdata=(RecyclerView) v.findViewById(R.id.recdata);
        layman=new LinearLayoutManager(getActivity().getApplicationContext());
        recdata.setLayoutManager(layman);
        recdata.setHasFixedSize(true);
        recdata.setItemAnimator(new DefaultItemAnimator());
        adapter=new Adapterpengumuman(id,url,header,detail,this);
        loadata();
        return v;
    }



    public void loadata(){
        pb.setVisibility(View.VISIBLE);
        RequestQueue rq= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/pengumuman.php",
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
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
