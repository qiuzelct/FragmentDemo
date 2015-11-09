package com.example.administrator.fragmentdemo;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private MyViewPager viewPager;
    private List<Fragment> fragments=new ArrayList<Fragment>();


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FragmentOne one=new FragmentOne();
//        FragmentManager fm=getFragmentManager();
//        FragmentTransaction transaction=fm.beginTransaction();
//        transaction.add(R.id.id_content, one);
//        transaction.commit();
            FragmentOne one=new FragmentOne();
            FragmentTwo two=new FragmentTwo();
            FragmentThree three=new FragmentThree();
            fragments.add(one);
            fragments.add(two);
            fragments.add(three);
            FragmentManager fm=getSupportFragmentManager();
        viewPager= (MyViewPager) findViewById(R.id.id_viewpager);
        viewPager.setCanalScroll(true);
        viewPager.setAdapter(new FragmentPagerAdapter(fm) {
           @Override
           public Fragment getItem(int i) {
               return fragments.get(i);
           }

           @Override
           public int getCount() {
               return fragments.size();
           }
       });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        Toast.makeText(MainActivity.this,"one",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this,"two",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this,"three",Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


}
