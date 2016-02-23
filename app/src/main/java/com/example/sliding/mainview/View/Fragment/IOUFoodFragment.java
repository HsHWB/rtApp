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
import com.example.sliding.mainview.Beans.Food;
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.example.sliding.mainview.View.Adapter.IOUDataTableAdapter;
import com.example.sliding.mainview.View.Adapter.IOUFoodAdapter;
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
 * Created by huehn on 16/2/22.
 */
public class IOUFoodFragment extends Fragment {

    private View iouFoodView;
    private ListViewForScrollView listViewForScrollView;
    private IOUFoodAdapter iouFoodAdapter;
    private ArrayList<Food> foodList;
    private ImageView newFoodImage;
    private boolean isFragmentExit = false;
    private int GET_ADAPTER_DATA = 0x123456;

    public IOUFoodFragment() {
        foodList = new ArrayList<>();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        iouFoodView = inflater.inflate(R.layout.fragment_ioufood, container, false);
        listViewForScrollView = (ListViewForScrollView) iouFoodView.findViewById(R.id.fragment_ioufood_listview);
        newFoodImage = (ImageView) iouFoodView.findViewById(R.id.fragment_ioufood_newfood);
        iouFoodAdapter = new IOUFoodAdapter(getActivity());
        listViewForScrollView.setAdapter(iouFoodAdapter);
        listViewForScrollView.setOnItemClickListener(iouFoodAdapter);
        newFoodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertNewTable.class);
                intent.putExtra("tag","insert");
                startActivity(intent);
            }
        });
        return iouFoodView;
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
                iouFoodAdapter.setFoodList((ArrayList<Food>) msg.obj);
                iouFoodAdapter.notifyDataSetChanged();
            }
        }
    };

    public void netWork(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(
                String.format(Contant.GET_TABLES, "Food"))
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("IOUFoodFragment onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                Food food;
                String htmlStr = response.body().string();
                JSONObject responseObject = JSON.parseObject(htmlStr);
                JSONArray resultArray;
                foodList.clear();
                if (responseObject.containsKey("result")) {
                    resultArray = responseObject.getJSONArray("result");
                    for (int i = 0; i < resultArray.size(); i++) {
                        food = new Food();
                        food.setFoodId(Integer.valueOf(resultArray.getJSONObject(i).getString("foodId")));
                        food.setFoodName(resultArray.getJSONObject(i).getString("foodName"));
                        food.setFoodPrice(resultArray.getJSONObject(i).getString("foodPrice"));
                        foodList.add(food);
                    }
                }
                Message message = new Message();
                message.what = GET_ADAPTER_DATA;
                message.obj = foodList;
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
