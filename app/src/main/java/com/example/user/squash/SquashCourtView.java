package com.example.user.squash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

/**
 * Created by User on 07.07.2017.
 */


class SquashCourtView extends SurfaceView {
    private static final float SCALE_FACTOR_SENSOR = 15.0f;
    SurfaceHolder ourHolder;
    Paint paint,paintClear;
    Canvas canvas;
    String TAG = "SquashView";
    Ball ball;
    Rocket rocket;
    Point size;
    float lastValueAccel=0;


    public SquashCourtView(Context context,Point size,Ball ball,Rocket rocket) {
        super(context);
        init(context,size,ball,rocket);
        ourHolder = getHolder();
        ourHolder.addCallback(new HolderCallback());
    }

    public void drawOperation(DrawAction drawAction){
        if (ourHolder.getSurface().isValid()) {
            Log.d(TAG, "drawOperation: Drawing operation");
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas = drawAction.run(canvas);
            ourHolder.unlockCanvasAndPost(canvas);
        }
        else {
            Log.d(TAG, "drawOperation: WARNING!!");
        }
    }

    private void init(Context context,Point size,Ball ball,Rocket rocket){
        this.size = size;
        paintClear = new Paint();
        paintClear.setColor(Color.WHITE);
        this.ball = ball;
        this.rocket = rocket;
        paint = new Paint();
    }

    public Paint getPaint() {
        return paint;
    }


    private final int index=2;
    public void sendSensorValues(float[] valuesLinAccel) {
        int sign = (int)(valuesLinAccel[index]/Math.abs(valuesLinAccel[index])) ;

        if (sign < 0 && rocket.getX() < 0) return;
        if (sign > 0 && rocket.getX()+rocket.rectWidth > size.x) return;

        Log.d(TAG, "sendSensorValues: verticles: " + valuesLinAccel[index]);
        float delta = Math.abs(lastValueAccel + valuesLinAccel[index]) * SCALE_FACTOR_SENSOR;
        Log.d(TAG, "sendSensorValues: delta = " + delta);

        rocket.setX(rocket.getX() + ((int)delta + rocket.getSpeed())*sign );
        lastValueAccel = valuesLinAccel[index];
    }
}
