package com.example.user.squash;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Surface;

/**
 * Created by User on 10.07.2017.
 */

public class SensorListener implements SensorEventListener {
    float[] valuesAccel = new float[3];
    float[] valuesMagnet = new float[3];
    float[] valuesResult2 = new float[3];
    float[] inR = new float[9];
    float[] outR = new float[9];
    float[] r = new float[9];
    int rotation;
    SquashCourtView sw;

    SensorListener(int rotation, SquashCourtView sw){
        this.rotation = rotation;
        this.sw = sw;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                for (int i = 0; i < 3; i++) {
                    valuesAccel[i] = event.values[i];
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                String TAG = "magnetic";
                for (int i=0; i < 3; i++){
                    valuesMagnet[i] = event.values[i];
                    Log.d(TAG, "onSensorChanged: " + valuesMagnet[0]);
                }
                break;
        }
        getDeviceOrientation();
        sw.sendSensorValues(valuesResult2);
    }

    void getDeviceOrientation() {
        SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet);
        int x_axis = SensorManager.AXIS_X;
        int y_axis = SensorManager.AXIS_Y;
        switch (rotation) {
            case (Surface.ROTATION_0): break;
            case (Surface.ROTATION_90):
                x_axis = SensorManager.AXIS_Y;
                y_axis = SensorManager.AXIS_MINUS_X;
                break;
            case (Surface.ROTATION_180):
                y_axis = SensorManager.AXIS_MINUS_Y;
                break;
            case (Surface.ROTATION_270):
                x_axis = SensorManager.AXIS_MINUS_Y;
                y_axis = SensorManager.AXIS_X;
                break;
            default: break;
        }
        SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR);
        SensorManager.getOrientation(outR, valuesResult2);
     /*   valuesResult2[0] = (float) Math.toDegrees(valuesResult2[0]);
        valuesResult2[1] = (float) Math.toDegrees(valuesResult2[1]);
        valuesResult2[2] = (float) Math.toDegrees(valuesResult2[2]);*/
        return;
    }

}