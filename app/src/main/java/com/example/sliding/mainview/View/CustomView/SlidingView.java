package com.example.sliding.mainview.View.CustomView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.sliding.mainview.View.Adapter.ContentListAdapter;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;

/**
 * Created by bingoo on 2015/11/3.
 */
public class SlidingView extends HorizontalScrollView {

    private Context mContext;
    private ViewGroup leftItemView;
    private ViewGroup contentView;
    private RelativeLayout slidingRelative;
    private LinearLayout mainLinear;
    private LinearLayout viewLinear;

    private ListViewForScrollView mListView;
    private View slidingCoverView;
    private ObjectAnimator animatorOpen;
    private ObjectAnimator animatorClose;

    private float screenWidth;
    private float screenHeight;
    private int menuWidth;
    private boolean menuState = false;
    private boolean isFirst = true;

    public SlidingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.screenHeight = WindowsUtils.getWindowHeight(context);
        this.screenWidth = WindowsUtils.getWindowWidth(context);
        this.isFirst = true;
    }

    /**
     * 定义view的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获取view对象
         */
        slidingRelative = (RelativeLayout)this.getChildAt(0);
        mainLinear = (LinearLayout) slidingRelative.getChildAt(0);
        leftItemView = (ViewGroup) mainLinear.getChildAt(0);
        contentView = (ViewGroup) mainLinear.getChildAt(1);
        viewLinear = (LinearLayout) slidingRelative.getChildAt(1);
        slidingCoverView = viewLinear.getChildAt(0);

        menuWidth = leftItemView.getWidth();
        mListView = (ListViewForScrollView) contentView.findViewById(R.id.content_listview);
        mListView.setAdapter(new ContentListAdapter(mContext));

        /**
         * contentView宽度设置为屏幕宽度
         */
        LinearLayout.LayoutParams contentViewll = new LinearLayout.LayoutParams(
                (int) this.screenWidth,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        contentView.setLayoutParams(contentViewll);

        /**
         * 覆盖view
         */
        RelativeLayout.LayoutParams coverViewll = new RelativeLayout.LayoutParams(
                (int) screenWidth - menuWidth,
                (int)(screenHeight)
        );
        viewLinear.setLayoutParams(coverViewll);
        viewLinear.setX(menuWidth);

        viewLinear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuState) {
                    menuClose();
                }
            }
        });


        contentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuState) {
                    menuClose();
                }
            }
        });


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 定义在父布局中的位置
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isFirst) {
            menuClose();
        }
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * action_up:
     *
     * @param ev
     * @return
     */
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
     * 滑动过程中的处理
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / menuWidth;
        leftItemView.setTranslationX(menuWidth * scale);
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

        isFirst = false;
        mListView.setEnabled(false);
        menuState = true;
        //        viewLinear.setVisibility(View.VISIBLE);
    }

    public void menuClose(){
        animatorClose = ObjectAnimator.ofInt(this, "scrollX", getScrollX(),menuWidth);
        animatorClose.setDuration(300);
        animatorClose.start();

        mListView.setEnabled(true);
        menuState = false;
        //        viewLinear.setVisibility(View.GONE);
    }
}
