package com.example.sliding.mainview.View.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dy.pull2refresh.view.Pull2RefreshListView;
import com.example.sliding.mainview.R;

/**
 * 主要内容fragment
 */
public class ContentFragment extends Fragment {

    private boolean isFirst = true;
    private View contentView;
    private Pull2RefreshListView emptyTableListView;


    public ContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (isFirst){
            /**
             * 第一次打开这个app时,显示空余餐桌fragment
             */
            contentView = inflater.inflate(R.layout.fragment_empty_table, container, false);
            emptyTableListView = (Pull2RefreshListView) emptyTableListView
                    .findViewById(R.id.fragment_empty_table_listview);
            return contentView;
        }

        return inflater.inflate(R.layout.fragment_content, container, false);
    }

}
