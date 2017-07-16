package com.example.user.squash;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by User on 07.07.2017.
 */

interface DrawAction {
    public Canvas run(Canvas canvas);
}
