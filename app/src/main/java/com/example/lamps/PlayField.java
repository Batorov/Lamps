package com.example.lamps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class PlayField extends View {

    int r = 80;
    int ro = 40;
    int n = 4, m = 4; // размер поля
    boolean arrayLamps[][] = new boolean[n][m];

    Paint paint = new Paint();
    double touchX, touchY;

    public PlayField(Context context) {
        super(context);
        Random random = new Random();
        for (int i = 0; i < arrayLamps.length; i++) {
            for (int j = 0; j < arrayLamps[i].length; j++) {
                boolean flag = random.nextBoolean();
                if (flag)
                    arrayLamps[i][j] = true;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchX = event.getX();
            touchY = event.getY();
            calculation();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = 0;
        float y = 0;
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        boolean win1 = true, win2 = true;
        for (int i = 0; i < arrayLamps.length; i++) {
            for (int j = 0; j < arrayLamps[i].length; j++) {
                if (arrayLamps[i][j]) {
                    win1 = false;
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                }
                else {
                    win2 = false;
                    paint.setStyle(Paint.Style.STROKE);
                }
                x = j * (2 * r + ro) + ro + r;
                y = i * (2 * r + ro) + ro + r;
                canvas.drawCircle(x, y, r, paint);
            }
        }
        if (win1 || win2) {
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setTextSize(80);
            canvas.drawText("Победа", 100, 100, paint);
        }
    }

    void calculation() {
        int i, j;
        float x, y;
        j = Double.valueOf(touchX).intValue() / (2 * r + ro);
        i = Double.valueOf(touchY).intValue() / (2 * r + ro);
        if (i < arrayLamps.length && j < arrayLamps[0].length) {
            x = j * (2 * r + ro) + ro + r;
            y = i * (2 * r + ro) + ro + r;
            if ((Math.pow(x - touchX, 2) + Math.pow(y - touchY, 2)) <= r * r) {
                arrayLamps[i][j] = !arrayLamps[i][j];
                if (i > 0) arrayLamps[i - 1][j] = !arrayLamps[i - 1][j];
                if (i < arrayLamps.length - 1) arrayLamps[i + 1][j] = !arrayLamps[i + 1][j];
                if (j > 0) arrayLamps[i][j - 1] = !arrayLamps[i][j - 1];
                if (j < arrayLamps.length - 1) arrayLamps[i][j + 1] = !arrayLamps[i][j + 1];
            }

            invalidate();
        }
    }
}
