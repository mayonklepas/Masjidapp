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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       Config.mp = new MediaPlayer().create(this,R.raw.adzan);
        Config.mp.setVolume(100,100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Config.mp !=null){
            Config.mp.start();
        }else {
            Config.mp = new MediaPlayer().create(this,R.raw.adzan);
            Config.mp.start();
        }
        return START_STICKY;
    }


    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }



    @Override
    public void onDestroy() {
        Config.mp.stop();
        Config.mp.release();
    }
}
