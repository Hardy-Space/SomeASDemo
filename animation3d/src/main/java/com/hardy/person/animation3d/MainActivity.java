package com.hardy.person.animation3d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //private ImageView image ;
    private LinearLayout linear;

    //定义坐标轴相关值
    public int WIDTH = 320;
    public int HEIGHT = 320;
    private int OFF_SET = 20;
    SurfaceHolder holder;
    SurfaceView surfaceView;
    Button sin;
    Button cos;
    int cx = OFF_SET;
    int centerY = HEIGHT/2;
    Timer timer = new Timer();
    TimerTask timerTask = null ;
    Paint mPaint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //属性动画
        /*linear = (LinearLayout) findViewById(R.id.linear);
        linear.addView(new MyBallView(this));*/
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        sin = (Button) findViewById(R.id.sin);
        cos = (Button) findViewById(R.id.cos);
        surfaceView = (SurfaceView) findViewById(R.id.surface);
        holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                drawBackground();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                drawBackground();
                cx = OFF_SET ;
                if (timerTask != null){
                    timerTask.cancel();
                }

                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        int cy = (int) (v.getId() == R.id.sin ? centerY-100 * Math.sin(2 * (cx - OFF_SET) * Math.PI/150) : centerY-100 * Math.cos(2 * (cx - OFF_SET) * Math.PI/150));
                        Canvas canvas = holder.lockCanvas(new Rect(cx , cy - 2, cx + 2, cy + 2));
                        canvas.drawPoint(cx,cy,mPaint);
                        holder.unlockCanvasAndPost(canvas);
                        cx++;
                        if (cx>WIDTH){
                            timerTask.cancel();
                            timerTask = null ;
                        }
                    }
                };

                timer.schedule(timerTask,0,30);


            }
        };
        sin.setOnClickListener(onClickListener);
        cos.setOnClickListener(onClickListener);


    }

    public void drawBackground() {

        Canvas canvas = holder.lockCanvas();
        //绘制白色背景
        canvas.drawColor(Color.WHITE);
        //绘制黑色坐标轴
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawLine(OFF_SET, HEIGHT / 2, WIDTH, HEIGHT / 2, paint);
        canvas.drawLine(OFF_SET, 40, OFF_SET, HEIGHT + 40, paint);
        holder.unlockCanvasAndPost(canvas);
        holder.lockCanvas(new Rect(0, 0, 0, 0));
        holder.unlockCanvasAndPost(canvas);

    }


}
