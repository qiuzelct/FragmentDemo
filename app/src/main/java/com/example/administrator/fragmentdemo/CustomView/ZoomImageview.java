package com.example.administrator.fragmentdemo.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2015/11/1.
 */
public class ZoomImageview extends View{
    private  static final int STATE_INIT=1;
    private  static  final int STATE_ZOOM_OUT=2;
    private  static  final  int STATE_ZOOM_IN=3;
    private  static  final int STATE_MOVE=4;
    private Matrix matrix=new Matrix();
    private  int currentState;
    private int height;
    private  int width;
    private Bitmap sourceBitmap;
    private double lastfingers;
    private float centerPointX;
    private float centerPointY;
    private float currentBitmapWidth;
    private float currentBitmapHeight;
    private float lastXmove=-1;
    private float lastYmove=-1;
    private float moveDistanceX;
    private float moveDistanceY;
    private float totaltranslateX;
    private float totaltranslateY;
    private float totalRadio;
    private float scaleRadio;
    private float initRadio;



    public ZoomImageview(Context context) {
        super(context);
    }

    public ZoomImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentState=STATE_INIT;
    }
    public void setImageBitmap(Bitmap bitmap){
        sourceBitmap=bitmap;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            width=getWidth();
            height=getHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (event.getPointerCount()==2) {
                        lastfingers = CaculateDistanceForFingers(event);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getPointerCount()==1){
                        float moveX=event.getX();
                        float moveY=event.getY();
                        if (lastXmove==-1&&lastYmove==-1){

                            lastXmove=moveX;
                            lastYmove=moveY;
                        }
                        currentState=STATE_MOVE;
                        moveDistanceX=moveX-lastXmove;
                        moveDistanceY=moveY-lastYmove;
                        if (totaltranslateX+moveDistanceX>0){
                            moveDistanceX=0;
                        }
                        else if (width-(totaltranslateX+moveDistanceX)>currentBitmapWidth){

                            moveDistanceX=0;
                        }
                        if (totaltranslateY+moveDistanceY>0){
                            moveDistanceY=0;
                        }
                        else if (height-(totaltranslateY+moveDistanceY)>currentBitmapHeight){

                            moveDistanceY=0;
                        }
                        invalidate();
                        lastXmove=moveX;
                        lastYmove=moveY;

                    }
                    else if (event.getPointerCount()==2){
                        centerPoint(event);
                        double fingers=CaculateDistanceForFingers(event);
                        if (fingers>lastfingers){
                            currentState=STATE_ZOOM_OUT;

                        }else {
                            currentState=STATE_ZOOM_IN;
                        }
                        if ((currentState==STATE_ZOOM_OUT&&totalRadio<4*initRadio)||
                                (currentState==STATE_ZOOM_IN&&totalRadio>initRadio)){
                            scaleRadio= (float) (fingers/lastfingers);
                            totalRadio=scaleRadio*totalRadio;
                            if (totalRadio>4){
                                totalRadio=4;

                            }else if (totalRadio<initRadio){
                                totalRadio=initRadio;
                            }

                            invalidate();
                            lastfingers=fingers;

                        }



                    }
                    break;
                case MotionEvent.ACTION_UP:
                    lastXmove=-1;
                    lastYmove=-1;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    if (event.getPointerCount()==2){
                        lastXmove=-1;
                        lastYmove=-1;

                    }
                    break;
                default:
                    break;
            }


        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (currentState){
            case STATE_INIT:
                initBitmap(canvas);
                break;
            case STATE_ZOOM_IN:
               zoom(canvas);
                break;
            case STATE_ZOOM_OUT:
                zoom(canvas);
                break;
            case STATE_MOVE:
                move(canvas);
                break;
            default:
                canvas.drawBitmap(sourceBitmap,matrix,null);
        }
    }
    private void move(Canvas canvas){
        matrix.reset();
        float translateX=totaltranslateX+moveDistanceX;
        float translateY=totaltranslateY+moveDistanceY;
        matrix.postScale(totalRadio,totalRadio);
        matrix.postTranslate(translateX, translateY);
        totaltranslateX=translateX;
        totaltranslateY=translateY;
        canvas.drawBitmap(sourceBitmap,matrix,null);

    }
    private void zoom(Canvas canvas){
        matrix.reset();
        matrix.postScale(totalRadio,totalRadio);
        float scalewidth=sourceBitmap.getWidth()*totalRadio;
        float scaleheight=sourceBitmap.getHeight()*totalRadio;
        float bitmapTranslateX=0f;
        float bitmaptranslateY=0f;
        if (currentBitmapWidth<width){
            bitmapTranslateX=(width-scalewidth)/2f;

        }else {
            //右边的偏移量+中点的偏移量
            bitmapTranslateX=totaltranslateX*scaleRadio+centerPointX*(1-scaleRadio);
            if (bitmapTranslateX>0){
                bitmapTranslateX=0f;

            }else if (width-bitmapTranslateX>scalewidth){
                bitmapTranslateX=width-scalewidth;
            }


        }
        if (currentBitmapHeight<height){
            bitmaptranslateY=(height-scaleheight)/2f;

        }else {
            bitmaptranslateY=totaltranslateY*scaleRadio+centerPointY*(1-scaleRadio);
            if (bitmaptranslateY>0){
                bitmaptranslateY=0f;

            }else if (height-bitmaptranslateY>scaleheight){
                bitmaptranslateY=height-scaleheight;
            }


        }
        matrix.postTranslate(bitmapTranslateX,bitmaptranslateY);
        totaltranslateX=bitmapTranslateX;
        totaltranslateY=bitmaptranslateY;
        currentBitmapWidth=scalewidth;
        currentBitmapHeight=scaleheight;
        canvas.drawBitmap(sourceBitmap,matrix,null);

    }
    private void initBitmap(Canvas canvas) {
        if (sourceBitmap != null) {
            matrix.reset();
            int bitmapwidth=sourceBitmap.getWidth();
            int bitmapheight=sourceBitmap.getHeight();
            if (bitmapwidth>width||bitmapheight>height){
                if (bitmapwidth>width){
                    float scaleRadio=width/(bitmapwidth*1f);
                    matrix.postScale(scaleRadio,scaleRadio);
                    float translateY=(height-bitmapheight*scaleRadio)/2.0f;
                    matrix.postTranslate(0,translateY);
                    totaltranslateY=translateY;
                    totalRadio=initRadio=scaleRadio;

                }
                else if (bitmapheight>height){
                    float scaleRadio=height/(bitmapheight*1f);
                    matrix.postScale(scaleRadio,scaleRadio);
                    float translateX=(width-bitmapwidth*scaleRadio)/2.0f;
                    matrix.postTranslate(0,translateX);
                    totaltranslateX=translateX;
                    totalRadio=initRadio=scaleRadio;


                }
                currentBitmapHeight=initRadio*bitmapheight;
                currentBitmapWidth=initRadio*bitmapwidth;

            }else {
                float translateX=(width-bitmapwidth)/2f;
                float translateY=(height-bitmapheight)/2f;
                matrix.postTranslate(translateX,translateY);
                totaltranslateX=translateX;
                totaltranslateY=translateY;
                totalRadio=initRadio=1;
                currentBitmapHeight=bitmapheight;
                currentBitmapWidth=bitmapwidth;



            }

            canvas.drawBitmap(sourceBitmap,matrix,null);
        }
    }


    private double CaculateDistanceForFingers(MotionEvent event){
        int x0= (int) event.getX(0);
        int x1= (int) event.getX(1);
        int y0= (int) event.getY(0);
        int y1= (int) event.getY(1);
        int x=Math.abs(x1-x0);
        int y=Math.abs(y1-y0);
        return Math.sqrt(x*x+y*y);

    }
    private void centerPoint(MotionEvent event){
        float x0=event.getX(0);
        float x1=event.getX(1);
        float y0=event.getY(0);
        float y1=event.getY(1);
        centerPointX=(x0+x1)/2;
        centerPointY=(y0+y1)/2;
    }

}
