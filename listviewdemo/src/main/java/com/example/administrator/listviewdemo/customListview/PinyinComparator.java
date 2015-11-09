package com.example.administrator.listviewdemo.customListview;

import java.util.Comparator;

/**
 * Created by Administrator on 2015/11/7.
 */
public class PinyinComparator implements Comparator<SortPerson> {
    @Override
    public int compare(SortPerson o1, SortPerson o2) {
        if (o1.getSortkey().equals("@")
                || o2.getSortkey().equals("#")) {
            return 1;
        } else if (o1.getSortkey().equals("#")
                || o2.getSortkey().equals("@")) {
            return -1;
        } else {
            return o1.getSortkey().compareTo(o2.getSortkey());
        }
    }
}
