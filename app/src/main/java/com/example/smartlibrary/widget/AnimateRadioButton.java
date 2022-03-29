package com.example.smartlibrary.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;

import com.example.smartlibrary.R;
import com.example.smartlibrary.utils.LogUtils;




/*
 * -----------------------------------------------------------------
 * Copyright (C) 2011-2018, by your signway, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: AnimateRadioButton.java
 *
 * Author: Administrator
 *
 * Create: 2018\8\3 0003 9:17
 *
 * Changes (from 2018\8\3 0003)
 * -----------------------------------------------------------------
 * 2018\8\3 0003 : Create AnimateRadioButton.java (Administrator);
 * -----------------------------------------------------------------
 */

@SuppressLint("AppCompatCustomView")
public class AnimateRadioButton extends RadioButton {
    private static final String TAG = AnimateRadioButton.class.getSimpleName();

    private static final int DEFAULT_BACKGROUND_START_COLOR = 0xff0a4ad8;
    private static final int DEFAULT_BACKGROUND_END_COLOR = 0xff0b9df2;
    private static final int DEFAULT_HIGHLIGHT_START_COLOR = 0xffFA7C20;
    private static final int DEFAULT_HIGHLIGHT_END_COLOR = 0xffFA8C20;

    /**
     * 视图的宽高
     */
    private int mWidth, mHeight;

    /**
     * 焦点框的圆角半径
     */
    private int focusBorderRadius;

    /**
     * 焦点框的线宽
     */
    private int borderLine; // 焦点框

    /**
     * 焦点框的位置
     */
    private int mFocusBorderX, mFocusBorderY, mFocusBorderWidth, mFocusBorderHeight;

    /**
     * 是否需要动画
     */
    private boolean mAnimation;

    private int mIconX, mIconY, mIconWidth, mIconHeight;

    private Animation scaleSmallAnimation;
    private Animation scaleBigAnimation;

    private RectF mFocusBorderRectF = new RectF();
    private RectF mFillBGRectF = new RectF();

    private Paint mFocusBorderPaint;
    private Paint mBackgroundPaint;
    private Paint mHighlightPaint;
    private Paint mBmpPaint;

    private boolean mBlurSolid; // 外发光
    private Bitmap mIconBmp; // 外发光

    private Bitmap bgBitmap; // 背景bitmap
    private Drawable background;
    private int bgWidth, bgHeight;

    /**
     * 是否显示设置的背景图片
     */
    private boolean mOriginBackgroundOn;

    private int[] highlightColors = new int[2];
    private int[] normalColors = new int[2];

    private Path borderPath = new Path();
    private Path fillBGPath = new Path();

    private float[] radiusArray = new float[8];

    private int backgroundStartColor;
    private int backgroundEndColor;
    private int highlightStartColor;
    private int highlightEndColor;

    public AnimateRadioButton(Context context) {
        super(context);
        init(context, null);
    }

    public AnimateRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AnimateRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimateRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnimateRadioButton);

            mBlurSolid = typedArray.getBoolean(R.styleable.AnimateRadioButton_blur_solid, false);
            borderLine = typedArray.getInt(R.styleable.AnimateRadioButton_focusBorder_width, 4);
            focusBorderRadius = typedArray.getInt(R.styleable.AnimateRadioButton_focusBorder_radius, 20);

            backgroundStartColor = typedArray.getColor(R.styleable.AnimateRadioButton_background_start,
                    DEFAULT_BACKGROUND_START_COLOR);
            backgroundEndColor = typedArray.getColor(R.styleable.AnimateRadioButton_background_end,
                    DEFAULT_BACKGROUND_END_COLOR);
            highlightStartColor = typedArray.getColor(R.styleable.AnimateRadioButton_highlight_start,
                    DEFAULT_HIGHLIGHT_START_COLOR);
            highlightEndColor = typedArray.getColor(R.styleable.AnimateRadioButton_highlight_end,
                    DEFAULT_HIGHLIGHT_END_COLOR);

            mOriginBackgroundOn = typedArray.getBoolean(R.styleable.AnimateRadioButton_originBackgroundOn, false);

            mIconBmp = drawableToBitmap(typedArray.getDrawable(R.styleable.AnimateRadioButton_drawableIcon));

            mAnimation = typedArray.getBoolean(R.styleable.AnimateRadioButton_radiobutton_animation, true);

            typedArray.recycle();
        }

        background = getBackground();

        //background包括color和Drawable,这里分开取值
        if (background != null) {
            if (background instanceof ColorDrawable) {
                ColorDrawable colordDrawable = (ColorDrawable) background;
                int color = colordDrawable.getColor();
                LogUtils.logd("color is " + Integer.toHexString(color));
            } else if (background instanceof BitmapDrawable) {
                bgBitmap = ((BitmapDrawable) background).getBitmap();

                if (bgBitmap != null) {
                    bgWidth = bgBitmap.getWidth();
                    bgHeight = bgBitmap.getHeight();
                } else {
                    LogUtils.logd("there‘s no background set");
                }
            } else if (background instanceof StateListDrawable) {
                Log.w(TAG, "not support state list drwable");
            }
        } else {
//            LogUtil.d("there‘s no background set");
        }

        // init colors
        normalColors[0] = backgroundStartColor;
        normalColors[1] = backgroundEndColor;
        highlightColors[0] = highlightStartColor;
        highlightColors[1] = highlightEndColor;

        // init paint
        mFocusBorderPaint = new Paint();
        mFocusBorderPaint.setColor(Color.WHITE);
        mFocusBorderPaint.setStyle(Paint.Style.STROKE);
        mFocusBorderPaint.setStrokeWidth(borderLine);
        mFocusBorderPaint.setAntiAlias(true);

        mHighlightPaint = new Paint();
        BlurMaskFilter maskFilter = new BlurMaskFilter(borderLine, BlurMaskFilter.Blur.SOLID);
        mHighlightPaint.setMaskFilter(maskFilter);
        mHighlightPaint.setStyle(Paint.Style.FILL);
        mHighlightPaint.setAntiAlias(true);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setAntiAlias(true);

        mBmpPaint = new Paint();
        mBmpPaint.setStyle(Paint.Style.FILL);
        mBmpPaint.setAntiAlias(true);

        initRadiusArray();

        if (mIconBmp != null) {
            mIconWidth = mIconBmp.getWidth();
            mIconHeight = mIconBmp.getHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // measure mWidth/mHeight
        mWidth = measureWidth(widthMode, widthSize);
        mHeight = measureHeight(heightMode, heightSize);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mHeight);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
