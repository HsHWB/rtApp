package com.example.sliding.mainview.View.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sliding.mainview.Activity.InsertNewTable;
import com.example.sliding.mainview.Beans.Item;
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.example.sliding.mainview.View.Adapter.IOUDataTableAdapter;
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
 * Created by huehn on 16/2/21.
 * 注意，正在使用中的台，不支持修改台名和id
 * 正在使用的菜式，不支持修改价格和名字
 */
public class IOUDataFragment extends Fragment{

    private View iouDataView;
    private ListViewForScrollView listViewForScrollView;
    private IOUDataTableAdapter iouDataTableAdapter;
    private ArrayList<OrderTable> orderTableList;
    private ImageView newTableImage;
    private boolean isFragmentExit = false;
    private int GET_ADAPTER_DATA = 0x123456;

    public IOUDataFragment() {
        orderTableList = new ArrayList<>();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        iouDataView = inflater.inflate(R.layout.fragment_ioudata, container, false);
        listViewForScrollView = (ListViewForScrollView) iouDataView.findViewById(R.id.fragment_ioudata_listview);
        newTableImage = (ImageView) iouDataView.findViewById(R.id.fragment_ioudata_newtable);
        iouDataTableAdapter = new IOUDataTableAdapter(getActivity());
        listViewForScrollView.setAdapter(iouDataTableAdapter);
        listViewForScrollView.setOnItemClickListener(iouDataTableAdapter);
        newTableImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertNewTable.class);
                intent.putExtra("tag","insert");
                startActivity(intent);
            }
        });
        return iouDataView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        netWork();
    }

    private Handler adapterHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_ADAPTER_DATA){
                iouDataTableAdapter.setTablesList((ArrayList<OrderTable>) msg.obj);
                iouDataTableAdapter.notifyDataSetChanged();
            }
        }
    };

    public void netWork(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url(
                String.format(Contant.GET_TABLES, "OrderTable"))
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("IOUDataFragment onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                OrderTable orderTable;
                String htmlStr = response.body().string();
                JSONObject responseObject = JSON.parseObject(htmlStr);
                JSONArray resultArray;
                orderTableList.clear();
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
            }
        });
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
    public void onResume() {
        super.onResume();
        if (isFragmentExit){
            netWork();
        }
        isFragmentExit = true;
    }
}