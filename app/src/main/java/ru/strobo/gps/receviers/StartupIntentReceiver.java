package ru.strobo.gps.receviers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ru.strobo.gps.services.LocationService;
import android.util.Log;

public class StartupIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, LocationService.class);
            context.startService(serviceIntent);
            Log.v("TEST", "Service loaded at start");
        } else {

        }
    }
}