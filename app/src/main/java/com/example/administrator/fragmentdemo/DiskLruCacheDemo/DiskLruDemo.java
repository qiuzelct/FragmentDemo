package com.example.administrator.fragmentdemo.DiskLruCacheDemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.PriorityQueue;

/**
 * Created by Administrator on 2015/10/30.
 */
public class DiskLruDemo {

    private DiskLruCache disk;
    private Context context;
    private static final String DISK_CACHE_NAME="qiuze";
    private  static final int  DISK_CACHE_SIZE=1024*1024*10;

    public DiskLruDemo(Context context){
        File cacheDir=getDiskCacheDir(context,DISK_CACHE_NAME);
        try {
            if (!cacheDir.exists()){
                cacheDir.mkdirs();
            }
            disk=DiskLruCache.open(cacheDir,0,1,DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public int getAppVersion(Context context){

        try {
            PackageInfo info=context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return info.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    public File getDiskCacheDir(Context context,String uniqueName){
        String cachePath=null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())||
                !Environment.isExternalStorageRemovable()){
                cachePath=context.getExternalCacheDir().getPath();

        }else {
            cachePath=context.getCacheDir().getPath();

        }
        return new File(cachePath+File.separator+uniqueName);
        //

    }
    public String hashKeyForDisk(String key){
        String cacheKey;
        try {
            MessageDigest mdigest=MessageDigest.getInstance("MD5");
            mdigest.update(key.getBytes());
            cacheKey=byteToHexString(mdigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey=String.valueOf(key.hashCode());
        }

        return cacheKey;
    }
    private String byteToHexString(byte[] bytes){
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<bytes.length;i++){
            String hex=Integer.toHexString(0xff&bytes[i]);
            if (hex.length()==1){
                sb.append('0');
            }
            sb.append(hex);

        }
        return sb.toString();

    }

    private boolean downlaodBitmapForUrl(String url,OutputStream outputStream){
        HttpURLConnection conn=null;
        BufferedInputStream in=null;
        BufferedOutputStream out=null;
        try {
            URL url1=new URL(url);
            conn= (HttpURLConnection) url1.openConnection();
            in=new BufferedInputStream(conn.getInputStream(),8*1024);
            out=new BufferedOutputStream(outputStream,8*1024);
            int b;
            while ((b=in.read())!=-1){
                out.write(b);
            }
            return  true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (conn!=null) {
                conn.disconnect();
            }
            try {
                if (in!=null){
                in.close();
                }
                if (out!=null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  false;

    }
    public void addUrlToDisk(String url){
        String key=hashKeyForDisk(url);
        try {
            DiskLruCache.Editor editor= disk.edit(key);
            OutputStream outputStream=editor.newOutputStream(0);
            if (downlaodBitmapForUrl(url,outputStream)){
                editor.commit();

            }else {
                editor.abort();
            }
            disk.flush();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    public Bitmap getBitmapFromDisk(String url){
        String key=hashKeyForDisk(url);
        try {
            DiskLruCache.Snapshot snapshot=disk.get(key);
            if (snapshot!=null){
                InputStream in=snapshot.getInputStream(0);
                Bitmap bitmap= BitmapFactory.decodeStream(in);
                return bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public void removeCache(String url){
        String key=hashKeyForDisk(url);
        try {
            disk.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
