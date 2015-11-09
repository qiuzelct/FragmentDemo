package com.example.administrator.fragmentdemo.myImageviewdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.fragmentdemo.R;
import com.example.administrator.fragmentdemo.ViewpagerActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/10/27.
 */
public class MyImageActivity extends Activity {
    private ImageView mImageView;
    private final String URL = "http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg";
    private MyImageLoader loader;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myimageview);
        gridView= (GridView) findViewById(R.id.gridview);
//        PhotoAdapter adapter=new PhotoAdapter(this);
//        gridView.setAdapter(adapter);
            MyPhotoAdapter adapter=new MyPhotoAdapter(this,0,Images.imageUrls,gridView);
            gridView.setAdapter(adapter);



    }
//    public void loaderImageview(String url,ImageView imageView){
//        String imagekey=url;
//        Bitmap bitmap=loader.getBitmapFromCache(imagekey);
//        if (bitmap!=null){
//            imageView.setImageBitmap(bitmap);
//
//        }else{
//            MyAncyTask my = new MyAncyTask(mImageView);
//            my.execute(url);
//
//        }
//
//    }



//    class MyAncyTask extends AsyncTask<String, Integer, Bitmap> {
//
//        private final WeakReference wf;
//        private int data;
//        private String urlstring;
//
//        public MyAncyTask(ImageView imageView) {
//            wf = new WeakReference(imageView);
//
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
////            data=params[0];
//            urlstring = params[0];
//
//            HttpURLConnection con = null;
//            FileOutputStream fos = null;
//            BufferedOutputStream bos = null;
//            BufferedInputStream bis = null;
//            File imageFile = null;
//            try {
//                URL url = new URL(urlstring);
//                con = (HttpURLConnection) url.openConnection();
//                con.setConnectTimeout(1000);
//                con.setReadTimeout(5 * 1000);
//                bis = new BufferedInputStream(con.getInputStream());
//                imageFile = new File(getImagePath(urlstring));
//                fos = new FileOutputStream(imageFile);
//                bos = new BufferedOutputStream(fos);
//                byte[] b = new byte[1024];
//                int length;
//                while ((length = bis.read(b)) != -1) {
//                    bos.write(b, 0, length);
//                    bos.flush();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (bis != null) {
//                        bis.close();
//                    }
//                    if (bos != null) {
//                        bos.close();
//                    }
//                    if (con != null) {
//                        con.disconnect();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            Bitmap bitmap= MyImageLoader.decodeBitmapFromFile(imageFile.getPath(), 500, 500);
//            loader.setbitmapFromCache(urlstring,bitmap);
//            return bitmap;
//
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (wf != null && bitmap != null) {
//                final ImageView timageView = (ImageView) wf.get();
//                if (timageView != null) {
//                    timageView.setImageBitmap(bitmap);
//                }
//
//            }
//        }
//
//        private String getImagePath(String imageUrl) {
//            int lastSlashIndex = imageUrl.lastIndexOf("/");
//            String imageName = imageUrl.substring(lastSlashIndex + 1);
//            String imageDir = Environment.getExternalStorageDirectory()
//                    .getPath() + "/PhotoWallFalls/";
//            File file = new File(imageDir);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            String imagePath = imageDir + imageName;
//            return imagePath;
//        }
//    }
}
