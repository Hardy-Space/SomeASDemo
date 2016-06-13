package com.hardy.person.animation3d;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class My_Animation extends Animation {
    private float centerX ;
    private float centerY ;
    private int duration ;
    private Camera mCamera = new Camera();

    public My_Animation(float x, float y ,int duration){
        this.centerX = x;
        this.centerY = y;
        this.duration = duration;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(duration);
        setFillAfter(true);
        setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        mCamera.save();
        mCamera.rotateX(45);
        Matrix matrix = t.getMatrix();
        mCamera.getMatrix(matrix);
        matrix.preTranslate(100,0);
        /*Matrix matrix = t.getMatrix() ;
        mCamera.getMatrix(matrix);
        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY) ;*/
        mCamera.restore();
    }
}
