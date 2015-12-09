package com.example.sliding.mainview.View.Fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dy.pull2refresh.view.Pull2RefreshListView;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.View.Adapter.ContentListAdapter;

/**
 * 主要内容fragment
 */
public class ContentFragment extends Fragment {

    private boolean isFirst = true;
    private View contentView;
    private EmptyTableFragment emptyTableFragment;
    private Pull2RefreshListView emptyTableListView;
    private ContentListAdapter contentListAdapter;

    private FragmentManager fm;
    private FragmentTransaction transaction;

    private int i = 0;

    public ContentFragment(){
        emptyTableFragment = new EmptyTableFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.layout_content, container, false);
//        if (isFirst){
//            /**
//             * 第一次打开这个app时,显示空余餐桌fragment
//             */
//            transaction = fm.beginTransaction();
//            transaction.replace(contentView.findViewById(R.id.fragment_content_layout)
//                    .getId(), emptyTableFragment);
//            transaction.commit();
//            isFirst = false;
//        }
//        getChildFragmentManager().beginTransaction().replace(R.id.fragment_content_layout, emptyTableFragment).commit();
        System.out.println(i++);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (savedInstanceState == null){
//            transaction = fm.beginTransaction();
//            transaction.replace(contentView.findViewById(R.id.fragment_content_layout)
//                    .getId(), emptyTableFragment);
//            transaction.commit();
//            getChildFragmentManager().beginTransaction().replace(contentView.findViewById(R.id.fragment_content_layout)
//            .getId(), emptyTableFragment).commit();
        }

        super.onViewCreated(view, savedInstanceState);
    }

    public void setFragmentManager(FragmentManager fm){
        this.fm = fm;
    }
}
