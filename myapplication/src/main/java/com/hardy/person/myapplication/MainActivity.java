package com.hardy.person.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    GameView mGameView;
    static Handler handler;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        mGameView = new GameView(this, width, height);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //设置成窗口全屏之后上方电池状态栏不见了,第一个参数是想要的标志位，第二个参数是要被修改的标志位，添加新的标志位所以两个参数一样
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        text = new TextView(MainActivity.this);
        text.setText("Game Over!");
        text.setTextSize(50);
        setContentView(mGameView);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mGameView.setVisibility(View.GONE);
                setContentView(text);
            }
        };


    }



    private class My_View extends View {
        /*//路径效果测试
        private Paint mPaint;
        private Path mPath;
        private PathEffect[] mEffects ;
        private float phase ;*/

        /*//drawTextOnPath测试
        private Path[] mPaths;
        private final String msg = "2016年5月8号";
        private Paint mPaintOne;
        private Paint mPaintTwo;*/

        public My_View(Context context) {
            super(context);

            /*//路径效果测试
            mEffects = new PathEffect[7] ;
            mPath = new Path();
            mPath.moveTo(0, 0);
            for (int i = 0; i < 15; i++) {
                mPath.lineTo(i*60, (float) (Math.random()*60));
            }
            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(4);
            mPaint.setTextSize(50);*/

            /*//drawTextOnPath测试
            mPaths = new Path[3];
            mPaths[0] = new Path();
            mPaths[0].moveTo(0, 0);
            for (int i = 0; i < 7; i++) {
                mPaths[0].lineTo(i * 60, (float) (Math.random() * 30));
            }
            RectF one = new RectF(0, 0, 200, 120);
            mPaths[1] = new Path();
            mPaths[1].addOval(one, Path.Direction.CW);
            mPaths[2] = new Path();
            mPaths[2].addArc(one, 60, 180);
            mPaintOne = new Paint() ;
            mPaintOne.setColor(Color.RED);
            mPaintOne.setStrokeWidth(4);
            mPaintOne.setTextAlign(Paint.Align.RIGHT);
            mPaintOne.setTextSize(20);
            //抗锯齿
            mPaintOne.setAntiAlias(true);
            mPaintOne.setStyle(Paint.Style.STROKE);
            mPaintTwo = new Paint() ;
            mPaintTwo.setColor(Color.BLUE);
            mPaintTwo.setStrokeWidth(4);
            mPaintTwo.setTextAlign(Paint.Align.RIGHT);
            mPaintTwo.setTextSize(20);
            //抗锯齿
            mPaintTwo.setAntiAlias(true);
            //不知为什么绘制Text时FILL会比STROKE窄???
            mPaintTwo.setStyle(Paint.Style.FILL);*/


        }


        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.WHITE);
            /*//路径效果测试
            mEffects[0] = null ;
            //拐角路径效果（变成有弧度的角），参数是弧度
            mEffects[1] = new CornerPathEffect(10) ;
            //离散路径效果，第一个参数是隔多长画一个偏离点，第二个参数是偏离出多远
            mEffects[2] = new DiscretePathEffect(3, 5) ;
            //扩折号路径效果，float数组必须是偶数个，奇数是画出的长度，偶数是空出的长度
            mEffects[3] = new DashPathEffect(new float[]{30, 20, 5, 20}, phase) ;
            //定义扩折号的形状
            Path p = new Path() ;
            //在这定义成正方形，最后一个参数是绘制的方向（counter-clockwise逆时针，clockwise是顺时针）
            p.addRect(0, 0, 8, 8, Path.Direction.CCW);
            //定义一个具有自定义形状的扩折号路径效果，第二个参数是间隔距离，第三个参数是相位，为了实现动画效果而存在，最后一个是动画的方式
            mEffects[4] = new PathDashPathEffect(p, 12, phase, PathDashPathEffect.Style.ROTATE) ;
            //第一个参数的效果在外面包裹着第二个参数的效果
            mEffects[5] = new ComposePathEffect(mEffects[2], mEffects[4]) ;
            //两个效果叠加后显示
            mEffects[6] = new SumPathEffect(mEffects[2], mEffects[4]) ;
            for(int i=0;i<mEffects.length;i++){
                mPaint.setPathEffect(mEffects[i]);
                canvas.drawPath(mPath, mPaint);
                canvas.translate(0, 60);
            }
            phase +=1 ;
            invalidate();*/


            /*//drawTextOnPath测试
            canvas.drawColor(Color.WHITE);
            canvas.drawPath(mPaths[0], mPaintOne);
            canvas.translate(0, 30);
            canvas.drawTextOnPath(msg, mPaths[0],-10,20 , mPaintTwo);
            canvas.translate(0, 30);
            canvas.drawPath(mPaths[1], mPaintOne);
            canvas.translate(0, 120);
            canvas.drawTextOnPath(msg, mPaths[1],-10,20,mPaintTwo);
            canvas.translate(0, 120);
            canvas.drawPath(mPaths[2],mPaintOne);
            canvas.translate(0, 120);
            canvas.drawTextOnPath(msg,mPaths[2],-10,20,mPaintTwo);*/


        }
    }
}
