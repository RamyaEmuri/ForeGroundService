package com.example.foregroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private BroadCastReceivers mReceiver = new BroadCastReceivers();

    Button buttonStart, buttonStop;
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });

        Button buttonIntentService = findViewById(R.id.buttonIntentService);
        buttonIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IntentService.class);
                startActivity(intent);
            }
        });


//-----------------------------------background service----------------
        Button startBackService = (Button)findViewById(R.id.buttonStartBackGround);
        startBackService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startServiceIntent = new Intent(MainActivity.this, BackGroundService.class);
                startService(startServiceIntent);
            }
        });

        Button stopBackService = (Button)findViewById(R.id.buttonStopBackGround);
        stopBackService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopServiceIntent = new Intent(MainActivity.this, BackGroundService.class);
                stopService(stopServiceIntent);
            }
        });
//--------------------------------------------------Broad cast receivers--------------------
        Button broadCastReceivers = (Button)findViewById(R.id.sendBroadcast);
        broadCastReceivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendCustomBroadcast(view);
            }
        });


        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        this.registerReceiver(mReceiver, filter);

        LocalBroadcastManager.getInstance(this).registerReceiver
                (mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));
//-----------------------------------Bound service--------------------------
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BoundService.class);
                startActivity(intent);
            }
        });
    }

    public void sendCustomBroadcast(View view) {
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(customBroadcastIntent);
    }

    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForeGroundService.class);
        serviceIntent.putExtra("inputExtra", "Running ForeGround");

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, ForeGroundService.class);
        stopService(serviceIntent);
    }
}


