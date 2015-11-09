package com.example.administrator.fragmentdemo.myImageviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.fragmentdemo.DiskLruCacheDemo.*;
import com.example.administrator.fragmentdemo.R;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Administrator on 2015/10/28.
 */
public class MyPhotoAdapter extends ArrayAdapter<String> implements AbsListView.OnScrollListener {

    private LruCache<String,Bitmap> lruCache;

    private GridView gridView;
    private boolean isFisetEnter=true;
    private Set<BitmapTask> bitmapTasks;
    private int mvisiableItemCount;
    private int mFirstItem;
    private DiskLruDemo diskLruDemo;




    public MyPhotoAdapter(Context context, int resource, String[] textViewResourceId,GridView gridView) {
        super(context, resource, textViewResourceId);
        int maxmemory= (int) Runtime.getRuntime().maxMemory();
        int cache=maxmemory/8;
        bitmapTasks=new HashSet<BitmapTask>();
        lruCache=new LruCache<String ,Bitmap>(cache){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        this.gridView=gridView;
        gridView.setOnScrollListener(this);
        diskLruDemo=new DiskLruDemo(context);

    }

    public  void addBitmapToResource(String key, Bitmap bitmap){
            if (getBitmapFromResource(key)==null){
                lruCache.put(key,bitmap);
            }

    }

    public  Bitmap getBitmapFromResource(String key){

        return lruCache.get(key);

    }
    public void setImageview(String url,ImageView imageView){
        Bitmap bitmap=getBitmapFromResource(url);
        if (bitmap!=null){
//            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(R.drawable.empty_photo);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url= (String) getItem(position);
        View view;
        LayoutInflater inflater=LayoutInflater.from(getContext());
        if (convertView==null){
            view=inflater.inflate(R.layout.childimageview,null);
        }else {
            view=convertView;

        }
        ImageView photo= (ImageView) view.findViewById(R.id.photo);
        photo.setTag(url);
        setImageview(url,photo);
        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState==SCROLL_STATE_IDLE){
            loadBitmaps(mFirstItem,mvisiableItemCount);
        }
        else{
            cancelAllTasks();
        }



    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                mFirstItem=firstVisibleItem;
                               mvisiableItemCount=visibleItemCount;
        if (isFisetEnter&&mFirstItem>0){
                loadBitmaps(firstVisibleItem,visibleItemCount);
                isFisetEnter=false;

        }
    }

    private void loadBitmaps(int mFirstItem,int mvisiableItemCount){

        try {
            for (int i=mFirstItem;i<mFirstItem+mvisiableItemCount;i++){
                    String url=Images.imageUrls[i];
                Bitmap bitmap=getBitmapFromResource(url);
                if (bitmap==null){
                    BitmapTask task=new BitmapTask();
                    bitmapTasks.add(task);
                    task.execute(url);
                }else {
                    final ImageView imageView= (ImageView) gridView.findViewWithTag(url);
                    if (imageView!=null&&bitmap!=null){
                        imageView.setImageBitmap(bitmap);
                    }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void cancelAllTasks() {
        if (bitmapTasks != null) {
            for (BitmapTask task : bitmapTasks) {
                task.cancel(false);
            }
        }
    }

    class  BitmapTask extends AsyncTask<String,Integer,Bitmap>{
            String urlname=null;

        @Override
        protected Bitmap doInBackground(String... params) {
            urlname=params[0];
            Bitmap bitmap = null;
            if (diskLruDemo.getSnapshot()==null){
            diskLruDemo.addUrlToDisk(urlname);

//            Bitmap bitmap=downBitmap(urlname);

                }else {

                bitmap = diskLruDemo.getBitmapFromDisk(urlname);
            }
            if (bitmap!=null){
                addBitmapToResource(params[0],bitmap);
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView= (ImageView) gridView.findViewWithTag(urlname);
            if (imageView!=null&&bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
            bitmapTasks.remove(this);

        }



        public Bitmap downBitmap(String url){
            HttpURLConnection conn=null;
            Bitmap bitmap=null;
            try {
                URL url1=new URL(url);
                conn= (HttpURLConnection) url1.openConnection();
                conn.setConnectTimeout(1 * 1000);
                conn.setReadTimeout(2 * 1000);
                bitmap= BitmapFactory.decodeStream(conn.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                conn.disconnect();
            }
            return bitmap;

        }





    }




}
