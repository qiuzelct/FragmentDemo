package com.example.administrator.fragmentdemo.myImageviewdemo;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

/**
 * Created by Administrator on 2015/10/27.
 */
public class MyImageLoader {
    LruCache<String, Bitmap> imageCache = null;
    private  static MyImageLoader myImageLoader;

    private MyImageLoader() {
        int maxmemory = (int) Runtime.getRuntime().maxMemory();
        int cachesize = maxmemory / 8;
        imageCache = new LruCache<String,Bitmap>(cachesize) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }
    public static MyImageLoader newImageLoader(){
        if (myImageLoader==null){
            myImageLoader=new MyImageLoader();

        }
        return myImageLoader;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                             int reqHeight){
       final int height= options.outHeight;
        final  int width=options.outWidth;
        int inSampleSize=1;
        if (height>reqHeight||width>reqWidth){
          final  int heightRatio=Math.round((float)height/(float)reqHeight);
          final  int widthRatio=Math.round((float)width/(float)reqWidth);
            inSampleSize=widthRatio<heightRatio?widthRatio:heightRatio;
        }
        return  inSampleSize;

    }
    public static Bitmap decodeBitmapFromResource(Resources res,int Id,int reqwidth,int reqheight){

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res,Id,options);
        options.inSampleSize=calculateInSampleSize(options,reqwidth,reqheight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeResource(res,Id,options);

    }
    public static Bitmap decodeBitmapFromFile(String url,int reqwidth,int reqheight){

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(url,options);
        options.inSampleSize=calculateInSampleSize(options,reqwidth,reqheight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeFile(url,options);



    }




    public void setbitmapFromCache(String key,Bitmap bitmap){
        if (getBitmapFromCache(key)==null){
            imageCache.put(key,bitmap);

        }

    }


    public  Bitmap getBitmapFromCache(String key){
        return imageCache.get(key);

    }


}