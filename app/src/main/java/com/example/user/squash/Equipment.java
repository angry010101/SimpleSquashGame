package com.example.user.squash;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by User on 07.07.2017.
 */

class Equipment {
    protected int x=0;
    protected int y=0;
    private int speed;

    Equipment(){
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public Canvas drawThis(Canvas canvas,Paint paint){ return null; }
}
