package com.hardy.person.animation3d;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class ShapeHolder {
    private float centerX ;
    private float centerY ;
    private float height ;
    private float alpha ;

    private ShapeDrawable mShapeDrawable ;

    public float getHeight() {
        return mShapeDrawable.getShape().getHeight();
    }

    public void setHeight(float height) {
        Shape shape = mShapeDrawable.getShape();
        shape.resize(shape.getWidth(),height);
    }


    public int getAlpha() {
        return mShapeDrawable.getAlpha();
    }

    public void setAlpha(int alpha) {
        mShapeDrawable.setAlpha(alpha);
    }

    public ShapeHolder(ShapeDrawable shapeDrawable) {
        mShapeDrawable = shapeDrawable;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public ShapeDrawable getShapeDrawable() {
        return mShapeDrawable;
    }

    public void setShapeDrawable(ShapeDrawable shapeDrawable) {
        mShapeDrawable = shapeDrawable;
    }
}
