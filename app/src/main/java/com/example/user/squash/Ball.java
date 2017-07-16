package com.example.user.squash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by User on 07.07.2017.
 */

class Ball extends Equipment {
    private int speed= 20;
    private int directionX = 0 ,directionY = -1 ;
    private int lastX,lastY;
    public int movementFlag = 00;
    public int color = Color.rgb(139,69,19);
    Bitmap bitmapSource;
    private Bitmap b;
    Ball(Context context){
        setSpeed(speed);

        bitmapSource = BitmapFactory.decodeResource(context.getResources(), R.drawable.meatball);
        b = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight());

    }

    @Override
    public Canvas drawThis(Canvas canvas, Paint paint) {
        int c = paint.getColor();
    /*    paint.setColor(color);
        canvas.drawCircle(x,y,radius,paint);
        paint.setColor(c);
        canvas.drawLine(x+radius/3,y,x+radius/2,y+radius/2,paint);
      */



        canvas.drawBitmap(b,x,y,paint);
        return canvas;
    }

    @Override
    public void setY(int y) {
        lastY = y;
        super.setY(y);
    }

    @Override
    public void setX(int x) {
        lastX = x;
        super.setX(x);
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public int getSpeed() {
        return speed;
    }
}
