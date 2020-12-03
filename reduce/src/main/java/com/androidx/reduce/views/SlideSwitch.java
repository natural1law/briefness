package com.androidx.reduce.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.androidx.reduce.R;

public final class SlideSwitch extends View {

    private Scroller mScroller;
    private boolean mIsOpen;
    private int mOpenBackground = R.drawable.widget_icon_slidebutton_yellow_bg;
    private int mCloseBackground = R.drawable.widget_icon_slidebutton_write_bg;
    private int mSlideImgId = R.drawable.widget_icon_slidebutton_write_slider;
    private int mSlideLeft;
    private int mMaxLeft;
    private int mStartX;
    private int mMoveX;

    private Bitmap mOpenBitmap;
    private Bitmap mCloseBitmap;
    private Bitmap mBackground;
    private Bitmap mSlideBitmap;


    public SlideSwitch(Context context) {
        super(context);
        init();
    }

    public SlideSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SlideSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideSwitch);
        mIsOpen = ta.getBoolean(R.styleable.SlideSwitch_isOpen, false);
        int openResourceId = ta.getResourceId(R.styleable.SlideSwitch_openBackground, -1);
        int closeResourceId = ta.getResourceId(R.styleable.SlideSwitch_closeBackground, -1);
        int slideImageId = ta.getResourceId(R.styleable.SlideSwitch_slideImage, -1);

        if (openResourceId != -1) {
            mOpenBackground = openResourceId;
        }
        if (closeResourceId != -1) {
            mCloseBackground = closeResourceId;
        }
        if (slideImageId != -1) {
            mSlideImgId = slideImageId;
        }
        init();
        ta.recycle();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //计算宽高比
        float v = (float) mBackground.getWidth() / mBackground.getHeight();
        if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        } else if (widthSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSpecSize, (int) (widthSpecSize / v));
        } else if (heightSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension((int) (widthSpecSize * v), heightSpecMode);
        } else {
            setMeasuredDimension(mBackground.getWidth(), mBackground.getHeight());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        super.onSizeChanged(w, h, old_w, old_h);
        //以测量长度与原图长度相差较小的一边为基准缩放bitmap
        if (Math.abs(w - mBackground.getWidth()) < Math.abs(h - mBackground.getHeight())) {
            setBitMapSize((float) w / mBackground.getWidth());
        } else {
            setBitMapSize((float) h / mBackground.getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBackground, 0, 0, null);
        canvas.drawBitmap(mSlideBitmap, mSlideLeft, 0, null);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mSlideLeft = mScroller.getCurrX();
            invalidate();
        }
    }

    public void setOpen(boolean isOpen) {
        mIsOpen = isOpen;
        post(() -> {
            if (mIsOpen) {
                mScroller.startScroll(mSlideLeft, 0, mMaxLeft - mSlideLeft, 0, 500);
            } else {
                mScroller.startScroll(mSlideLeft, 0, -mSlideLeft, 0, 500);
            }
            setBackground(mIsOpen);
            invalidate();
        });
    }

    private void init() {
        mOpenBitmap = BitmapFactory.decodeResource(getResources(), mOpenBackground);
        mCloseBitmap = BitmapFactory.decodeResource(getResources(), mCloseBackground);
        mSlideBitmap = BitmapFactory.decodeResource(getResources(), mSlideImgId);
        setBackground(mIsOpen);

        //点击改变开关状态
        this.setOnClickListener(v -> {
            setOpen(!mIsOpen);
            if (listener != null) {
                listener.onButtonChange(this, mIsOpen);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isOnClick = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) event.getX();
                //x 手指坐标改变的大小，有正负
                int diffX = endX - mStartX;
                //记录每次移动的距离
                mMoveX += diffX;
                //按钮左边距更具手指坐标改变而改变
                mSlideLeft += diffX;
                //不能超过最大值
                if (mSlideLeft > mMaxLeft) {
                    mSlideLeft = mMaxLeft;
                } else if (mSlideLeft < 0) {
                    mSlideLeft = 0;
                }
                invalidate();
                mStartX = endX;
                break;
            case MotionEvent.ACTION_UP:
                boolean isOpen = mIsOpen;
                //移动距离大于2就进行处理
                int moveX = Math.abs(this.mMoveX);
                if (moveX > 2) {
                    if (Math.abs(this.mMoveX) > mMaxLeft / 2) {
                        mIsOpen = !mIsOpen;
                    }
                    if (mIsOpen) {
                        mScroller.startScroll(mSlideLeft, 0, mMaxLeft - mSlideLeft, 0, 500);
                    } else {
                        mScroller.startScroll(mSlideLeft, 0, -mSlideLeft, 0, 500);
                    }
                    setBackground(mIsOpen);
                    invalidate();
                    if (listener != null && isOpen != mIsOpen) {
                        listener.onButtonChange(this, mIsOpen);
                    }
                    isOnClick = false;
                }
                this.mMoveX = 0;
                break;
        }
        //如果是点击事件就交给父类处理
        return !isOnClick || super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //处理事件冲突，请求父容器不要拦截事件，然后直接交给onTouchEvent处理
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    /**
     * 设置背景
     *
     * @param isOpen isOpen
     */
    private void setBackground(boolean isOpen) {
        mBackground = isOpen ? mOpenBitmap : mCloseBitmap;
    }

    /**
     * 设置Bitmap缩放比例
     *
     * @param scale scale
     */
    private void setBitMapSize(float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);//x y 对应宽高缩小的比例，比如matrix.postScale(0.99f, 0.99f)
//        mOpenBitmap = Bitmap.createBitmap(mOpenBitmap, 0, 0, mOpenBitmap.getWidth(), mOpenBitmap.getHeight(), matrix, true);
//        mCloseBitmap = Bitmap.createBitmap(mCloseBitmap, 0, 0, mCloseBitmap.getWidth(), mCloseBitmap.getHeight(), matrix, true);
//        mSlideBitmap = ThumbnailUtils.extractThumbnail(mSlideBitmap, mOpenBitmap.getHeight(), mOpenBitmap.getHeight());
        setBackground(mIsOpen);
        mMaxLeft = mBackground.getWidth() - mSlideBitmap.getWidth();
        if (this.mIsOpen) {
            //如果是打开状态
            mSlideLeft = mMaxLeft;
        } else {
            //如果是关闭状态
            mSlideLeft = 0;
        }
    }

    /**
     * 状态改变的回调
     */
    public OnSlideSwitchChangeListener listener;

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public void setOnChangeListener(OnSlideSwitchChangeListener listener) {
        this.listener = listener;
    }

    public interface OnSlideSwitchChangeListener {
        void onButtonChange(SlideSwitch view, boolean isOpen);
    }

}
