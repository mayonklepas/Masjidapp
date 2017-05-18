package com.alazharbsd.masjid.masjidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoDetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView videoframe;
    TextView judul,tanggal,sinopsis;
    String videoid;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        videoframe=(YouTubePlayerView) findViewById(R.id.videoframe);
        judul=(TextView) findViewById(R.id.judul);
        tanggal=(TextView) findViewById(R.id.tanggal);
        sinopsis=(TextView) findViewById(R.id.sinopsis);
        Bundle ex=getIntent().getExtras();
        judul.setText(ex.getString("judul"));
        tanggal.setText(ex.getString("tanggal"));
        sinopsis.setText(ex.getString("sinopsis"));
        videoid=ex.getString("videoid");
        videoframe.initialize(Config.APIKEY,this);
        imgback=(ImageView) findViewById(R.id.imgback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.loadVideo(videoid);
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            youTubePlayer.setShowFullscreenButton(true);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
