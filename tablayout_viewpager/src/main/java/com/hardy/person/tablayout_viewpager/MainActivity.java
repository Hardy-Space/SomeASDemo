package com.hardy.person.tablayout_viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //导航条
    private TabLayout mTab;
    //ViewPager
    private ViewPager mPager;
    //盛放标题的集合
    private List<String> titles = new ArrayList<>();
    //盛放ViewPager的显示内容
    private List<View> views = new ArrayList<>();
    //定义一系列ViewPager要显示的view
    View view1,view2,view3,view4,view5,view6;
    //实例化view用到的压力泵
    private LayoutInflater mLayoutInflater;
    //底部状态栏四个TextView
    private TextView mAdvice;
    private TextView mRank;
    private TextView mFind;
    private TextView mMine;



    private void assignViews() {
        mTab = (TabLayout) findViewById(R.id.tab);
        mPager = (ViewPager) findViewById(R.id.pager);
        mAdvice = (TextView) findViewById(R.id.advice);
        mRank = (TextView) findViewById(R.id.rank);
        mFind = (TextView) findViewById(R.id.find);
        mMine = (TextView) findViewById(R.id.mine);
        mLayoutInflater = getLayoutInflater();
        view1 = mLayoutInflater.inflate(R.layout.viewpager_content,null);
        view2 = mLayoutInflater.inflate(R.layout.viewpager_content,null);
        view3 = mLayoutInflater.inflate(R.layout.viewpager_content,null);
        view4 = mLayoutInflater.inflate(R.layout.viewpager_content,null);
        view5 = mLayoutInflater.inflate(R.layout.viewpager_content,null);
        view6 = mLayoutInflater.inflate(R.layout.viewpager_content,null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到xml中控件
        assignViews();
        //添加多个标题
        titles.add("No.1");
        titles.add("No.2");
        titles.add("No.3");
        titles.add("No.4");
        titles.add("No.5");
        titles.add("No.6");
        //在此设置成滚轴模式，当Tab太多时会滚动显示，其他模式会挤在屏幕中
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置文字下边的选择提示条颜色
        mTab.setSelectedTabIndicatorColor(Color.BLUE);
        //设置背景色
        mTab.setBackgroundColor(Color.BLACK);
        //设置文字颜色，第一个参数是未选择状态下的文字颜色，第二个是已选择状态下的文字颜色
        mTab.setTabTextColors(Color.WHITE, Color.RED);
        //添加标签
        mTab.addTab(mTab.newTab().setText(titles.get(0)));
        mTab.addTab(mTab.newTab().setText(titles.get(1)));
        mTab.addTab(mTab.newTab().setText(titles.get(2)));
        mTab.addTab(mTab.newTab().setText(titles.get(3)));
        mTab.addTab(mTab.newTab().setText(titles.get(4)));
        mTab.addTab(mTab.newTab().setText(titles.get(5)));
        //设置ViewPager的适配器
        MyViewpagerAdapter myViewpagerAdapter = new MyViewpagerAdapter(views);
        mPager.setAdapter(myViewpagerAdapter);
        //将TabLayout和ViewPager关联在一起
        mTab.setupWithViewPager(mPager);
        //为TextView设置点击监听器
        mAdvice.setOnClickListener(this);
        mRank.setOnClickListener(this);
        mFind.setOnClickListener(this);
        mMine.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.advice:
                mAdvice.setSelected(true);
                mRank.setSelected(false);
                mFind.setSelected(false);
                mMine.setSelected(false);
                break;
            case R.id.rank:
                mAdvice.setSelected(false);
                mRank.setSelected(true);
                mFind.setSelected(false);
                mMine.setSelected(false);
                break;
            case R.id.find:
                mAdvice.setSelected(false);
                mRank.setSelected(false);
                mFind.setSelected(true);
                mMine.setSelected(false);
                break;
            case R.id.mine:
                mAdvice.setSelected(false);
                mRank.setSelected(false);
                mFind.setSelected(false);
                mMine.setSelected(true);
                break;
        }


    }

    class MyViewpagerAdapter extends PagerAdapter{

        private List<View> list ;

        public MyViewpagerAdapter(List<View> list) {
            this.list = list;
        }

        //必须重写
        @Override
        public int getCount() {
            return list.size();
        }

        //必须重写
        @Override
        public boolean isViewFromObject(View view, Object object) {
            //官方推荐写法
            return view == object;
        }


        //显示viewpager显示内容
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //添加显示的页卡,最好添加Fragment作为内容
            container.addView(views.get(position));
            return views.get(position);
        }


        //删除页卡
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }


        //设置标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
