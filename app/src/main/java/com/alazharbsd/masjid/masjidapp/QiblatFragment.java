package com.alazharbsd.masjid.masjidapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Config;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Minami on 11/05/2017.
 */

public class QiblatFragment extends Fragment implements LocationListener {

    private static final String TAG = "compas";

    ImageView imgqiblat,imgpenanda;
    TextView tvgps;
    private SensorManager sman;
    private Sensor sn;
    private SampleView kiblat;
    private float[] mvalues;
    public double longmasjid;
    public double latmasjid;
    double bearing;
    LocationManager locma;
    String provider;
    float currentDegree;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qiblat, container, false);
        imgqiblat = (ImageView) v.findViewById(R.id.imgqiblat);
        imgpenanda = (ImageView) v.findViewById(R.id.imgpenanda);
        tvgps = (TextView) v.findViewById(R.id.tgps);
        kiblat=new SampleView(getActivity().getApplicationContext());
        sman = (SensorManager) getActivity().getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        sn = sman.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        locma = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locma.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            },1);

            return v;
        }
        Location location = locma.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        } else {
            tvgps.setText("Location not available");
        }
        return v;
    }


    private double gantitempat(double longmasjid, double latmasjid){
        double longKabah = 39.82616111;
        double latKabah = 21.42250833;
        double longdelta= (longKabah - longmasjid);
        double y=Math.sin(longdelta)* Math.cos(latKabah);
        double x=Math.cos(latmasjid)* Math.sin(latKabah)-Math.sin(latmasjid)*Math.cos(latKabah)*Math.cos(longdelta);
        double bearing = Math.toDegrees(Math.atan2(y,x));
        return bearing;
    }

    private final SensorEventListener mlistener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            /*mvalues = event.values;
            if(kiblat!=null){
                kiblat.invalidate();
            }*/

           // float degree = Math.round(event.values[0]+((float)gantitempat(longmasjid,latmasjid)/1.5));
            float degree = Math.round(event.values[0]);
            RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ra.setDuration(200);
            ra.setFillAfter(true);
            imgqiblat.startAnimation(ra);
            currentDegree = -degree;
            /*if (degree==360.0){
                tvgps.setText("Arah Tepat");
                tvgps.setTextColor(Color.parseColor("#1be528"));
                imgpenanda.setColorFilter(Color.parseColor("#0fc41b"));
                imgqiblat.setColorFilter(Color.parseColor("#0fc41b"));
            }else {
                tvgps.setText("Arah Belum Tepat");
                tvgps.setTextColor(Color.parseColor("#ea160b"));
                imgpenanda.setColorFilter(Color.parseColor("#000000"));
                //imgqiblat.setColorFilter(Color.parseColor("#000000"));
            }*/



        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private double hitungkiblat(double longmasjid, double latmasjid) {
        double lngKabah = 39.82616111;
        double latKabah = 21.42250833;
        double lKlM = (lngKabah - longmasjid);
        double sinLKLM = Math.sin(lKlM * 2.0 * Math.PI / 360);
        double cosLKLM = Math.cos(lKlM * 2.0 * Math.PI / 360);
        double sinLM = Math.sin(latmasjid * 2.0 * Math.PI / 360);
        double cosLM = Math.cos(latmasjid * 2.0 * Math.PI / 360);
        double tanLK = Math.tan(latKabah * 2 * Math.PI / 360);
        double denominator = (cosLM * tanLK) - sinLM * cosLKLM;
        double Qibla;
        double direction;
        Qibla = Math.atan2(sinLKLM, denominator) * 180 / Math.PI;
        direction = Qibla < 0 ? Qibla + 360 : Qibla;
        return direction;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locma.requestLocationUpdates(provider, 0,0 , this);
        sman.registerListener(mlistener,sn,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locma.removeUpdates(this);
        sman.unregisterListener(mlistener);
    }

    @Override
    public void onLocationChanged(Location location) {
        latmasjid = location.getLatitude();
        longmasjid = location.getLongitude();
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


    private class SampleView extends View {
        private Paint mPaint = new Paint();
        private Path mPath = new Path();
        private boolean mAnimate;


        public SampleView(Context context) {
            super(context);

            // Construct a wedge-shaped path
            mPath.moveTo(0, -100);
            mPath.lineTo(20, 120);
            mPath.lineTo(0, 100);
            mPath.lineTo(-20, 120);
            mPath.close();
        }

        //make an arrow for pointing direction

        protected void onDraw(Canvas canvas) {
            Paint paint = mPaint;
            paint.setAntiAlias(true);
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);

            int w = canvas.getWidth();
            int h = canvas.getHeight();
            int cx = w / 2;
            int cy = h / 2;
            float Qibla = (float) gantitempat(longmasjid,latmasjid);
            canvas.translate(cx, cy);
            if (mvalues != null) {
                canvas.rotate(-(mvalues[0] + Qibla));
            }
            canvas.drawPath(mPath, mPaint);
        }

        @Override
        protected void onAttachedToWindow() {
            mAnimate = true;
            if (Config.DEBUG) Log.d(TAG, "onAttachedToWindow. mAnimate=" + mAnimate);
            super.onAttachedToWindow();
        }

        @Override
        protected void onDetachedFromWindow() {
            mAnimate = false;
            if (Config.DEBUG) Log.d(TAG, "onDetachedFromWindow. mAnimate=" + mAnimate);
            super.onDetachedFromWindow();
        }

    }







}
