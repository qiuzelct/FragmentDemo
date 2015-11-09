package com.example.administrator.listviewdemo.customListview;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.listviewdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/11/7.
 */
public class ListviewActivity extends AppCompatActivity {
    private ListView sortListView;
    private IndexerView indexerView;
    private TextView dialog;
    private IndexerAdapter adapter;
    private LetterImageView centerimageview;


    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortPerson> SourceList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);
        initViews();
    }

    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        indexerView = (IndexerView) findViewById(R.id.indexerview);
//        dialog = (TextView) findViewById(R.id.dialog);
        centerimageview= (LetterImageView) findViewById(R.id.center_imageview);
        centerimageview.setOval(true);
//        indexerView.setTextview(dialog);
        indexerView.setImageView(centerimageview);
        indexerView.setOnChangedListener(new IndexerView.OnChangedListener() {
            @Override
            public void onChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

            List<String> people=new ArrayList<String>();
        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor=getContentResolver().query(uri,new String[]{"display_name"},null,
                null,"display_name");
        if (cursor.moveToFirst()){
            do {
                String name=cursor.getString(0);
                people.add(name);

            }while (cursor.moveToNext());
        }
        startManagingCursor(cursor);


        SourceList = filledData(people);

        // 根据a-z进行排序源数据
        Collections.sort(SourceList, pinyinComparator);
        adapter = new IndexerAdapter(this, SourceList);
        sortListView.setAdapter(adapter);

    }
    private List<SortPerson> filledData(List<String> data){
        List<SortPerson> mSortList = new ArrayList<SortPerson>();

        for(int i=0; i<data.size(); i++){
            SortPerson person = new SortPerson();
            person.setName(data.get(i));
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(data.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                person.setSortkey(sortString.toUpperCase());
            }else{
                person.setSortkey("#");
            }

            mSortList.add(person);
        }
        return mSortList;

    }

}
