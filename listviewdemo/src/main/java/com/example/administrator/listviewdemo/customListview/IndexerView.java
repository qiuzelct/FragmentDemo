package com.example.administrator.listviewdemo.customListview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.listviewdemo.R;

/**
 * Created by Administrator on 2015/11/7.
 */
public class IndexerView extends View {
    private  String[] key={"#","A","B","C","D","E","F","G","H","I","J",
            "K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private Paint mpaint;
    private TextView dialogTextview;
    private  LetterImageView imageView;
    private int choose=-1;
    private OnChangedListener mlistener;


    public IndexerView(Context context) {
        super(context);
    }

    public IndexerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mpaint=new Paint();
        mpaint.setAntiAlias(true);
    }
    public void setTextview(TextView textview){
        dialogTextview=textview;
    }
    public void setImageView(LetterImageView imageView){
        this.imageView=imageView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width=getWidth();
        int height=getHeight();
        int singleHeight=height/key.length;
        for (int i=0;i<key.length;i++){
            mpaint.setColor(Color.rgb(33, 65, 98));
           mpaint.setTypeface(Typeface.DEFAULT_BOLD);
            mpaint.setTextSize(24);
            if (i==choose){
            mpaint.setColor(Color.parseColor("#3399ff"));
                mpaint.setFakeBoldText(true);
            }
            float xPos=width/2-mpaint.measureText(key[i])/2;
            float yPos=singleHeight*i+singleHeight;
            canvas.drawText(key[i],xPos,yPos,mpaint);
            mpaint.reset();



        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        final float y=event.getY();
        final  int oldchoose=choose;
        final  int position= (int) (y/getHeight()*key.length);
        final  OnChangedListener listener=mlistener;

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose=-1;
                invalidate();
//                if (dialogTextview!=null){
//                    dialogTextview.setVisibility(View.INVISIBLE);
//                }
                if (imageView!=null){
                    imageView.setVisibility(View.INVISIBLE);
                }

                break;
            default:
                setBackgroundColor(Color.rgb(192,192,192));
                if (oldchoose!=position){
                    if (position>=0&&position<key.length){
                        if (listener!=null){
                            listener.onChanged(key[position]);
                        }

//                        if (dialogTextview!=null){
//                            dialogTextview.setText(key[position]);
//                            dialogTextview.setVisibility(View.VISIBLE);
//                        }
                        if (imageView!=null){
                            imageView.setLetter(key[position].charAt(0));
                            imageView.setVisibility(View.VISIBLE);
                        }
                        choose=position;
                        invalidate();

                    }


                }
                break;
        }
            return  true;
    }

    public  void setOnChangedListener(OnChangedListener listener){
        mlistener=listener;
    }
    public  interface OnChangedListener{

        public void  onChanged(String s);
    }

}
