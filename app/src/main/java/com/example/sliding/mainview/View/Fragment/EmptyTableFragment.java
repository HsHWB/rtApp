package com.example.sliding.mainview.View.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.pull2refresh.view.Pull2RefreshListView;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.View.Adapter.ContentListAdapter;

import java.lang.reflect.Field;

/**
 * 空余餐桌fragment
 */
public class EmptyTableFragment extends Fragment implements Pull2RefreshListView.OnLoadMoreListener,
Pull2RefreshListView.OnRefreshListener, Pull2RefreshListView.OnClickListener{

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
        emptyTableListView = (Pull2RefreshListView) emptyTableView
                .findViewById(R.id.fragment_empty_table_listview);
        emptyTableListView.setCanLoadMore(true);
        emptyTableListView.setCanRefresh(true);
        emptyTableListView.setAutoLoadMore(true);
        emptyTableListView.setOnLoadListener(this);
        emptyTableListView.setOnRefreshListener(this);

        contentListAdapter = new ContentListAdapter(getActivity());
        emptyTableListView.setAdapter(contentListAdapter);
        return emptyTableView;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
