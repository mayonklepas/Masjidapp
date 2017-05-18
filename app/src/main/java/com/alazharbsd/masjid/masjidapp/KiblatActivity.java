package com.alazharbsd.masjid.masjidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class KiblatActivity extends AppCompatActivity {

    double longi,lati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiblat);
        koordinatlisten();
    }

    private void koordinatlisten() {
        LocationManager locma = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locma.isProviderEnabled(LocationManager.GPS_PROVIDER) || !locma.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            AlertDialog.Builder adb=new AlertDialog.Builder(KiblatActivity.this);
            adb.setTitle("Pemberitahuan");
            adb.setMessage("GPS Dan Network Location Tidak Aktif, Mohon Untuk Diaktifkan");
            adb.setPositiveButton("Buka Pengaturan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
            });
            adb.setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            adb.show();
        }else {
            LocationListener locis = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    longi = location.getLongitude();
                    lati = location.getLatitude();
                    Toast.makeText(KiblatActivity.this, String.valueOf(longi) + "," + String.valueOf(lati), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            Criteria ct = new Criteria();
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locma.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, locis);
        }
    }

}
