package com.example.user.squash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 07.07.2017.
 */

public class GameActivity extends AppCompatActivity {
    private static final int PRECISION = 10;
    SquashCourtView sw;
    Point size;
    SensorManager sensorManager;
    Sensor sensorAccel,sensorMagnet;
    protected PowerManager.WakeLock mWakeLock;
    Ball ball;
    Rocket rocket;
    Display display;
    DrawAction drawAction;
    Handler h;
    SensorListener sensorListener;
    int FPS = 100;
    private boolean kickedfromrocket;
    String TAG = "SquashGame";
    TextView deads;
    int deadscount=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.game_activity);
        deads = (TextView) findViewById(R.id.viewdeads);
        deads.setText("Deads: 0");
        LinearLayout ll = findViewById(R.id.main_game_layout);
        ll.addView(sw);
        mainloop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, sensorAccel,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(sensorListener,sensorMagnet,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }

    private void init(){
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

        ball = new Ball(this);
        rocket = new Rocket();
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);

        resetDefault();

        Intent intent = getIntent();
        ball.setSpeed(intent.getIntExtra("speed_ball",ball.bitmapSource.getWidth()/2));
        rocket.setSpeed(intent.getIntExtra("speed_rocket",10));
        FPS = 100-intent.getIntExtra("speed_upd",50);

        sensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnet =  sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);



        sw = new SquashCourtView(this,size,ball,rocket);
        sensorListener = new SensorListener(display.getRotation(),sw);
    }

    private void resetDefault() {
        kickedfromrocket = false;
        ball.setX(size.x - size.x/2);
        ball.setY(100);
        rocket.setX(size.x - size.x/2 - rocket.rectWidth/2);
        rocket.setY(size.y - size.y/4);
    }

    private void mainloop(){
        Timer t = new Timer();
        h = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                drawAction = calculations();
                Log.d(TAG, "handleMessage: " + ball.getX());
                //canvas.drawRect(0,0,size.x,size.y,paintClear);
                sw.drawOperation(drawAction);;
            }
        };
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                h.sendEmptyMessage(0);
            }
        },0,FPS);
    }


    private DrawAction calculations() {
        if (ball.movementFlag == 10 || ball.movementFlag == 11){
            if (kickedfromrocket) ball.setY(ball.getY() - ball.getSpeed());
        }
        else {
            if (!kickedfromrocket) ball.setY(ball.getY() + ball.getSpeed());
        }

        if (ball.getY() > rocket.getY() - ball.bitmapSource.getHeight()){
            if (ball.getX() > rocket.getX() && ball.getX() < rocket.getX() + rocket.rectWidth){
                ball.movementFlag |= 10;
                kickedfromrocket = true;//kick from rocket
               /* if (rocket.getX() + rocket.rectWidth / 2 + PRECISION > ball.bitmapSource.getWidth()+ball.getX()
                        && rocket.getX() + rocket.rectWidth / 2 - PRECISION < ball.bitmapSource.getWidth()+ball.getX()) {
                        resetDefault();
                        return new DrawAction() {
                        @Override
                        public Canvas run(Canvas canvas) {
                            canvas = ball.drawThis(canvas,sw.getPaint());
                            canvas = rocket.drawThis(canvas,sw.getPaint());
                            return canvas;
                        }
                    };*/
                        //absorbed*/

            }
            else {
                //missed
                Log.d(TAG, "calculations: missed");
                deadscount++;
                deads.setText("Deads: " + deadscount);
                resetDefault();
                if (ball.getY() - rocket.getY() > 50) resetDefault();
            }
        }
        else {
            if (ball.getY() < 40) {
                kickedfromrocket = false;
                ball.movementFlag &= 01;
            } //kick from wall;
        }

        //Direction of the ball
        Random randInt = new Random();
        int ourRandom = randInt.nextInt(3);
        switch (ourRandom){
            case 0: if (ball.getX() < 0) break;
                ball.setX(ball.getX() - ball.getSpeed()); break;
            case 1: if (ball.getX()+ball.bitmapSource.getWidth() > size.x) break;
                ball.setX(ball.getX() + ball.getSpeed()); break;
            case 2:  break;
        }

        DrawAction da = new DrawAction() {
            @Override
            public Canvas run(Canvas canvas) {
                canvas = ball.drawThis(canvas,sw.getPaint());
                canvas = rocket.drawThis(canvas,sw.getPaint());
                return canvas;
            }
        };

        return da;
    }

}
