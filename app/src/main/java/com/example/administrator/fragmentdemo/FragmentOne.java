package com.example.administrator.fragmentdemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Administrator on 2015/10/24.
 */
public class FragmentOne extends android.support.v4.app.Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.one,container,false);
        Button button= (Button) v.findViewById(R.id.button);
        button.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
//        FragmentTwo two=new FragmentTwo();
//        FragmentManager fm=getFragmentManager();
//        FragmentTransaction transaction=fm.beginTransaction();
//        transaction.replace(R.id.id_content,two,"two");
//        transaction.addToBackStack(null);
//        transaction.commit();
    }
}
