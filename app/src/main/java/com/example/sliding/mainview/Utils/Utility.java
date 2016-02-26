package com.example.sliding.mainview.Utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.sliding.mainview.View.CustomView.ListViewForScrollView;

/**
 * Created by huehn on 16/2/26.
 */
public class Utility {
    public static void setListViewHeightBasedOnChildren(ListViewForScrollView listView, float screenHeight) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
        // pre-condition
            return;
        }


        int totalHeight = 0;
        int len = listAdapter.getCount();
        System.out.println("len == "+listAdapter.getCount());
        for (int i = 0; i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
            System.out.println("proportion == "+(float)listItem.getMeasuredHeight()/(screenHeight/10));
        }
//        totalHeight = len * (int)screenHeight/10;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height = totalHeight + 200;
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}