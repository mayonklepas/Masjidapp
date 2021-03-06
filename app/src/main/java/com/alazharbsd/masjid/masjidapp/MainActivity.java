package com.alazharbsd.masjid.masjidapp;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView bartitlekanan,bartitlekanan2;
    String hari;
    Hijriahconverter hjc=new Hijriahconverter();
    ImageView imgpop;
    AlarmManager alm;
    PendingIntent pi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bartitlekanan=(TextView) findViewById(R.id.bartitlekanan);
        bartitlekanan2=(TextView) findViewById(R.id.bartitlekanan2);
        Date date=new Date();
        hari=new SimpleDateFormat("EEEE, d MMM yyyy").format(date);
        if(hari.split(",")[0].equals("Minggu")){
            hari="Ahad, "+hari.split(",")[1];
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bartitlekanan.setText(hari);
        bartitlekanan2.setText(hjc.gettglhijriah());

        if(koneksi()==false){
            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Pemberitahuan");
            adb.setMessage("Internet Tidak Aktif, Anda Harus Mengaktifkan Internet Anda untuk Menggunakan Aplikasi");
            adb.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            adb.setCancelable(false);
            adb.show();

        }else {

            Bundle ex=getIntent().getExtras();
            String sendnotif="";
            try {
                sendnotif=ex.getString("kat");
            }catch (Exception exs){
                sendnotif="freshopen";
            }
            if(sendnotif.equals("jadwal")){
                Config.mp.stop();
                Config.mp.release();
                Config.mp=null;
                //Config.notif_is_aktif=true;
                navigation.setSelectedItemId(R.id.shalat);
                getFragmentManager().beginTransaction().replace(R.id.content, new SholatFragment()).commit();

            }else{
                getFragmentManager().beginTransaction().replace(R.id.content, new MainFragment()).commit();
                final Dialog dialog = new Dialog(this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater li = getLayoutInflater();
                View inflate = li.inflate(R.layout.custompopup, null);
                dialog.setContentView(inflate);
                imgpop = (ImageView) inflate.findViewById(R.id.popupbg);
                Glide.with(this).load(Config.url + "/masjidapp/src/gambar/pictinfo.jpg").
                        diskCacheStrategy(DiskCacheStrategy.NONE).
                        placeholder(R.drawable.placeholder).
                        centerCrop().
                        into(imgpop);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 10 * 1000);
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);

                return;
            }


        }

                /*if (ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            },1);

            return;
        }*/


    }

    private boolean koneksi() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setTitle("Pemberitahuan");
        adb.setMessage("Keluar dari aplikasi akan menghentikan pemberitahuan, Lakukan?");
        adb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        adb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.show();
        //super.onBackPressed();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.thome:
                    getFragmentManager().beginTransaction().replace(R.id.content,new MainFragment()).commit();
                    bartitlekanan.setText(hari);
                    bartitlekanan2.setText(hjc.gettglhijriah());
                    return true;
                case R.id.shalat:
                    getFragmentManager().beginTransaction().replace(R.id.content,new SholatFragment()).commit();
                    bartitlekanan.setText("Jadwal Sholat,");
                    bartitlekanan2.setText("Tangerang Selatan");
                    return true;
                case R.id.qiblat:
                    getFragmentManager().beginTransaction().replace(R.id.content,new QiblatFragment()).commit();
                    bartitlekanan.setText("Arah Qiblat,");
                    bartitlekanan2.setText("Tangerang Selatan");
                    return true;
                case R.id.pesan:
                    if (Config.statuslogin==1){
                        getFragmentManager().beginTransaction().replace(R.id.content,new PengumumanFragment()).commit();
                        bartitlekanan.setText("Pesan dan");
                        bartitlekanan2.setText("Pengumuman");
                    }else{
                        Intent i=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                    }
                    return true;
                case R.id.lainnya:
                    getFragmentManager().beginTransaction().replace(R.id.content,new LainnyaFragment()).commit();
                    bartitlekanan.setText("Daftar Menu");
                    bartitlekanan2.setText("Lainnya");
                    return true;
            }
            return false;
        }

    };

}
