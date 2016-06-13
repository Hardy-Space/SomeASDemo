package com.hardy.person.planegame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    static int screenWidth ;
    static int screenHeight ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        Display display = getWindowManager().getDefaultDisplay() ;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels ;
        screenHeight = displayMetrics.heightPixels ;
        setContentView(R.layout.activity_main);

    }



}
