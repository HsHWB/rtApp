package com.example.sliding.mainview.View.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sliding.mainview.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotedTableFragment extends Fragment {


    public NotedTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noted_table, container, false);
    }


}
