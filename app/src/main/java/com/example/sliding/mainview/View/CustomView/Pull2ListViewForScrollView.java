package com.example.sliding.mainview.View.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by bingoo on 2015/11/18.
 */
public class Pull2ListViewForScrollView extends PullToRefreshListView{

    private float x_1;
    private float x_2;
    private float y_1;
    private float y_2;
    private boolean state = true;

    public Pull2ListViewForScrollView(Context pContext) {
        super(pContext);
    }

    public Pull2ListViewForScrollView(Context pContext, AttributeSet pAttrs) {
        super(pContext, pAttrs);
    }

//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        float yBegin = ev.getY();
//        float xBegin = ev.getX();
//
//        switch (ev.getAction()) {
//            // 当手指触摸listview时，让父控件交出ontouch权限,不能滚动
//            case MotionEvent.ACTION_DOWN:
//                x_1 = xBegin;
//                y_1 = yBegin;
//                setParentScrollAble(false);
//            case MotionEvent.ACTION_MOVE:
//                x_2 = ev.getX();
//                y_2 = ev.getY();
//                if (Math.abs(y_2 - y_1) < 30 && Math.abs(x_2 - x_1) > 30 && state){
//                    setParentScrollAble(true);
//                }else if (Math.abs(y_2 - y_1) >= 30){
//                    setParentScrollAble(false);
//                    state = false;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                // 当手指松开时，让父控件重新获取onTouch权限
//                setParentScrollAble(true);
//                state = true;
//                break;
//
//        }
//        return super.onTouchEvent(ev);
//    }
//
//    // 设置父控件是否可以获取到触摸处理权限
//    private void setParentScrollAble(boolean flag) {
//        getParent().requestDisallowInterceptTouchEvent(!flag);
//    }

}
