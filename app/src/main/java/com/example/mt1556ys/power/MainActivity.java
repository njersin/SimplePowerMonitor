package com.example.mt1556ys.power;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    PowerReceiver mPowerReceiver;
    IntentFilter mDisconnectIntentFilter;
    IntentFilter mConnectIntentFilter;

    final static String CHARGER_CONNECTED = "Charger is connected";
    final static String CHARGER_NOT_CONNECTED = "Charger is not connected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPowerReceiver = new PowerReceiver();
        mDisconnectIntentFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        mConnectIntentFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);

        TextView powerTV = (TextView) findViewById(R.id.power);

        IntentFilter batteryStatusFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        Intent batteryStatus = registerReceiver(null, batteryStatusFilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        if (status == BatteryManager.BATTERY_PLUGGED_AC) {
            powerTV.setText(CHARGER_CONNECTED);
        } else {
            powerTV.setText(CHARGER_NOT_CONNECTED);
        }

        TextView batteryLevel = (TextView) findViewById(R.id.battery_level);
        float maxLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        float currentLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        int currentPercentageLevel = (int) (currentLevel / maxLevel * 100);

        batteryLevel.setText("Current Battery Level: " + currentPercentageLevel);

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mPowerReceiver, mDisconnectIntentFilter);
        registerReceiver(mPowerReceiver, mConnectIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mPowerReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
