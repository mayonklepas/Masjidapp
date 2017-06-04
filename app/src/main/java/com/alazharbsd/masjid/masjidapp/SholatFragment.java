package com.alazharbsd.masjid.masjidapp;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Minami on 11/05/2017.
 */

public class SholatFragment extends Fragment {

    TextView subuh,dzuhur,ashar,maghrib,isya,judul;
    String ssubuh,sdzuhur,sashar,smaghrib,sisya;
    ProgressBar pbsubuh,pbdzuhur,pbashar,pbmaghrib,pbisya;
    String hari;
    AlarmManager alm;
    PendingIntent pi;
    ToggleButton tbnotif;


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
        tbnotif=(ToggleButton) v.findViewById(R.id.tbnotif);
        Date date=new Date();
        hari=new SimpleDateFormat("EEEE, d MMM yyyy").format(date);
        if(hari.split(",")[0].equals("Minggu")){
            hari="Ahad, "+hari.split(",")[1];
        }
        judul.setText("Jadwal Sholat , "+hari);
        loadata();
        tbnotif.setVisibility(View.INVISIBLE);
        if(Config.notif_is_aktif==true){
            tbnotif.setChecked(true);
        }else {
            tbnotif.setChecked(false);
        }
        tbnotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Config.notif_is_aktif=true;
                    //loadalarm();
                    loadalarmsubuh();
                    loadalarmdzuhur();
                    loadalarmashar();
                    loadalarmmaghrib();
                    loadalarmisya();
                    Toast.makeText(getActivity(), "Alarm Waktu Sholat Diaktifkan", Toast.LENGTH_LONG).show();
                }else{
                    Config.notif_is_aktif=false;
                    stopalarmsubuh();
                    stopalarmdzuhur();
                    stopalarmashar();
                    stopalarmsmaghrib();
                    stopalarmisya();

                }
            }
        });
        return v;

    }


    public void loadata(){
        pbsubuh.setVisibility(View.VISIBLE);
        pbdzuhur.setVisibility(View.VISIBLE);
        pbashar.setVisibility(View.VISIBLE);
        pbmaghrib.setVisibility(View.VISIBLE);
        pbisya.setVisibility(View.VISIBLE);
        RequestQueue rq= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/masjidapp/rest/jadwal-sholat.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println(response);
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                ssubuh=jo.getString("subuh").toUpperCase();
                                sdzuhur=jo.getString("dzuhur").toUpperCase();
                                sashar=jo.getString("ashar").toUpperCase();
                                smaghrib=jo.getString("maghrib").toUpperCase();
                                sisya=jo.getString("isya").toUpperCase();
                                Config.subuh=jo.getString("subuh").substring(0,jo.getString("subuh").length()-3);
                                Config.dzuhur=jo.getString("dzuhur").substring(0,jo.getString("dzuhur").length()-3);
                                Config.meridiandzuhur=jo.getString("dzuhur").split(" ")[1].toUpperCase();
                                Config.ashar=jo.getString("ashar").substring(0,jo.getString("ashar").length()-3);
                                Config.maghrib=jo.getString("maghrib").substring(0,jo.getString("maghrib").length()-3);
                                Config.isya=jo.getString("isya").substring(0,jo.getString("isya").length()-3);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                tbnotif.setVisibility(View.VISIBLE);
                System.out.println(Config.meridiandzuhur);
            }
        });
    }

    public void loadalarm(){
        alm=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 9 );
        calendar.set(Calendar.MINUTE, 39);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.AM_PM,Calendar.PM);
        if(System.currentTimeMillis() > calendar.getTimeInMillis()){
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        Intent intent = new Intent(getActivity().getApplicationContext(), Alarmreceiver.class);
        intent.putExtra("waktu",1);
        pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 1, intent, 0);
        alm.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, pi);
    }

   private void loadalarmsubuh(){
       alm=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
       Calendar calendar = Calendar.getInstance();
       calendar.set(Calendar.HOUR,Integer.parseInt(Config.subuh.split(":")[0]));
       calendar.set(Calendar.MINUTE,Integer.parseInt(Config.subuh.split(":")[1]));
       calendar.set(Calendar.SECOND,0);
       calendar.set(Calendar.MILLISECOND,0);
       calendar.set(Calendar.AM_PM,Calendar.AM);
       if(System.currentTimeMillis() > calendar.getTimeInMillis()){
         calendar.add(Calendar.DAY_OF_YEAR,1);
       }
       Intent intent = new Intent(getActivity().getApplicationContext(), Alarmreceiver.class);
       intent.putExtra("waktu",1);
       pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 1, intent, 0);
       alm.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, pi);
    }

    private void loadalarmdzuhur(){
        alm=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,Integer.parseInt(Config.dzuhur.split(":")[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(Config.dzuhur.split(":")[1]));
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        if(Config.meridiandzuhur.equals("AM")){
            calendar.set(Calendar.AM_PM,Calendar.AM);
        }else{
            calendar.set(Calendar.AM_PM,Calendar.PM);
        }
        if(System.currentTimeMillis() > calendar.getTimeInMillis()){
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        Intent intent = new Intent(getActivity().getApplicationContext(), Alarmreceiver.class);
        intent.putExtra("waktu",2);
        pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 2, intent, 0);
        alm.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, pi);

    }

    private void loadalarmashar(){
        alm=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,Integer.parseInt(Config.ashar.split(":")[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(Config.ashar.split(":")[1]));
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.AM_PM,Calendar.PM);
        if(System.currentTimeMillis() > calendar.getTimeInMillis()){
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        Intent intent = new Intent(getActivity().getApplicationContext(), Alarmreceiver.class);
        intent.putExtra("waktu",3);
        pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 3, intent, 0);
        alm.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, pi);
    }

    private void loadalarmmaghrib(){
        alm=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,Integer.parseInt(Config.maghrib.split(":")[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(Config.maghrib.split(":")[1]));
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.AM_PM,Calendar.PM);
        if(System.currentTimeMillis() > calendar.getTimeInMillis()){
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        Intent intent = new Intent(getActivity().getApplicationContext(), Alarmreceiver.class);
        intent.putExtra("waktu",4);
        pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 4, intent, 0);
        alm.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, pi);
    }

    private void loadalarmisya(){
        alm=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,Integer.parseInt(Config.isya.split(":")[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(Config.isya.split(":")[1]));
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.AM_PM,Calendar.PM);
        if(System.currentTimeMillis() > calendar.getTimeInMillis()){
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        Intent intent = new Intent(getActivity().getApplicationContext(), Alarmreceiver.class);
        intent.putExtra("waktu",5);
        pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 5, intent, 0);
        alm.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, pi);
    }

    private void stopalarmsubuh(){
        Intent intent = new Intent(getActivity(), Alarmreceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void stopalarmdzuhur(){
        Intent intent = new Intent(getActivity(), Alarmreceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void stopalarmashar(){
        Intent intent = new Intent(getActivity(), Alarmreceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 3, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void stopalarmsmaghrib(){
        Intent intent = new Intent(getActivity(), Alarmreceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 4, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void stopalarmisya(){
        Intent intent = new Intent(getActivity(), Alarmreceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 5, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }






}
