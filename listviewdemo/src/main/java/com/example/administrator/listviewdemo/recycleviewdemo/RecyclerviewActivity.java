package com.example.administrator.listviewdemo.recycleviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.listviewdemo.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2015/11/7.
 */
public class RecyclerviewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> mDatas;
    Myadapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview);
        initData();
        recyclerView= (RecyclerView) findViewById(R.id.id_recycleview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setLayoutManager(new GridLayoutManager(this,5));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        myadapter=new Myadapter();
        recyclerView.setAdapter(myadapter);


    }
    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    class Myadapter extends RecyclerView.Adapter<Myadapter.Viewholder>{


        @Override
        public Myadapter.Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Viewholder viewholder= null;
            viewholder=new Viewholder(LayoutInflater.from(RecyclerviewActivity.this).inflate(R.layout.list_recycler,viewGroup,false));
            return viewholder;
        }

        @Override
        public void onBindViewHolder(Myadapter.Viewholder viewholder, int i) {
//            viewholder.tv.setText(mDatas.get(i));
//            viewholder.tv.setHeight(100 + (i % 3) * 30);


        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
            //
        public class Viewholder extends RecyclerView.ViewHolder {
            TextView tv;
             CircleImageView circleImageView;
            public Viewholder(View view){
                super(view);
//                tv= (TextView) view.findViewById(R.id.id_num);
                circleImageView= (CircleImageView) view.findViewById(R.id.img_like);
            }
        }
    }


}
