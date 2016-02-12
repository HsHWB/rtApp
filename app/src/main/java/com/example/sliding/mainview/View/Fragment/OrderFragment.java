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
import com.example.sliding.mainview.Beans.Food;
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.example.sliding.mainview.View.Adapter.OrderListViewAdapter;
import com.example.sliding.mainview.View.CustomView.ListViewForScrollView;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/2.
 */
public class OrderFragment extends Fragment {

    private View orderView;
    private ListViewForScrollView orderListView;
    private OrderListViewAdapter orderListViewAdapter;
    private ArrayList<Food> foodList;
    private OrderTable orderTable;
    private int GET_ADAPTER_DATA = 0x12345;

    public OrderFragment() {
        this.foodList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        orderView = inflater.inflate(R.layout.order_listview, container, false);
        orderListView = (ListViewForScrollView) orderView.findViewById(R.id.order_listview_listview);
        orderListViewAdapter = new OrderListViewAdapter(getActivity());
        orderListView.setAdapter(orderListViewAdapter);


        return orderView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url(
                String.format(Contant.GET_TABLES, "Food"))
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("orderFragment onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                Food food;
                String htmlStr = response.body().string();
                JSONObject responseObject = JSON.parseObject(htmlStr);
                JSONArray resultArray;
                if (responseObject.containsKey("result")) {
                    resultArray = responseObject.getJSONArray("result");
                    for (int i = 0; i < resultArray.size(); i++) {
                        food = new Food();
                        food.setFoodPrice(resultArray.getJSONObject(i).getString("foodPrice"));
                        food.setFoodName(resultArray.getJSONObject(i).getString("foodName"));
                        food.setFoodId(resultArray.getJSONObject(i).getInteger("foodId"));
                        foodList.add(food);
                    }
                }
                Message message = new Message();
                message.what = GET_ADAPTER_DATA;
                message.obj = foodList;
                orderHandler.sendMessage(message);
//                System.out.println("orderTableList == " + orderTableList.size());
            }
        });
    }

    private Handler orderHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_ADAPTER_DATA) {
                orderListViewAdapter.setOrderList(foodList, orderTable);
                orderListViewAdapter.notifyDataSetChanged();
            }
        }
    };
    /**
     * 获取要开台的table名字等待
     */
    public void setOrderTable(OrderTable orderTable){
        this.orderTable = orderTable;
    }

    public OrderListViewAdapter getOrderListViewAdapter(){
        return orderListViewAdapter;
    }
}
