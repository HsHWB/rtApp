package com.example.sliding.mainview.View.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sliding.mainview.Beans.Item;
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.example.sliding.mainview.View.Adapter.ItemSelectAdapter;
import com.example.sliding.mainview.View.CustomView.ListViewForScrollView;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by huehn on 16/2/12.
 */
public class ShowItemFragment extends Fragment {
    private ArrayList<Item> itemList;
    private Item item;
    private View showItemView;
    private ItemSelectAdapter itemSelectAdapter;
    private ListViewForScrollView listViewForScrollView;
    private Button orderButton;
    private int tableId;
    private int GET_ADAPTER_DATA = 0x123180;
    public ShowItemFragment() {
        itemList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showItemView = inflater.inflate(R.layout.fragment_showitem, container, false);
        Bundle data = getArguments();
        tableId = data.getInt("tableId");
        orderButton = (Button) showItemView.findViewById(R.id.fragment_showitem_okpay);
        listViewForScrollView = (ListViewForScrollView) showItemView.findViewById(R.id.fragment_showitem_listview);
        itemSelectAdapter = new ItemSelectAdapter(getActivity());
//        listViewForScrollView.setAdapter(itemSelectAdapter);
        return showItemView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url(
                String.format(Contant.GET_ITEMS, tableId))
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("showItemFragment onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                String htmlStr = response.body().string();
                JSONObject responseObject = JSON.parseObject(htmlStr);
                JSONArray resultArray;
                if (responseObject.containsKey("result")) {
                    resultArray = responseObject.getJSONArray("result");
                    for (int i = 0; i < resultArray.size(); i++) {
                        item = new Item();
                        item.setItemName(resultArray.getJSONObject(i).getString("itemName"));
                        item.setTableId(resultArray.getJSONObject(i).getInteger("tableId"));
                        item.setFoodId(resultArray.getJSONObject(i).getInteger("foodId"));
                        item.setFoodNum(resultArray.getJSONObject(i).getInteger("foodNum"));
                        item.setFoodPrice(Integer.valueOf(resultArray.getJSONObject(i).getString("foodPrice")));
                        item.setPrice(resultArray.getJSONObject(i).getString("price"));
                        itemList.add(item);
                    }
                }
                Message message = new Message();
                message.what = GET_ADAPTER_DATA;
                message.obj = itemList;
                adapterHandler.sendMessage(message);
            }
        });
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient mOkHttpClient = new OkHttpClient();
                //创建一个Request
                Request request = new Request.Builder().url(
                        String.format(Contant.PAY, tableId))
                        .build();
                Call call = mOkHttpClient.newCall(request);
                //请求加入调度
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        System.out.println("showItemFragment onFailure== " + request.body());
                        getActivity().finish();
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {
                        getActivity().finish();
                    }
                });
            }
        });
    }

    private Handler adapterHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_ADAPTER_DATA) {
//                System.out.println(((ArrayList<Item>)msg.obj).size());
                itemSelectAdapter.setList((ArrayList<Item>) msg.obj);
                listViewForScrollView.setAdapter(itemSelectAdapter);
//                itemSelectAdapter.notifyDataSetChanged();
            }
        }
    };
}
