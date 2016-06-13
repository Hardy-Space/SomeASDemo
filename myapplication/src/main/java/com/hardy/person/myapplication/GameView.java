package com.hardy.person.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
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
public class GameView extends View {

    //画挡板的画笔
    Paint mBoardPaint ;
    //画小球的画笔
    Paint mBallPaint ;
    //小球圆点
    float cx = 15;
    float cy = 15;
    //小球半径
    final float radius = 35;
    //小球运动水平速度
    final float xSpeed = 50;
    //小球运动垂直速度
    final float ySpeed = 50 ;
    //小球水平方向是屏幕往右
    boolean toRight = true ;
    //小球垂直方向是屏幕往下
    boolean toBottom = true ;
    //挡板宽和高
    final float boardWidth = 300;
    final float boardHeight = 30 ;
    //挡板起点位置
    float bx ;
    float by ;
    //传进来的Activity
    MainActivity mActivity ;
    //屏幕宽高
    float screenWidth ;
    float screenHeight ;


    public GameView(Context context, final int width, final int height) {
        super(context);
        mActivity = (MainActivity) context;
        screenWidth = width ;
        screenHeight = height ;
        //设置焦点
        setFocusable(true);
        //初始化挡板画笔
        mBoardPaint = new Paint() ;
        mBoardPaint.setColor(Color.BLACK);
        mBoardPaint.setStyle(Paint.Style.FILL);
        //初始化小球画笔
        mBallPaint = new Paint() ;
        mBallPaint.setColor(Color.GREEN);
        mBallPaint.setStyle(Paint.Style.FILL);
        //默认在窗口下面并且居中显示
        bx = width/2 - boardWidth/2 ;
        by = height - 50 ;
       // Log.d("宽高", "" + bx + "###"+by);
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                if ((cx + radius >= bx + boardWidth || cx + radius <= bx) && cy + radius >= by) {
                    Log.d("########", "" + cy + radius);
                    mActivity.handler.sendEmptyMessage(1);
                    time.cancel();

                }
                if (cx + radius >= bx && cy + radius >= by) {
                    toBottom = false;
                }
                if (cx + radius >= width - radius) {
                    cx = width - radius - xSpeed;
                    toRight = false;
                }
                if (cx <= radius) {
                    cx = radius + xSpeed;
                    toRight = true;
                }
                if (toRight) {
                    cx += xSpeed;
                }
                if (!toRight) {
                    cx -= xSpeed;
                }
                if (cy <= radius) {
                    cy = radius + ySpeed;
                    toBottom = true;
                }
                if (toBottom) {
                    cy += ySpeed;
                }
                if (!toBottom) {
                    cy -= ySpeed;
                }
                postInvalidate();
            }
        }, 0, 80);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getX()-150<0){
            bx = 0 ;
        }if (event.getX()+150>screenWidth){
            bx = screenWidth-300 ;
        } else{
            bx = event.getX() - 150;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //画小球
        canvas.drawCircle(cx, cy, radius, mBallPaint);
        //画挡板
        canvas.drawRect(bx, by, bx + boardWidth, by + boardHeight, mBoardPaint);
    }
}
