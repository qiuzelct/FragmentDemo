package com.example.administrator.listviewdemo.customListview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.administrator.listviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/7.
 */
public class IndexerAdapter extends BaseAdapter implements SectionIndexer {
    private List<SortPerson> list=new ArrayList<>();
    private Context context;


    public  IndexerAdapter(Context context, List<SortPerson> list){
        this.context=context;
        this.list=list;

    }
    public void updateData(List<SortPerson> list){
        this.list=list;
        notifyDataSetChanged();

    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {

        for (int i = 0; i < list.size(); i++) {
            String sortStr = list.get(i).getSortkey();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;

    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortkey().charAt(0);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        final  SortPerson p= list.get(position);
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_list,null);
            viewHolder.title= (TextView) convertView.findViewById(R.id.title);
            viewHolder.contact=(TextView) convertView.findViewById(R.id.contact);
            viewHolder.imageView= (LetterImageView) convertView.findViewById(R.id.letter_imageview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        int section=getSectionForPosition(position);
        if (position==getPositionForSection(section)){
            viewHolder.title.setText(p.getSortkey());
            viewHolder.title.setVisibility(View.VISIBLE);

        }else {
            viewHolder.title.setVisibility(View.GONE);
        }
         viewHolder.contact.setText(p.getName());
         viewHolder.imageView.setLetter(p.getName().charAt(0));


        return convertView;
    }

    class  ViewHolder{
        TextView title;
        TextView contact;
        LetterImageView imageView;


    }
    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
}
