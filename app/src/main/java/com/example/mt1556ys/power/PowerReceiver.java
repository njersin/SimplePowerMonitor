package com.example.mt1556ys.power;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

/**
 * Created by mt1556ys on 10/12/15.
 */
public class PowerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        MainActivity activity = (MainActivity) context;

        TextView powerTV = (TextView)activity.findViewById(R.id.power);
        if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            powerTV.setText(MainActivity.CHARGER_NOT_CONNECTED);
        }

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            powerTV.setText(MainActivity.CHARGER_CONNECTED);
        }

    }

}
