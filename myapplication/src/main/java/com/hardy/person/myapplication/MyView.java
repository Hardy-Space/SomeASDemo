package com.hardy.person.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class MyView extends View {

    private float preX ;
    private float preY ;
    private Path path ;
    public Paint paint = null;
    Bitmap cacheBitmap = null;
    Canvas cacheCanvas = null ;

    public MyView(Context context, int width,int height) {
        super(context);
        cacheBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888) ;
        cacheCanvas = new Canvas() ;
        cacheCanvas.setBitmap(cacheBitmap);
        path = new Path() ;
        paint = new Paint(Paint.DITHER_FLAG) ;
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX() ;
        float currentY = event.getY() ;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(currentX, currentY);
                preX = currentX ;
                preY = currentY ;
                break;
            case MotionEvent.ACTION_MOVE:
                //画贝塞尔曲线，四个参数分别代表控制点和结束点的x、y坐标，如果没有设置moveTo，则起点默认为（0，0）
                path.quadTo(preX, preY, currentX, currentY);
                preX = currentX ;
                preY = currentY ;
                break;
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(path, paint);
                //重置路径
                path.reset();
                break;
        }
        invalidate();
        return true ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(cacheBitmap, 0, 0, paint);
        canvas.drawPath(path,paint);


    }
}
