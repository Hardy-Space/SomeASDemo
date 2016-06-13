package com.hardy.person.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class PlaneGameView extends View {
    //背景图的Bitmap对象
    Bitmap background;
    //飞机图片的Bitmap对象
    Bitmap plane;
    //飞机图片宽
    final int planeWidth = 200;
    //飞机图片高度
    final int planeHeight = 200;
    //飞机图片起点坐标
    float planePositionX;
    float planePositionY;
    //绘制背景图的矩阵参数
    Matrix matrix;
    //背景图的移动速度
    float yTranslateSpeed = 3;
    //背景图绘制的开始Y坐标
    int startY = 0;

    public PlaneGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取背景图的Bitmap对象
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
        //获取飞机图片的Bitmap对象
        plane = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane);
        //飞机画在屏幕底部居中的位置
        planePositionX = MainActivity.screenWidth / 2 - planeWidth / 2;
        planePositionY = MainActivity.screenHeight - 300;
        //定义绘制背景图的矩阵参数
        matrix = new Matrix();
        matrix.setTranslate(0, yTranslateSpeed);
        //开启子线程来一直平移背景图达到飞机运动的效果
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //下次绘制时开始的Y坐标要变成已经改变的当前Y坐标
                if (startY + MainActivity.screenHeight > background.getHeight() - yTranslateSpeed-10) {
                    startY = 0;
                } else {
                    startY += yTranslateSpeed;
                }
                postInvalidate();
            }
        }, 0, 100);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        //挖取背景图的不同区域
        //按我的理解，最后一个参数filter是当matrix里面不只设置了translation(平移)这一个属性的时候有用，意思就是当设置了多个属性时如果filter为true，则只会保留translation这一个属性。
        Bitmap bitmap2 = Bitmap.createBitmap(background, 0, startY, background.getWidth(), startY+MainActivity.screenHeight, matrix, false);
        //画背景
        canvas.drawBitmap(bitmap2, 0, 0, null);
        //定义飞机画在画布上的大小
        Rect mRect = new Rect((int) planePositionX, (int) planePositionY, (int) planePositionX + planeWidth, (int) planePositionY + planeHeight);
        //画飞机
        canvas.drawBitmap(plane, null, mRect, null);
    }
}
