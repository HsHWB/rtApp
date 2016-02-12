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
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.example.sliding.mainview.View.Adapter.EmptyTableListAdapter;
import com.example.sliding.mainview.View.Adapter.NotedTableAdapter;
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
 * 正在使用的餐桌fragment
 */
public class NotedTableFragment extends Fragment {

    private ArrayList<OrderTable> orderTableList;
    private int GET_ADAPTER_DATA = 0x123460;
    private View notedTableView;
    private NotedTableAdapter notedListAdapter;
    private ListViewForScrollView listViewForScrollView;
    public NotedTableFragment() {
        // Required empty public constructor
        orderTableList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        notedTableView = inflater.inflate(R.layout.fragment_noted_table, container, false);
        listViewForScrollView = (ListViewForScrollView) notedTableView
                .findViewById(R.id.fragment_note_table_listview);
        notedListAdapter = new NotedTableAdapter(getActivity());
        listViewForScrollView.setAdapter(notedListAdapter);
        listViewForScrollView.setOnItemClickListener(notedListAdapter);
        return notedTableView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url(
                String.format(Contant.GET_NOTED_TABLE, 1))
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
                notedListAdapter.setOrderTableList((ArrayList<OrderTable>) msg.obj);
                notedListAdapter.notifyDataSetChanged();
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
}
