package com.example.sliding.mainview.View.Fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.View.Adapter.EmptyTableListAdapter;
import com.example.sliding.mainview.View.CustomView.ListViewForScrollView;
import com.example.sliding.mainview.View.CustomView.SlidingMenu;

import java.lang.reflect.Field;

/**
 * 主要内容fragment
 */
public class ContentFragment extends Fragment {

    private boolean isFirst = true;
    private View contentView;
    private EmptyTableFragment emptyTableFragment;
    private NotedTableFragment notedTableFragment;
    private ListViewForScrollView emptyTableListView;
    private EmptyTableListAdapter tableListAdapter;

    private Button emptyTableButton;
    private Button notedTableButton;
    private ImageView menuImage;
    private Boolean isEmptyFragment;
    private Boolean isNotedFragmentFirst;

    private FragmentManager fm;
    private FragmentTransaction transaction;

    private SlidingMenu slidingMenu;
    private boolean isMenuOpen;

    private int i = 0;

    public ContentFragment(){
        emptyTableFragment = new EmptyTableFragment();
        notedTableFragment = new NotedTableFragment();
        isMenuOpen = false;
    }

    public void setSlidingMenu(SlidingMenu slidingMenu){
        this.slidingMenu = slidingMenu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.layout_content, container, false);
        init();
        return contentView;
    }

    public void replaceView(){
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_content_layout, emptyTableFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        isEmptyFragment = true;
        isNotedFragmentFirst = true;
    }

    private void init(){
        emptyTableButton = (Button) contentView.findViewById(R.id.fragment_empty_table_fragmentempty);
        notedTableButton = (Button) contentView.findViewById(R.id.fragment_empty_table_fragmentnoted);
        menuImage = (ImageView) contentView.findViewById(R.id.fragment_empty_table_showmenu);
        emptyTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmptyFragment) {
                    transaction = fm.beginTransaction();
                    transaction.hide(notedTableFragment);
                    transaction.show(emptyTableFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    isEmptyFragment = true;
                }

            }
        });

        notedTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyFragment ) {
                    if (isNotedFragmentFirst) {
                        transaction = fm.beginTransaction();
                        transaction.hide(emptyTableFragment);
                        transaction.add(R.id.fragment_content_layout, notedTableFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        isEmptyFragment = false;
                        isNotedFragmentFirst = false;
                    }else {
                        transaction = fm.beginTransaction();
                        transaction.hide(emptyTableFragment);
                        transaction.show(notedTableFragment);
                        transaction.commit();
                        isEmptyFragment = false;
                    }
                }
            }
        });

        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingMenu.isMenuOpen()){
                    slidingMenu.menuClose();
                }else if (!slidingMenu.isMenuOpen()) {
                    slidingMenu.menuOpen();
                }
            }
        });
    }

    /**
     * 父activity生命周期更新数据
     */
    public void netWork(){
        emptyTableFragment.netWork();
        /**
         * 当没加载notedTableFragment时，不获取它的数据
         */
        if (!isNotedFragmentFirst) {
            notedTableFragment.netWork();
        }
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
