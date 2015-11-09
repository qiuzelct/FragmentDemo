package com.example.administrator.fragmentdemo.CustomView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.fragmentdemo.R;

import java.util.Random;

/**
 * Created by Administrator on 2015/11/1.
 */
public class MyLetterImage extends ImageView{

    private Paint mTextPaint;
    private Paint mBackgroundPaint;
    private int mTextColor= Color.WHITE;
    private char mLetter;
    private Paint strokepaint;

    public MyLetterImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mBackgroundPaint=new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(randomColor());
        strokepaint=new Paint();
        strokepaint.setAntiAlias(true);
        strokepaint.setStyle(Paint.Style.STROKE);
        strokepaint.setAlpha(1);
        strokepaint.setStrokeWidth(1f);
        strokepaint.setColor(Color.RED);
    }
    public void setChar(char mLetter){
        this.mLetter=mLetter;
        invalidate();
    }
    public char getChar(){
        return mLetter;
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        if (getDrawable() == null) {
//            // Set a text font size based on the height of the view
//            mTextPaint.setTextSize(canvas.getHeight() - getTextPadding() * 2);
//                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);
//
//            // Measure a text
//            Rect textBounds = new Rect();
//            //计算文字所在矩形的大小
//            mTextPaint.getTextBounds(String.valueOf(mLetter), 0, 1, textBounds);
//            //得到文字的宽高
//            float textWidth = mTextPaint.measureText(String.valueOf(mLetter));
//            float textHeight = textBounds.height();
//            // Draw the text
//            canvas.drawText(String.valueOf(mLetter), canvas.getWidth() / 2f - textWidth / 2f,
//                    canvas.getHeight() / 2f + textHeight / 2f, mTextPaint);
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //如果没有设置图片
        if (getDrawable()==null) {

            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);
            Rect rect = new Rect();
            mTextPaint.setTextSize(canvas.getHeight()-getTextPadding()*2);
            mTextPaint.getTextBounds(String.valueOf(mLetter), 0, 1, rect);
            float textwidth = mTextPaint.measureText(String.valueOf(mLetter));
            float textheight = rect.height();
            canvas.drawText(String.valueOf(mLetter), canvas.getWidth() / 2f - textwidth / 2f,
                    canvas.getHeight() / 2f + textheight / 2f, mTextPaint);
            canvas.drawRect(0, 0, canvas.getWidth()+2, canvas.getHeight()+2, strokepaint);
        }
    }
    private float getTextPadding() {
        // Set a default padding to 8dp
        return 8 * getResources().getDisplayMetrics().density;
    }

    private int randomColor() {
        Random random = new Random();
        String[] colorsArr = getResources().getStringArray(R.array.colors);
        return Color.parseColor(colorsArr[random.nextInt(colorsArr.length)]);
    }



}
