package com.alazharbsd.masjid.masjidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class KataDetailActivity extends AppCompatActivity {

    ImageView img_app_bar;
    TextView konten;
    String header,detail;
    FloatingActionButton appbar;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kata);
        img_app_bar=(ImageView) findViewById(R.id.app_bar_image);
        konten=(TextView) findViewById(R.id.konten);
        appbar=(FloatingActionButton) findViewById(R.id.btshare);
        tb=(Toolbar) findViewById(R.id.toolbar);
        Bundle ex=getIntent().getExtras();
        header=ex.getString("header");
        detail=ex.getString("detail");
        tb.setTitle(header);
        konten.setText(detail);
        appbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, detail+
                        ".\n\nDi Share dari Aplikasi Asy-Syarif, Bagikan dan Tebar Manfaat." +
                        "\nDownload Aplikasinya Di Playstore," +
                        " \"Masjid Asysyarif\" ");
                startActivity(Intent.createChooser(sharingIntent,"Share Ke"));
            }
        });
    }

}
