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
public class FragmentTwo extends android.support.v4.app.Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.two,container,false);
        Button button= (Button) v.findViewById(R.id.button_two);
        button.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
//        FragmentThree three=new FragmentThree();
//        FragmentManager fm=getFragmentManager();
//        FragmentTransaction transaction=fm.beginTransaction();
//        transaction.hide(this);
//        transaction.add(R.id.id_content, three, "three");
//        transaction.addToBackStack(null);
//        transaction.commit();
    }
}
