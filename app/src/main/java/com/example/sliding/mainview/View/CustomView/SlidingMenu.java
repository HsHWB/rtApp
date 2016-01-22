package com.example.sliding.mainview.View.CustomView;

import android.animation.ObjectAnimator;

import android.content.Context;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;


/**
 * Created by huehn on 15/11/23.
 */
public class SlidingMenu extends HorizontalScrollView {

    /**
     * 还是写两个addview然后在view里面代替fragment吧
     */

    private Context mContext;

    private float screenWidth;
    private float screenHeight;

    private int menuWidth;
    private boolean menuState = false;
    private ObjectAnimator animatorOpen;
    private ObjectAnimator animatorClose;
    private ViewGroup menu;

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    /**
     * 计算子view的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenHeight = WindowsUtils.getWindowHeight(mContext);
        screenWidth = WindowsUtils.getWindowWidth(mContext);
        menuWidth = 2*(int)screenWidth/3;

        menu = (ViewGroup) ((LinearLayout)getChildAt(0)).getChildAt(0);
//        System.out.println("menu == "+menu);

    }

    /**
     * 子view应该放哪里
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.scrollTo(menuWidth, 0);
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

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / 200;
        menu.setTranslationX(200 * scale);
    }

    public void setMenuView(FrameLayout menu){
//        this.menu = menu;
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
        /**
         * 通知fragment
         */

    }

    public void menuClose(){
        animatorClose = ObjectAnimator.ofInt(this, "scrollX", getScrollX(),menuWidth);
        animatorClose.setDuration(300);
        animatorClose.start();
        menuState = false;
    }

}
