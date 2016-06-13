package com.hardy.company.yundong;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener{



    //private Button btn ;
    private TextView text ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*btn = (Button)findViewById(R.id.btn1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSupportActionBar().isShowing()) {
                    getSupportActionBar().hide();
                } else {
                    getSupportActionBar().show();
                }

            }
        });*/
        text = (TextView) findViewById(R.id.text1);
        ActionBar action = getSupportActionBar();
        action.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        action.addTab(action.newTab().setText("第一页").setTabListener(this));
        action.addTab(action.newTab().setText("第二页").setTabListener(this));
        action.addTab(action.newTab().setText("第三页").setTabListener(this));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        int i = tab.getPosition();
        text.setText("点击了第" + i + "个Tab");

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
