package com.example.sliding.mainview.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import java.lang.reflect.Field;

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
        System.out.println("123123123");
        return contentView;
    }


    public void replaceView(){
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_content_layout, emptyTableFragment);
        transaction.commit();
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

}
