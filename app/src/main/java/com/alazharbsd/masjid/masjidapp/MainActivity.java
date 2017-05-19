package com.alazharbsd.masjid.masjidapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView bartitlekanan,bartitlekanan2;
    String hari;
    Hijriahconverter hjc=new Hijriahconverter();


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
        bartitlekanan.setText(hari);
        bartitlekanan2.setText(hjc.gettglhijriah());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getFragmentManager().beginTransaction().replace(R.id.content,new MainFragment()).commit();

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
