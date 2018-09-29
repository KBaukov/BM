package ru.strobo.gps.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.strobo.gps.data.BmDbHelper;
import ru.strobo.gps.data.ent.Point;

public class LocationService extends Service {

    private static final String TAG = "BMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;

    private String devID;
    private BmDbHelper db;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //private Context cntx;

    public LocationService() { }


    private class LocationListener implements android.location.LocationListener{
        Location mLastLocation;

        Handler mHandler = new Handler();


        public LocationListener(String provider)
        {
            Log.e(TAG, "LocationListener " + provider);
            showMsg("LocationListener " + provider);
            //Toast.makeText(cntx, "LocationListener "+ provider, Toast.LENGTH_SHORT).show();
            mLastLocation = new Location(provider);
        }
        @Override
        public void onLocationChanged(Location location)
        {
            Log.e(TAG, "onLocationChanged: " + location);
            Double lat = location.getLatitude();
            Double lon = location.getLongitude();
            String loc = lat.toString()+"," +lon.toString();
            showMsg(loc);
            db.setPoint(
                    new Point(
                            sdf.format(new Date()),
                            loc,
                            0
                    )
            );
            mLastLocation.set(location);
        }
        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
            showMsg("onProviderDisabled " + provider);
        }
        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
            showMsg("onProviderEnabled " + provider);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
            showMsg("onStatusChanged " + provider);
        }
    }
    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        showMsg( "Служба запущена");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
    @Override
    public void onCreate()  {
        Log.e(TAG, "onCreate");
        showMsg( "onCreate");
        initializeLocationManager();

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
            //showMsg( "fail to request location update, ignore: " + ex.getMessage() );
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
            //showMsg( "network provider does not exist, " + ex.getMessage() );
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

        db = new BmDbHelper(this);
        devID = db.getConfig().getDevId();

    }
    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        showMsg("Служба остановлена");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }
    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        showMsg("initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void showMsg(final String msg) {
        final Context MyContext = this;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast toast1 = Toast.makeText(MyContext, msg, Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }
//    private void showMsg(String msg) {
//        Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }


}
