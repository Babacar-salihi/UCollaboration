package com.babacar.ucollaboration.UMarket.Activitys;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewPanier;

public class ServiceMinuteur extends Service {

    private CountDownTimer mCount;

    @Override
    public void onCreate() {
        super.onCreate();

        this.mCount = RecyclerViewPanier.sCountDownTimer;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        this.mCount = RecyclerViewPanier.sCountDownTimer;
        Log.d("SERVICEEEE4567E", "DANS SERVICEEEE");
        mCount.start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("SERVICEEEE4567E", "DESTROYYY");

    }
}
