package com.example.foregroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BoundService extends AppCompatActivity {

    CustomBoundService customBoundService;
    boolean serviceBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);

        final TextView timestampText = (TextView) findViewById(R.id.timestamp_text);
        Button printTimestampButton = (Button) findViewById(R.id.print_timestamp);
        Button stopServiceButon = (Button) findViewById(R.id.stop_service);

                printTimestampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceBound) {
                    timestampText.setText(customBoundService.getTimestamp());
                }
            }
        });

        stopServiceButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceBound) {
                    unbindService(mServiceConnection);
                    serviceBound = false;
                }
                Intent intent = new Intent(BoundService.this,
                        CustomBoundService.class);
                stopService(intent);
            }
        });
    }
        @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, CustomBoundService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceBound) {
            unbindService(mServiceConnection);
            serviceBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           CustomBoundService.MyBinder myBinder = (CustomBoundService.MyBinder) service;
            customBoundService = myBinder.getService();
            serviceBound = true;
        }
    };
}
