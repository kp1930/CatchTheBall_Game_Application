package com.theblackdiamonds.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.theblackdiamonds.R;

public class StartActivity extends AppCompatActivity {

//    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

//        interstitial = new InterstitialAd(this);
//        interstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        AdRequest adRequest = new AdRequest.Builder().build();
//        interstitial.loadAd(adRequest);
//
//        interstitial.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                displayInterstitial();
//            }
//        });
    }


//    public void displayInterstitial() {
//        if (interstitial.isLoaded()) {
//            interstitial.show();
//        }
//    }

    public void startGame(View view) {
        startActivity(new Intent(StartActivity.this, MainActivity.class));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}