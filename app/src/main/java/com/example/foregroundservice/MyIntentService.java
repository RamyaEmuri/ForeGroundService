package com.example.foregroundservice;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Random;

public class MyIntentService extends IntentService {



    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    public MyIntentService(){
        super(MyIntentService.class.getSimpleName());
    }

    class MyIntentServiceBinder extends Binder {
        public MyIntentService getService(){
            return MyIntentService.this;
        }
    }

    private IBinder mBinder = new MyIntentServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(getString(R.string.service_demo_tag), "Service created");
    }


    @Override
    public void onStart(Intent intent, int startId){
        Log.i(getString(R.string.service_demo_tag),"Service Started");
        Toast.makeText(this,"Service Started", Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);
    }

    @NonNull
    @Override
    public IBinder onBind(Intent intent){
        Log.i(getString(R.string.service_demo_tag),"In OnBind");
        Toast.makeText(this,"Service Binded", Toast.LENGTH_SHORT).show();
        return mBinder;
    }

    public void onRebind(Intent intent){
        Log.i(getString(R.string.service_demo_tag),"In OnReBind");
        Toast.makeText(this,"Service ReBinded", Toast.LENGTH_SHORT).show();
        super.onRebind(intent);
    }
    @Override
    protected void onHandleIntent(@NonNull Intent intent){
        mIsRandomGeneratorOn = true;
        startRandomNumberGenerator();
    }
    private void startRandomNumberGenerator(){
        while (mIsRandomGeneratorOn){
            try {
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber = new Random().nextInt(MAX)+MIN;
                    Log.i(getString(R.string.service_demo_tag),"Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
                }
            }catch (InterruptedException e){
                Log.i(getString(R.string.service_demo_tag),"Thread Interrupted");
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsRandomGeneratorOn=false;
        Log.i(getString(R.string.service_demo_tag),"Service Destroyed");
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(getString(R.string.service_demo_tag),"In onUnbind");
        Toast.makeText(this, "Service UnBind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }


    public int getRandomNumber(){
        return mRandomNumber;
    }
}

