package com.example.administrator.fragmentdemo.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/11/1.
 */
public class ColorButton extends ImageView {
    private Paint mbackgroundpaint;
    private int borderColor;
    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mbackgroundpaint=new Paint();
        mbackgroundpaint.setAntiAlias(true);
        mbackgroundpaint.setColor(borderColor);
        mbackgroundpaint.setStyle(Paint.Style.FILL);
        mbackgroundpaint.setStrokeWidth(1f);

    }
    public void setColor(int color){
        borderColor=color;
        invalidate();

    }
    public int getBorderColor(){
        return borderColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        RectF rect=new RectF(0,0,canvas.getWidth(),canvas.getHeight());
//        canvas.drawRoundRect(rect,canvas.getHeight()/2,canvas.getHeight()/2,mbackgroundpaint);
            canvas.drawCircle(getWidth()/2f,getHeight()/2f,Math.min(getHeight()/2f,getWidth()/2f),mbackgroundpaint);
    }
}
