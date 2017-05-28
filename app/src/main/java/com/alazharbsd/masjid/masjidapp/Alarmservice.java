package com.alazharbsd.masjid.masjidapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by Minami on 25/05/2017.
 */

public class Alarmservice extends Service {
    MediaPlayer mp;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp=new MediaPlayer().create(this,R.raw.adzan);
        //mp.setLooping(true);
        mp.setVolume(100,100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
        return START_STICKY;
    }


    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }



    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
    }
}
