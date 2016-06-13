package com.hardy.person.gesture_test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


/*
添加手势
*/
public class MainActivity extends AppCompatActivity {

    GestureOverlayView mGestureOverlayView;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.searchGesture);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mGestureOverlayView = (GestureOverlayView) findViewById(R.id.overlayview);
        mGestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, final Gesture gesture) {
/*
                手势绘制完成保存
*/
                View saveView = getLayoutInflater().inflate(R.layout.gesture_save_layotu, null);
                final ImageView gestureImage = (ImageView) saveView.findViewById(R.id.gestureImage);
                Bitmap bitmap = gesture.toBitmap(480, 480, 0, Color.BLACK);
                gestureImage.setImageBitmap(bitmap);
                final EditText gestureName = (EditText) saveView.findViewById(R.id.gestureName);
                new AlertDialog.Builder(MainActivity.this)
                        .setView(saveView)
                        .setTitle("保存手势到手势库")
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = gestureName.getText().toString();
                                if (TextUtils.isEmpty(name)) {
                                    Toast.makeText(MainActivity.this, "手势名字不能为空！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                GestureLibrary gestureLibrary = GestureLibraries.fromFile("/mnt/sdcard/mygestures");
                                gestureLibrary.addGesture(name, gesture);
                                gestureLibrary.save();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

    }
}


/*
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
*/

/*
测试OnGestureListener
*/
/*public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {


    Animation[] mAnimations = new Animation[2];
    int[] images;
    GestureDetector mGestureDetector;
    ViewFlipper mViewFlipper;
    final float FLIP_DISTANCE = 50 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureDetector = new GestureDetector(this, this);
        mAnimations[0] = AnimationUtils.loadAnimation(this, R.anim.in);
        mAnimations[1] = AnimationUtils.loadAnimation(this, R.anim.out);
        images = new int[]{R.drawable.huosai, R.drawable.maci, R.drawable.moshu, R.drawable.pskkb, R.drawable.qicai, R.drawable.xiaoniu, R.drawable.yongshi};
        mViewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        for (int i = 0; i < images.length; i++) {
            mViewFlipper.addView(addImageView(images[i]));
        }

    }

    private View addImageView(int src) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(src);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //        Toast.makeText(MainActivity.this, "onDown", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //        Toast.makeText(MainActivity.this, "onSHowPress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //        Toast.makeText(MainActivity.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //        Toast.makeText(MainActivity.this, "onScroll", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //        Toast.makeText(MainActivity.this, "onLongPress", Toast.LENGTH_SHORT).show();
    }


*//*
    第一个参数是刚落下时触发的事件，像onDown，第二个参数是最后触发onFling的点（松开时带有加速度），后面的是松开时的速度
*//*
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //        Toast.makeText(MainActivity.this, "onFling", Toast.LENGTH_SHORT).show();

        if (e1.getX()-e2.getX()>FLIP_DISTANCE){
            mViewFlipper.setInAnimation(mAnimations[0]);
            mViewFlipper.setOutAnimation(mAnimations[1]);
            mViewFlipper.showNext();
        }else if (e2.getX()-e1.getX()>FLIP_DISTANCE){
            mViewFlipper.setInAnimation(mAnimations[0]);
            mViewFlipper.setOutAnimation(mAnimations[1]);
            mViewFlipper.showPrevious();
        }

        return true;
    }
}*/

/*
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
*/
/*
    实现双指缩放和双击缩放
 */
/*public class MainActivity extends AppCompatActivity {

    ImageView mImageView;
    //记录上次触发事件时的时间
    long lastTime = 0;
    //记录上次的点
    float currentX = -1;
    float currentY = -1;
    //记录之前总共的偏移值，因为当不是第一次拖动时它会先跳回初始位置然后再执行偏移操作
    float totalTransitionX;
    float totalTransitionY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image);
        final float viewInitialScaleX = mImageView.getScaleX();
        final float viewInitialScaleY = mImageView.getScaleY();
        class MyTouchListener implements View.OnTouchListener {

            //记录是几指触碰
            int points = 0;
            //上一次两点触碰之间的距离
            float oldDistance;
            //代表触发触摸事件的组件
            View mView;
            //记录组件原始的缩放尺寸
            float viewScaleX;
            float viewScaleY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获得事件源
                mView = v;
                //获得原始尺寸
                viewScaleX = mView.getScaleX();
                viewScaleY = mView.getScaleY();

                //
                *//*switch（）里的语句必须这么写，如果只写event.getAction()的话不会触发MotionEvent.ACTION_POINTER_DOWN和
                MotionEvent.ACTION_POINTER_UP事件，因为设计原理就是他俩的比较结果比MotionEvent.ACTION_MASK小的时候才会
                去触发这俩事件*//*
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    //当只有一个触摸点按下时触发
                    case MotionEvent.ACTION_DOWN:
                        //记录当前触摸点只有一个
                        points = 1;
                        break;
                    //当唯一的一个触摸点也没有的时候触发
                    case MotionEvent.ACTION_UP:
                        //记录当前触摸点一个也没有了
                        points = 0;
                        //处理双击缩放业务
                        if (System.currentTimeMillis() - lastTime < 300 && System.currentTimeMillis() - lastTime > 0) {
                            if (mView.getScaleX() == viewInitialScaleX && mView.getScaleY() == viewInitialScaleY)
                                scaleImage(2f, 2f);
                            else
                                scaleImage(viewInitialScaleX, viewInitialScaleY);
                        }
                        currentX = -1;
                        currentY = -1;
                        lastTime = System.currentTimeMillis();
                        break;
                    //当有两个及以上的触摸点时触发
                    case MotionEvent.ACTION_POINTER_DOWN:
                        //记录触摸点加一个
                        points += 1;
                        oldDistance = spacing(event);
                        break;
                    //当多个触摸点中任何一个点松开时触发（不是最后一个点）
                    case MotionEvent.ACTION_POINTER_UP:
                        //记录触摸点减一个
                        points -= 1;
                        break;
                    //触摸滑动时触发
                    case MotionEvent.ACTION_MOVE:
                        //滑动时判断当前触摸点有几个
                        if (points == 1) {//放大状态下拖动图片业务，！！！！不完美！！！！
                            Log.d("MOVE", "" + totalTransitionX);
                            //如果是放大状态下
                            if (mView.getScaleX() > viewInitialScaleX && mView.getScaleY() > viewInitialScaleY) {
                                //实际距离等于之前所做的偏移的和，因为当不是第一次拖动时它会先跳回初始位置然后再执行偏移操作
                                float distanceX = totalTransitionX + event.getX() - currentX;
                                float distanceY = totalTransitionY + event.getY() - currentY;
                                //如果是刚接触屏幕，不滑动
                                if (currentX == -1) {
                                    distanceX = 0;
                                }
                                if (currentY == -1) {
                                    distanceY = 0;
                                }
                                mView.setTranslationX(distanceX);
                                mView.setTranslationY(distanceY);
                                currentX = event.getX();
                                currentY = event.getY();
                                totalTransitionX = distanceX;
                                totalTransitionY = distanceY;
                            }
                        }
                        if (points >= 2) {//如果大于两个
                            //记录最新的前两点的距离
                            float newDistance = spacing(event);
                            //如果当前两点间距离大于上一次的，则执行放大操作，至于+1是为了防止触摸抖动
                            if (newDistance > oldDistance + 1) {
                                //实际缩放比例，必须要结合原始的scale（乘以原始的scale），否则会出现缩放抖动厉害
                                float scaleX = newDistance / oldDistance * viewScaleX;
                                float scaleY = newDistance / oldDistance * viewScaleY;
                                //缩放
                                scaleImage(scaleX, scaleY);
                                //更新上一次的距离变为现在的距离
                                oldDistance = newDistance;
                            }
                            //如果当前两点间距离小于上一次的，则执行缩小操作，至于-1是为了防止触摸抖动
                            if (newDistance < oldDistance - 1) {
                                //实际缩放比例，必须要结合原始的scale（乘以原始的scale），否则会出现缩放抖动厉害
                                float scaleX = newDistance / oldDistance * viewScaleX;
                                float scaleY = newDistance / oldDistance * viewScaleY;
                                //缩放
                                scaleImage(scaleX, scaleY);
                                //更新上一次的距离变为现在的距离
                                oldDistance = newDistance;
                            }
                        }
                        break;
                }
                return true;
            }

            //计算两点间的距离
            float spacing(MotionEvent event) {
                //计算两点间的x坐标上的距离
                float x = event.getX(0) - event.getX(1);
                //计算两点间的y坐标上的距离
                float y = event.getY(0) - event.getY(1);
                //计算实际距离
                return (float) Math.sqrt(x * x + y * y);
            }

            //缩放操作
            void scaleImage(float scaleX, float scaleY) {

                //设置缩放的最大或最小尺寸
                if (scaleX > 2.5 || scaleX < 0.5 || scaleY > 2.5 || scaleY < 0.5) {
                    return;
                }
                //设置scale
                mView.setScaleX(scaleX);
                mView.setScaleY(scaleY);
            }

        }
        MyTouchListener myTouchListener = new MyTouchListener();
        mImageView.setOnTouchListener(myTouchListener);
    }
}*/
