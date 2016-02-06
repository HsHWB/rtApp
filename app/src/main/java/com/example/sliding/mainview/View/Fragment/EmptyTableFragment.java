package com.example.sliding.mainview.View.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.text.style.LineHeightSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dy.pull2refresh.view.Pull2RefreshListView;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.example.sliding.mainview.View.Adapter.TableListAdapter;
import com.example.sliding.mainview.View.CustomView.ListViewForScrollView;

import java.lang.reflect.Field;

/**
 * 空余餐桌fragment
 */
public class EmptyTableFragment extends Fragment implements Pull2RefreshListView.OnLoadMoreListener,
Pull2RefreshListView.OnRefreshListener, Pull2RefreshListView.OnClickListener{

    private ListViewForScrollView emptyTableListView;
    private TableListAdapter tableListAdapter;
    private View emptyTableView;
    private float screenHeight;
    private float screenWidth;
    private int j = 0;

    public EmptyTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        emptyTableView = inflater.inflate(R.layout.fragment_empty_table, container, false);
        screenHeight = WindowsUtils.getWindowHeight(getActivity());
        screenWidth = WindowsUtils.getWindowWidth(getActivity());
        emptyTableListView = (ListViewForScrollView) emptyTableView
                .findViewById(R.id.fragment_empty_table_listview);

        tableListAdapter = new TableListAdapter(getActivity());
        emptyTableListView.setAdapter(tableListAdapter);
        emptyTableListView.setOnItemClickListener(tableListAdapter);
//        RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(
//                7*(int)screenWidth/8,
//                ViewGroup.LayoutParams.MATCH_PARENT
//        );
//        ll.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        emptyTableView.setLayoutParams(ll);
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
