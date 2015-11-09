package com.example.administrator.fragmentdemo.CustomView;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.example.administrator.fragmentdemo.R;

import java.util.Arrays;
import java.util.List;


public class MyActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListAdapter listAdapter = new SampleListAdapter(this, Arrays.asList(getResources().getStringArray(R.array.names)));
        setListAdapter(listAdapter);
    }

    private static class SampleListAdapter extends ArrayAdapter<String> {

        public SampleListAdapter(Context context, List<String> objects) {
            super(context, R.layout.list_item, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String name = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

//            LetterImageView letterImageView = (LetterImageView) convertView.findViewById(R.id.iv_avatar);
//            letterImageView.setOval(true);
            MyLetterImage image= (MyLetterImage) convertView.findViewById(R.id.iv_avatar);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_name);
            image.setChar(name.charAt(0));
            textView.setText(name);

            return convertView;
        }
    }
}
