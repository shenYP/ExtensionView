package com.example.user.myapplication.extension;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by USER on 2019/1/23.
 */

public class ExtensionItemView extends android.support.v7.widget.AppCompatImageView {

    private Path mPath;
    private Paint mPaint;
    private Bitmap bitmap;
    private PorterDuffXfermode xfermode;
    /** 宽度 */
    protected float mWidth = 139;
    /** 高度 */
    protected float mHeight = 340;

    public ExtensionItemView(Context context) {
        this(context, null);
    }

    public ExtensionItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtensionItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPath = new Path();

        mPath.moveTo(0,0);
        mPath.lineTo(150,0);
        mPath.lineTo(150,150);
        mPath.lineTo(0,150);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }

    public void setImage(int res) {
        bitmap = BitmapFactory.decodeResource(getResources(), res);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.e("syp","是否为空 " + bitmap);
        if (bitmap != null) {
            int sc = canvas.saveLayer(0, 0, mWidth, mHeight, mPaint, Canvas.ALL_SAVE_FLAG);
            RectF rectF = new RectF(0,0,mWidth,mHeight);
            canvas.drawBitmap(bitmap, null, rectF, mPaint);
            mPaint.setXfermode(xfermode);
            mPaint.setColor(Color.RED);
            canvas.drawPath(mPath, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(sc);
        }
    }

    public void xfermodePath(Path path) {
        path.reset();
        path.moveTo(0,0);
        path.lineTo(150,0);
        path.lineTo(150,150);
        path.lineTo(0,150);
        path.close();
        this.mPath = path;
        invalidate();
    }
}
