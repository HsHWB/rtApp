package com.example.sliding.mainview.View.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.pull2refresh.view.Pull2RefreshListView;
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.example.sliding.mainview.View.Adapter.EmptyTableListAdapter;
import com.example.sliding.mainview.View.CustomView.ListViewForScrollView;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 空余餐桌fragment
 */
public class EmptyTableFragment extends Fragment implements Pull2RefreshListView.OnLoadMoreListener,
Pull2RefreshListView.OnRefreshListener, Pull2RefreshListView.OnClickListener{

    private ListViewForScrollView emptyTableListView;
    private EmptyTableListAdapter tableListAdapter;
    private View emptyTableView;
    private ArrayList<OrderTable> orderTableList;
    private float screenHeight;
    private float screenWidth;
    private int j = 0;

    private int GET_ADAPTER_DATA = 0x123456;

    public EmptyTableFragment() {
        // Required empty public constructor
        orderTableList = new ArrayList<>();
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
        tableListAdapter = new EmptyTableListAdapter(getActivity());
        emptyTableListView.setAdapter(tableListAdapter);
        emptyTableListView.setOnItemClickListener(tableListAdapter);
        return emptyTableView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //创建okHttpClient对象
        super.onActivityCreated(savedInstanceState);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url(
                String.format(Contant.GET_EMPTY_TABLE, 0))
                        .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("emptyTableFragment onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                OrderTable orderTable;
                String htmlStr = response.body().string();
                JSONObject responseObject = JSON.parseObject(htmlStr);
                JSONArray resultArray;
                if (responseObject.containsKey("result")) {
                    resultArray = responseObject.getJSONArray("result");
                    for (int i = 0; i < resultArray.size(); i++) {
                        orderTable = new OrderTable();
                        orderTable.setIdtable((Integer) resultArray.getJSONObject(i).get("idtable"));
                        orderTable.setTableName((String) resultArray.getJSONObject(i).get("tableName"));
                        orderTable.setTableNum((Integer) resultArray.getJSONObject(i).get("tableNum"));
                        orderTable.setTableState((Integer) resultArray.getJSONObject(i).get("tableState"));
                        orderTableList.add(orderTable);
                    }
                }
                Message message = new Message();
                message.what = GET_ADAPTER_DATA;
                message.obj = orderTableList;
                adapterHandler.sendMessage(message);
//                System.out.println("orderTableList == " + orderTableList.size());
            }
        });
    }

    private Handler adapterHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_ADAPTER_DATA){
                tableListAdapter.setOrderTableList((ArrayList<OrderTable>) msg.obj);
                tableListAdapter.notifyDataSetChanged();
            }
        }
    };
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
