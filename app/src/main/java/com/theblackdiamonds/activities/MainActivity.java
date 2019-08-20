package com.theblackdiamonds.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.theblackdiamonds.R;
import com.theblackdiamonds.utils.SoundPlayer;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    int screenWidth, screenHeight, frameHeight,
            boxSize, boxY, boxSpeed,
            orangeX, orangeY, orangeSpeed,
            pinkX, pinkY, pinkSpeed,
            blackX, blackY, blackSpeed,
            score = 0;
    private TextView scoreLabel, startLabel;
    private ImageView box, orange, pink, black;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;
    private boolean action_flg = false, start_flg = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = new SoundPlayer(this);

        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        boxSpeed = Math.round(screenHeight / 60);
        orangeSpeed = Math.round(screenWidth / 60);
        pinkSpeed = Math.round(screenWidth / 36);
        blackSpeed = Math.round(screenWidth / 45);

        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        black.setX(-80);
        black.setY(-80);

        scoreLabel.setText("Score : 0");
    }

    @SuppressLint("SetTextI18n")
    public void changePos() {
        hitCheck();

        orangeX -= orangeSpeed;
        if (orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (int) Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        blackX -= blackSpeed;
        if (blackX < 0) {
            blackX = screenWidth + 10;
            blackY = (int) Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        pinkX -= pinkSpeed;
        if (pinkX < 0) {
            pinkX = screenWidth + 5000;
            pinkY = (int) Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        if (action_flg) {
            boxY -= boxSpeed;
        } else {
            boxY += boxSpeed;
        }
        if (boxY < 0) {
            boxY = 0;
        }
        if (boxY > frameHeight - boxSize) {
            boxY = frameHeight - boxSize;
        }
        box.setY(boxY);
        scoreLabel.setText("Score : " + score);
    }


    public void hitCheck() {
        int orangeCenterX = orangeX + orange.getWidth() / 2;
        int orangeCenterY = orangeY + orange.getHeight() / 2;

        if (0 <= orangeCenterX && orangeCenterX <= boxSize && boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {
            score += 10;
            orangeX = -10;
            sound.playHitSound();
        }

        int pinkCenterX = pinkX + pink.getWidth() / 2;
        int pinkCenterY = pinkY + pink.getHeight() / 2;

        if (0 <= pinkCenterX && pinkCenterX <= boxSize && boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize) {
            score += 30;
            pinkX = -10;
            sound.playHitSound();
        }

        int blackCenterX = blackX + black.getWidth() / 2;
        int blackCenterY = blackY + black.getHeight() / 2;

        if (0 <= blackCenterX && blackCenterX <= boxSize && boxY <= blackCenterY && blackCenterY <= boxY + boxSize) {
            timer.cancel();
            timer = null;

            sound.playOverSound();

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }
    }

    public boolean onTouchEvent(MotionEvent me) {
        if (!start_flg) {
            start_flg = true;

            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            boxY = (int) box.getY();
            boxSize = box.getHeight();
            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        } else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }

        return true;
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