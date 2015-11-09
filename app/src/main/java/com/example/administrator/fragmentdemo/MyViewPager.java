package com.example.administrator.fragmentdemo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/10/25.
 */
public class MyViewPager extends ViewPager {

        private  boolean isCanalScroll=true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);


    }
    public void  setCanalScroll(boolean isCanalScroll){

        this.isCanalScroll=isCanalScroll;

    }

//    @Override
//    public void scrollTo(int x, int y) {
//        if (isCanalScroll) {
//            super.scrollTo(x, y);
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanalScroll) {
            return false;
        }else{
            return super.onTouchEvent(ev);

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanalScroll) {
            return false;
        }else{
            return super.onInterceptTouchEvent(ev);

        }
    }
}
