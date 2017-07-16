package com.example.user.squash;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by User on 07.07.2017.
 */

class Rocket extends Equipment {
    public int rectWidth = 200, rectHeight = 25;
    int color = Color.rgb(139,69,19);
    private int marginPlate=20;
    public int plateHeight = 50;
    public int steamHeight = 150;
    public int steamCount = 3;
    public int steamPrecision = 4;
    public int steamWidth = 30;
    public int steamColor = Color.BLACK;
    public int marginSteamPlateTop = 10;
    public int marginSteamPlateCorner = 10;
    public int marginSteams = (rectWidth-marginSteamPlateCorner)/steamCount;
    private int steamC=0;
    private int steamCFPS=3;
    @Override
    public Canvas drawThis(Canvas canvas, Paint paint) {
        int lastColor = paint.getColor();
        paint.setColor(color);
        canvas.drawRoundRect(new RectF(x+marginPlate,y,x+rectWidth-marginPlate,y+rectHeight+plateHeight), 20, 20, paint);
        canvas.drawRect(new Rect(x,y,x+rectWidth,y+rectHeight),paint);

        if (steamC++>steamCFPS){
            canvas = drawSteam(canvas, paint);
            steamC=0;
        }
        paint.setColor(lastColor);
        return canvas;
    }

    private Canvas drawSteam(Canvas canvas,Paint paint) {
        paint.setColor(steamColor);
        float f = paint.getStrokeWidth();
        paint.setStrokeWidth(4);
        for (int j=0;j<steamCount;j++){
            for (int i=0;i<steamPrecision;i++){
                canvas.drawLine(x+(steamWidth/2)+marginSteamPlateCorner+(steamWidth/2)*(int)Math.pow(-1,i)+marginSteams*j,
                        y-steamHeight-marginSteamPlateTop+(steamHeight/steamPrecision)*i,
                        x+steamWidth/2+(steamWidth/2)*(int)Math.pow(-1,i+1)+marginSteams*j,
                        y-marginSteamPlateTop-(steamPrecision-i-1)*(steamHeight/steamPrecision),paint);
            }
        }
        paint.setStrokeWidth(f);
        return canvas;
    }
}
