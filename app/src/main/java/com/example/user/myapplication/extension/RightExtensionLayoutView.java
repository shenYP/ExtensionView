package com.example.user.myapplication.extension;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author by syp on 2019/1/8.
 */
public class RightExtensionLayoutView extends BaseExtensionLayout {

    public RightExtensionLayoutView(Context context) {
        this(context, null);
    }

    public RightExtensionLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightExtensionLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        mClipPath = new Path();
        mClipPath.moveTo(differenceWidth, 0);
        mClipPath.lineTo(mWidth, 0);
        mClipPath.lineTo(mWidth, mHeight - angleSize * 2);
        mClipPath.lineTo(mWidth - angleSize, mHeight - angleSize * 2);
        mClipPath.lineTo(mWidth - angleSize, mHeight - angleSize);
        mClipPath.lineTo(mWidth - angleSize * 2, mHeight - angleSize);
        mClipPath.lineTo(mWidth - angleSize * 2, mHeight);
        mClipPath.lineTo(0, mHeight);
        mClipPath.close();

        View background = getChildAt(0);
        if (background == child) {
            canvas.save();
            canvas.clipPath(mClipPath);
        }
        View person = getChildAt(1);
        if (person == child) {
            canvas.restore();
            if (isFocused()) {
                clipPersonPath(canvas);
            } else {
                if (isAnimator) {
                    clipPersonPath(canvas);
                } else {
                    canvas.clipPath(mClipPath);
                }
            }
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    /**
     * 裁切人物路径
     */
    private void clipPersonPath(Canvas canvas) {
        mClipPersonPath = new Path();
        mClipPersonPath.moveTo(differenceWidth, mHeight - mHeight * focusPersonScale);
        mClipPersonPath.lineTo(mWidth, mHeight - mHeight * focusPersonScale);
        mClipPersonPath.lineTo(mWidth, mHeight * focusPersonScale);
        mClipPersonPath.lineTo(0, mHeight * focusPersonScale);
        mClipPersonPath.close();
        canvas.clipPath(mClipPersonPath);
    }
}
