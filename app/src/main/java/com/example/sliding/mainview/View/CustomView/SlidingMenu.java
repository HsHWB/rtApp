package com.example.sliding.mainview.View.CustomView;

import android.animation.ObjectAnimator;
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

    private FrameLayout childMenu;
    private FrameLayout childContent;
    private FragmentManager fm;
    private FragmentTransaction transaction;

    private float screenWidth;
    private float screenHeight;

    private int menuWidth;
    private int quarterMenuWidth;
    private boolean menuState = false;
    private boolean isFirst = true;
    private ObjectAnimator animatorOpen;
    private ObjectAnimator animatorClose;
    /**
     * frament组件
     */
    private Fragment notedTable;
    private Fragment emptyTable;
    private Fragment menu;
    private LinearLayout.LayoutParams menull;
    private LinearLayout.LayoutParams contentll;

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.isFirst = true;
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

        menuWidth = 2*(int)screenWidth/3;
        quarterMenuWidth = menuWidth/4;

    }

    /**
     * 子view应该放哪里
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.scrollTo(menuWidth, 0);
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

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_UP:
                int xScroll = getScrollX();
                //右拉，meun为close时，触发
                if (xScroll > menuWidth / 2 * 1.5 && !menuState) {
                    menuClose();
                }
                //左拉,menu为open时，触发
                else if (xScroll <= menuWidth / 2 * 1.5 && !menuState) {
                    menuOpen();
                } else if (xScroll <= menuWidth / 4 && menuState) {
                    menuOpen();
                } else if (xScroll > menuWidth / 4 && menuState) {
                    menuClose();
                }
                return true;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * menu的状态和动作
     */
    public void menuOpen(){
        /**
         * 动画实现缓慢滑动
         */
        animatorOpen = ObjectAnimator.ofInt(this, "scrollX", getScrollX(),0);
        animatorOpen.setDuration(300);
        animatorOpen.start();
        menuState = true;
        isFirst = false;
    }

    public void menuClose(){
        animatorClose = ObjectAnimator.ofInt(this, "scrollX", getScrollX(),menuWidth);
        animatorClose.setDuration(300);
        animatorClose.start();
        menuState = false;
    }
}
