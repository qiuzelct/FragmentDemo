package com.example.administrator.fragmentdemo.myImageviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.administrator.fragmentdemo.R;


/**
 * Created by Administrator on 2015/10/28.
 */
public class PhotoAdapter extends BaseAdapter implements AbsListView.OnScrollListener {


    private MyImageLoader loader=MyImageLoader.newImageLoader();

        Context context;
    public  PhotoAdapter(Context context){
        this.context=context;

    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public int getCount() {
        return Images.imageUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return Images.imageUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String url= (String) getItem(position);
        ViewHolder view=new ViewHolder();
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView= inflater.inflate(R.layout.childimageview,null);
            view.imageView= (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(view);
        }else {

            view= (ViewHolder) convertView.getTag();

        }
            loaderImageview(url,view.imageView);


        return convertView;
    }
    public void loaderImageview(String url,ImageView imageView){
        String imagekey=url;
        Bitmap bitmap=loader.getBitmapFromCache(imagekey);
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);

        }else{
            MyAncyTask my = new MyAncyTask(imageView);
            my.execute(url);

        }

    }


    class ViewHolder{
        ImageView imageView;

    }

}
