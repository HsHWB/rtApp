package com.example.sliding.mainview.View.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.View.Adapter.MenuListViewAdapter;
import com.example.sliding.mainview.View.CustomView.ListViewForScrollView;

/**
 * Created by Administrator on 2016/2/2.
 */
public class OrderFragment extends Fragment {

    private View orderView;
    private ListViewForScrollView orderListView;
    private MenuListViewAdapter menuListViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        orderView = inflater.inflate(R.layout.order_listview, container, false);
        orderListView = (ListViewForScrollView) orderView.findViewById(R.id.menu_listview_listview);
        menuListViewAdapter = new MenuListViewAdapter(getActivity());
        orderListView.setAdapter(menuListViewAdapter);
        orderListView.setOnItemClickListener(menuListViewAdapter);

        return orderView;
    }
}
