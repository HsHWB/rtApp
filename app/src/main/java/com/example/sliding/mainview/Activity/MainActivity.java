package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Button;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.example.sliding.mainview.View.CustomView.SlidingMenu;
import com.example.sliding.mainview.View.CustomView.SlidingView;
import com.example.sliding.mainview.View.Fragment.ContentFragment;
import com.example.sliding.mainview.View.Fragment.EmptyTableFragment;
import com.example.sliding.mainview.View.Fragment.MenuFragment;
import com.example.sliding.mainview.View.Fragment.NotedTableFragment;

public class MainActivity extends Activity {

    public static float screenWidth;
    public static float screenHeight;

    private SlidingMenu slidingMenu;
    private ContentFragment contentFragment;
    private EmptyTableFragment emptyTableFragment;
    private NotedTableFragment notedTableFragment;
    private MenuFragment menuFragment;
    private FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rt_main_activity);
        screenHeight = WindowsUtils.getWindowHeight(getApplicationContext());
        screenWidth = WindowsUtils.getWindowWidth(getApplicationContext());

        contentFragment = new ContentFragment();
        menuFragment = new MenuFragment();

        slidingMenu = (SlidingMenu) this.findViewById(R.id.rt_main_slidingMenu);
        fm = getFragmentManager();
        slidingMenu.setFragmentManager(fm);
        slidingMenu.replaceFragemnt(menuFragment, contentFragment);
    }

}
