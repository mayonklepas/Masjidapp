package com.alazharbsd.masjid.masjidapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Minami on 25/05/2017.
 */

public class Alarmreceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extra=intent.getExtras();
        String waktu="";
        int id=extra.getInt("waktu");

        switch (id){
            case  1:waktu="Subuh";
                break;
            case  2:waktu="Dzuhur";
                break;
            case  3:waktu="Ashar";
                break;
            case  4:waktu="Maghrib";
                break;
            case  5:waktu="Isya";
                break;
            default:waktu="Default";
        }
        Toast.makeText(context, "Waktunya Sholat "+waktu+" Telah Tiba", Toast.LENGTH_LONG).show();
        intent = new Intent(context, MainActivity.class);
        intent.putExtra("kat","jadwal");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.mosquedomes)
                .setContentTitle("Pemberitahuan")
                .setContentText("Waktunya Sholat "+waktu)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notificationBuilder.build());

        Intent serviceaudio=new Intent(context,Alarmservice.class);
        context.startService(serviceaudio);

    }

}
