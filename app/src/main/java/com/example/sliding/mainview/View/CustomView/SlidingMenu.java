package com.example.sliding.mainview.View.CustomView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;


/**
 * Created by huehn on 15/11/23.
 */
public class SlidingMenu extends HorizontalScrollView {

    private Context mContext;

    private View slidingView;
    private FrameLayout childMenu;
    private FrameLayout childContent;
    private FragmentManager fm;
    private FragmentTransaction transaction;

    private float screenWidth;
    private float screenHeight;

    private Fragment notedTable;
    private Fragment emptyTable;
    private Fragment menu;
    private LinearLayout.LayoutParams menull;
    private LinearLayout.LayoutParams contentll;

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    /**
     * 从activity中获取fm
     * @param fm
     */
    public void setFragmentManager(FragmentManager fm){
        this.fm = fm;
    }

    /**
     * 传入fragment分别代替布局中的三个fragment
     *
     */
    public void replaceFragemnt(Fragment menu, Fragment notedFragment, Fragment emptyFragment){
        this.menu = menu;
        this.emptyTable = emptyFragment;
        this.notedTable = notedFragment;

    }

    /**
     * 计算子view的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenHeight = WindowsUtils.getWindowHeight(mContext);
        screenWidth = WindowsUtils.getWindowWidth(mContext);

        this.childMenu = (FrameLayout) this.findViewById(R.id.slidingview_select_item);
        this.childContent = (FrameLayout) this.findViewById(R.id.slidingview_content);

        menull = new LinearLayout.LayoutParams(2*(int)screenWidth/3,(int)screenHeight);
        contentll = new LinearLayout.LayoutParams((int)screenWidth,(int)screenHeight);
        this.childMenu.setLayoutParams(menull);
        this.childContent.setLayoutParams(contentll);

    }

    /**
     * 子view应该放哪里
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        /**
         * 换掉菜单和空余的餐桌页
         */
        transaction = fm.beginTransaction();
        transaction.replace(this.childMenu.getId(), this.menu);
        transaction.replace(this.childContent.getId(), this.emptyTable);
        transaction.commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
