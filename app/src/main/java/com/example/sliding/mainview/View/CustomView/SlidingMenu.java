package com.example.sliding.mainview.View.CustomView;

import android.animation.ObjectAnimator;

import android.app.Activity;
import android.content.Context;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
    private ViewGroup content;

    private boolean isFirst = true;
    private boolean isNewFragment = false;

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
        content = (ViewGroup) ((LinearLayout) getChildAt(0)).getChildAt(1);

    }

    /**
     * 子view应该放哪里
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        this.scrollTo(menuWidth, 0);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (isFirst){
            /**
             * 第一次打开app
             */
            this.scrollTo(menuWidth, 0);
            isFirst = false;
        }else{
            /**
             * add了一个新的fragment，快速移动
             */
            if (getIsNewFragment()){
                this.scrollTo(menuWidth, 0);
                setIsNewFragment(false);
            } else {
                /**
                 * 其他操作，缓慢移动
                 */
                this.menuClose();
            }
        }
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
//        float scale = l * 1.0f / 200;
//        menu.setTranslationX(200 * scale);
        float scale = l * 1.0f / menuWidth;//1、如果content从左向右即显示侧栏滑动的话，menuWidth会越来越小，scale会越来越大
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;

        menu.setScaleX(leftScale);
        menu.setScaleY(leftScale);
        menu.setAlpha(0.4f + 0.4f * (1 - scale));//2、若scale越来越大，则表达式越来越接近 0.6 ，透明度越来越小
        menu.setTranslationX(menuWidth * scale * 0.6f);

        content.setPivotX(0);
        content.setPivotY(content.getHeight() / 2);
        content.setScaleX(rightScale);
        content.setScaleY(rightScale);
    }

    public void setMenuView(FrameLayout menu){
//        this.menu = menu;
    }

    public void setIsNewFragment(boolean isNewFragment){
        this.isNewFragment = isNewFragment;
    }

    public boolean getIsNewFragment(){
        return this.isNewFragment;
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

    /**
     * 供其他类获得menu是否打开的信息
     * @return
     */
    public boolean isMenuOpen(){
        return menuState;
    }

}
