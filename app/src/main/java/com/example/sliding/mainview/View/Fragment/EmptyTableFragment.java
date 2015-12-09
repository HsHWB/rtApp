package com.example.sliding.mainview.View.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.pull2refresh.view.Pull2RefreshListView;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.View.Adapter.ContentListAdapter;

/**
 * 空余餐桌fragment
 */
public class EmptyTableFragment extends Fragment {

    private Pull2RefreshListView emptyTableListView;
    private ContentListAdapter contentListAdapter;
    private View emptyTableView;
    private int j = 0;

    public EmptyTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        emptyTableView = inflater.inflate(R.layout.fragment_empty_table, container, false);
//        emptyTableListView = (Pull2RefreshListView) container
//                .findViewById(R.id.fragment_empty_table_listview);
//        contentListAdapter = new ContentListAdapter(getActivity());
//        emptyTableListView.setAdapter(contentListAdapter);
        System.out.println("j == "+j++);
        return emptyTableView;
    }


}
