package com.example.administrator.fragmentdemo.CustomView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.fragmentdemo.R;

/**
 * Created by Administrator on 2015/11/1.
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qiuze);
        MyLetterImage image= (MyLetterImage)findViewById(R.id.iv_avatar);
//        final Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.item03);
        image.setImageResource(R.drawable.item03);
        MyLetterImage image2= (MyLetterImage) findViewById(R.id.id_avatar);
        TextView textView= (TextView) findViewById(R.id.text);
       String name= textView.getText().toString();
        image2.setChar(name.charAt(0));
    }
}
