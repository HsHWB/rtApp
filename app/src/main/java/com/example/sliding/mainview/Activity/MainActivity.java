package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.example.sliding.mainview.View.CustomView.SlidingMenu;
import com.example.sliding.mainview.View.Fragment.ContentFragment;
import com.example.sliding.mainview.View.Fragment.EmptyTableFragment;
import com.example.sliding.mainview.View.Fragment.MenuFragment;
import com.example.sliding.mainview.View.Fragment.NotedTableFragment;


public class MainActivity extends FragmentActivity {

    public static float screenWidth;
    public static float screenHeight;
    private FragmentManager fm;
    private FragmentTransaction transaction;

    private SlidingMenu slidingMenu;
    private FrameLayout childMenu;
    private FrameLayout childContent;
    private int menuWidth;
    private int quarterMenuWidth;
    private ContentFragment contentFragment;
    private MenuFragment menuFragment;
    private LinearLayout.LayoutParams menull;
    private LinearLayout.LayoutParams contentll;

    private EmptyTableFragment emptyTableFragment;
    private NotedTableFragment notedTableFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rt_main_activity);
        screenHeight = WindowsUtils.getWindowHeight(getApplicationContext());
        screenWidth = WindowsUtils.getWindowWidth(getApplicationContext());

        initView();
    }

    private void initView(){
        /**
         * 菜单栏的宽度
         */
        menuWidth = 2*(int)screenWidth/3;
        quarterMenuWidth = menuWidth / 4;

        /**
         * 获取关于侧滑控件的子控件id,这里还没封装好
         */
        slidingMenu = (SlidingMenu) this.findViewById(R.id.rt_main_slidingMenu);
        childMenu = (FrameLayout) slidingMenu.findViewById(R.id.slidingview_select_item);
        childContent = (FrameLayout) slidingMenu.findViewById(R.id.slidingview_content);

        /**
         * 设置侧滑控件的子控件大小
         */
        menull = new LinearLayout.LayoutParams(2*(int)screenWidth/3,(int)screenHeight);
        contentll = new LinearLayout.LayoutParams((int)screenWidth,(int)screenHeight);
        childMenu.setLayoutParams(menull);
        childContent.setLayoutParams(contentll);

        /**
         * 替换fragment
         */
        contentFragment = new ContentFragment();
        menuFragment = new MenuFragment();
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(childMenu.getId(), menuFragment, "menu");
        transaction.add(this.childContent.getId(), contentFragment, "content");
        transaction.commit();

        /**
         * 首次打开app通知  内容fragment  用空余餐桌fragment代替
         */
        contentFragment.replaceView();
    }

}
