package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.example.sliding.mainview.View.CustomView.SlidingView;

public class MainActivity extends Activity {

    public static Button slidingCoverButton;
    public static float screenWidth;
    public static float screenHeight;
    private SlidingView slidingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenHeight = WindowsUtils.getWindowHeight(getApplicationContext());
        screenWidth = WindowsUtils.getWindowWidth(getApplicationContext());

    }

}
