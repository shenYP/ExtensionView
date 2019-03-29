package com.example.user.myapplication.extension;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import at.wirecube.additiveanimations.additive_animator.AnimationEndListener;

/**
 * @author by syp on 2019/1/8.
 */
public class BaseExtensionLayout extends RelativeLayout {

    /** 动画持续时间 */
    protected int mDuration = 5000;
    /** 缩放动画持续时间 */
    protected int mScaleDuration = 5000;
    /** 描边画笔 */
    protected Paint mBorderPaint;
    /** 内容画笔 */
    protected Paint mPaint;
    /** 宽度 */
    protected float mWidth = 139;
    /** 高度 */
    protected float mHeight = 340;
    /** 最大宽度 */
    protected float mMaxWidth;
    /** 最小宽度 */
    protected float mMinWidth;
    /** 上下差异宽度 */
    protected float differenceWidth = 32;
    /** 边角大小 */
    protected float angleSize = 12;
    /** 裁切背景路径 */
    protected Path mClipPath;
    /** 裁切人物路径 */
    protected Path mClipPersonPath;
    /** 聚焦时候背景缩放倍数 */
    protected float focusBackgroundScale = 1.1F;
    /** 聚焦时候人物缩放倍数 */
    protected float focusPersonScale = 1.1F;
    /** 未聚焦时候缩放倍数 */
    protected float unFocusScale = 1.0F;
    /** 是否裁切子view（默认false不裁切） */
    protected boolean clipChildren;
    /** 描边画笔宽度 */
    protected int mBorderSize = 12;
    /** 焦点改变回调 */
    private IFocusChangedCallback mFocusCallback;
    /** 缩放动画 */
    private AdditiveAnimator scaleAnimator;
    /** 宽度动画 */
    private AdditiveAnimator widthAnimator;
    /** 是否正在执行动画 */
    protected boolean isAnimator;

    public BaseExtensionLayout(Context context) {
        this(context, null);
    }

    public BaseExtensionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseExtensionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SpecialShaped, 0, 0);
        mMaxWidth = array.getDimension(R.styleable.SpecialShaped_maxWidth, 0);
        mMinWidth = array.getDimension(R.styleable.SpecialShaped_minWidth, 0);
        differenceWidth = array.getDimension(R.styleable.SpecialShaped_differenceWidth, differenceWidth);
        mDuration = array.getInteger(R.styleable.SpecialShaped_duration, mDuration);
        focusBackgroundScale = array.getFloat(R.styleable.SpecialShaped_focusBackgroundScale, focusBackgroundScale);
        focusPersonScale = array.getFloat(R.styleable.SpecialShaped_focusPersonScale, focusPersonScale);
        clipChildren = array.getBoolean(R.styleable.SpecialShaped_clipChildren, clipChildren);
        angleSize = array.getDimension(R.styleable.SpecialShaped_angleSize, angleSize);
        array.recycle();
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);

        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.GREEN);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setDither(true);
        mBorderPaint.setStrokeWidth(mBorderSize);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setClipChildren(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (mFocusCallback != null) {
            mFocusCallback.onFocusChanged(this, gainFocus, direction);
        }
    }

    public void setOnFocusChanged(IFocusChangedCallback focusCallback) {
        this.mFocusCallback = focusCallback;
    }

    /**
     * 设置是否展开
     *
     * @param isExtension
     */
    public void setExtension(boolean isExtension) {
        ImageView person = (ImageView) getChildAt(1);
        if (isExtension) {
            // 展开
            bringToFront();
            startWidthAnimation(mMaxWidth, focusBackgroundScale, false);
            if (person != null) {
                scaleView(person, focusPersonScale, focusPersonScale);
            }
        } else {
            // 收缩
            startWidthAnimation(mMinWidth, unFocusScale, true);
            if (person != null) {
                scaleView(person, unFocusScale, unFocusScale);
            }
        }
    }

    /**
     * 缩放
     *
     * @param view   需要缩放的view
     * @param scaleX X轴倍数
     * @param scaleY Y轴倍数
     */
    public void scaleView(View view, float scaleX, float scaleY) {
        if (scaleAnimator != null) {
            scaleAnimator.cancelAllAnimations();
        }
        scaleAnimator = new AdditiveAnimator()
                .targets(view)
                .setDuration(mScaleDuration)
                .scaleX(scaleX)
                .scaleY(scaleY)
                .addStartAction(new Runnable() {
                    @Override
                    public void run() {
                        isAnimator = true;
                    }
                })
                .addEndAction(new AnimationEndListener() {
                    @Override
                    public void onAnimationEnd(boolean wasCancelled) {
                        isAnimator = false;
                    }
                });
        scaleAnimator.start();
    }

    /**
     * 开始启动动画
     *
     * @param endWidth       结束时候的宽度
     * @param scale          缩放比例
     * @param isClipChildren 动画结束后是否裁切子view
     */
    protected void startWidthAnimation(float endWidth, float scale, final boolean isClipChildren) {
        if (widthAnimator != null) {
            widthAnimator.cancelAllAnimations();
        }
        widthAnimator = new AdditiveAnimator()
                .targets(this)
                .setDuration(mDuration)
                .width((int) endWidth)
                .scale(scale);
        widthAnimator.start();
    }

    public interface IFocusChangedCallback {
        void onFocusChanged(View view, boolean gainFocus, int direction);
    }
}