//        LogUtil.d("onDraw() " + mWidth + ", " + mHeight);
        if (mBlurSolid) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        initPosition();

        if (mWidth <= 0 && mHeight <= 0)
            return;

        if (!mOriginBackgroundOn) {
            mFillBGRectF.set(0, 0, mWidth, mHeight);
            fillBGPath.addRoundRect(mFillBGRectF, radiusArray, Path.Direction.CW);

            // 绘制定义的高亮、正常状态
            if (isSelected() || isChecked() || isPressed()) {
                LinearGradient highlightGradient = new LinearGradient(0, mHeight, mWidth, 0, highlightColors, null,
                        Shader.TileMode.CLAMP);
                mHighlightPaint.setShader(highlightGradient);

                canvas.drawPath(fillBGPath, mHighlightPaint);
            } else {
                LinearGradient backGradient = new LinearGradient(0, mHeight, mWidth, 0, normalColors, null, Shader
                        .TileMode.CLAMP);

                mBackgroundPaint.setShader(backGradient);

                canvas.drawPath(fillBGPath, mBackgroundPaint);
            }

            if (mIconBmp != null) {
                mIconX = (mWidth - mIconWidth) / 2;
                mIconY = (mHeight - mIconHeight) / 2;

                canvas.drawBitmap(mIconBmp, mIconX, mIconY, mBmpPaint);
            }
        }

        // draw background
        if (background != null) {
            background.draw(canvas);
        }

        // 焦点框
        if (hasFocus()) {
            mFocusBorderRectF.set(mFocusBorderX, mFocusBorderY, mWidth - borderLine / 2, mHeight - borderLine / 2);

            borderPath.addRoundRect(mFocusBorderRectF, radiusArray, Path.Direction.CW);

            canvas.drawPath(borderPath, mFocusBorderPaint);
        }

        super.onDraw(canvas);
    }

    /**
     * 测量宽度
     *
     * @param mode
     * @param width
     * @return
     */
    private int measureWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.AT_MOST:
                mWidth = getPaddingLeft() + getPaddingRight() + Math.max(bgWidth, mIconHeight);
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY:
                mWidth = width;
                break;
        }

        return mWidth;
    }

    /**
     * 测量高度
     *
     * @param mode
     * @param height
     * @return
     */
    private int measureHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.AT_MOST:
                mHeight = getPaddingBottom() + getPaddingTop() + Math.max(bgHeight, mIconHeight);
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY:
                mHeight = height;
                break;
        }

        return mHeight;
    }

    private void initPosition() {
        mFocusBorderX = borderLine / 2;
        mFocusBorderY = borderLine / 2;
        mFocusBorderWidth = mWidth - borderLine;
        mFocusBorderHeight = mHeight - borderLine;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        LogUtils.logd("onFocusChanged focused " + focused + ", ");

        animateZoom(focused);
    }

    @Override
    public void setSelected(boolean selected) {
        LogUtils.logd("setSelected " + selected);

        super.setSelected(selected);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
//        if (isInTouchMode()) {
        animateZoom(checked);
//        }
        invalidate();
    }

    /**
     * 动画切换效果
     *
     * @param isOut
     */
    private void animateZoom(boolean isOut) {
        if (mAnimation) {
            if (isOut) {
                zoomOut();
            } else {
                zoomIn();
            }
        }
    }

    private void initRadiusArray() {
        for (int i = 0; i < radiusArray.length; i++) {
            radiusArray[i] = focusBorderRadius;
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null)
            return null;

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable
                    .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(bitmap);

            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            drawable.draw(canvas);

            return bitmap;
        }
    }

    /**
     * @param drawable
     * @param scale
     * @return
     */
    private Drawable scaleDrawable(Drawable drawable, float scale) {
        if (drawable == null) return null;

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = (scale * width);
        float scaleHeight = (scale * height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(null, newbmp);
    }


    /**
     * 缩小动画
     */
    private void zoomIn() {
        if (scaleSmallAnimation == null) {
            scaleSmallAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_small);
        }

        startAnimation(scaleSmallAnimation);
    }

    /**
     * 放倒动画
     */
    private void zoomOut() {
        if (scaleBigAnimation == null) {
            scaleBigAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_big);
        }

        startAnimation(scaleBigAnimation);
    }
}