package com.hardy.person.animation3d;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class MyBallView extends View implements ValueAnimator.AnimatorUpdateListener{

    private int BALL_WIDTH = 110 ;
    private float FULL_TIME = 1000 ;
    private List<ShapeHolder> allBalls = new ArrayList<ShapeHolder>();

    public MyBallView(Context context){
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //为了防止同一个点的动画重复播放
        if (event.getAction()!=MotionEvent.ACTION_DOWN&&event.getAction()!=MotionEvent.ACTION_MOVE){
            return false ;
        }
        //点击屏幕的点的Y坐标
        float startY = event.getY() ;
        //圆心终止的Y坐标
        float endY = getHeight()-BALL_WIDTH;
        //此View的高度
        float h = getHeight();
        //实例化ShapeHolder
        ShapeHolder newBall = addBall(event.getX(), event.getY());
        //定义小球垂直位置改变的属性动画
        ValueAnimator downPosition = ObjectAnimator.ofFloat(newBall,"centerY",startY,endY);
        //设置动画时间
        downPosition.setDuration((int) (FULL_TIME * (h - startY) / h));
        //设置插值类型（变化速度器）
        downPosition.setInterpolator(new AccelerateInterpolator());
        //添加属性值改变的监听器
        downPosition.addUpdateListener(this);
        //定义并实例化下落时透明度增强的效果动画
        ObjectAnimator downAlpha = ObjectAnimator.ofFloat(newBall,"alpha",0f,1f);
        //设置动画时间
        downAlpha.setDuration((int) (FULL_TIME * (h - startY) / h));
        //定义并实例化动画集合实例
        AnimatorSet fallSet = new AnimatorSet();
        //设置动画播放顺序
        fallSet.play(downPosition).with(downAlpha);
        //定义小球压扁效果的动画
        ValueAnimator smallY = ObjectAnimator.ofFloat(newBall,"centerY",endY,endY+BALL_WIDTH/2);
        //设置动画时间
        smallY.setDuration(150);
        //设置插值类型（变化速度器）
        smallY.setInterpolator(new DecelerateInterpolator());
        //设置动画重复次数
        smallY.setRepeatCount(1);
        //设置重复类型，是正序还是倒序
        smallY.setRepeatMode(ValueAnimator.REVERSE);
        //添加属性值改变的监听器
        smallY.addUpdateListener(this);
        //小球高度改变的动画
        ValueAnimator toSmall = ObjectAnimator.ofFloat(newBall,"height",BALL_WIDTH,BALL_WIDTH/2);
        toSmall.setDuration(150);
        toSmall.setInterpolator(new DecelerateInterpolator());
        toSmall.setRepeatCount(1);
        toSmall.setRepeatMode(ValueAnimator.REVERSE);
        //定义并实例化弹起后透明度减弱的动画
        ObjectAnimator upAlpha = ObjectAnimator.ofFloat(newBall,"alpha",1f,0f);
        //设置动画时间
        upAlpha.setDuration((int) (FULL_TIME * (h - startY) / h));
        //透明度为0后让小球消失
        upAlpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                allBalls.remove(((ObjectAnimator) animation).getTarget());
            }
        });
        upAlpha.addUpdateListener(this);
        //定义并实例化弹起后的位置改变动画
        ValueAnimator upPosition = ObjectAnimator.ofFloat(newBall,"centerY",endY,startY);
        //设置动画时间
        upPosition.setDuration((int) (FULL_TIME * (h - startY) / h));
        //设置插值类型（变化速度器）
        upPosition.setInterpolator(new DecelerateInterpolator());
        //添加属性值改变的监听器
        upPosition.addUpdateListener(this);
        //定义并实例化小球压扁和弹起后的一系列动画的集合
        AnimatorSet upSet = new AnimatorSet();
        //设置动画播放顺序为压扁、弹起
        upSet.play(upAlpha).with(upPosition);
        upSet.play(toSmall).with(smallY);
        upSet.play(toSmall).before(upPosition);
        //定义并实例化总动画的集合
        AnimatorSet sumSet =new AnimatorSet();
        //设置全部动画的播放顺序
        sumSet.play(fallSet).before(upSet);
        //启动动画
        sumSet.start();
        return true;
    }
    //返回ShapeHolder实例，参数为点击屏幕的点的x、y坐标
    public ShapeHolder addBall(float x,float y){
        //定义一个椭圆形状
        OvalShape shape = new OvalShape();
        //设置椭圆成圆，指定半径
        shape.resize(BALL_WIDTH,BALL_WIDTH);
        //实例化ShapeDrawable对象，形状为圆
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        //实例化ShapeHolder实例
        ShapeHolder shapeHolder = new ShapeHolder(shapeDrawable);
        //设置小球的圆心为点击屏幕的位置
        shapeHolder.setCenterX(x);
        shapeHolder.setCenterY(y);
        //添加进ShapeHolder集合，一会统一画出来
        allBalls.add(shapeHolder);
        //返回ShapeHolder实例
        return shapeHolder ;
    }

    @Override
    protected void onDraw(Canvas canvas) {


        //绘制集合里的所有小球
        for (ShapeHolder single:allBalls){
            //保存画布原始数据
            canvas.save();
            //将画布移动到点击屏幕的位置（即小球的虚拟矩形左上角起点处）
            canvas.translate(single.getCenterX(), single.getCenterY());
            //画小球
            single.getShapeDrawable().draw(canvas);
            //恢复到原来的画布设置
            canvas.restore();
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        //属性值改变，回调此方法来重绘组件
        invalidate();
    }
}
