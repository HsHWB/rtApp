package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sliding.mainview.Beans.Food;
import com.example.sliding.mainview.Beans.Item;
import com.example.sliding.mainview.Beans.MenuItem;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.example.sliding.mainview.Utils.JsonUtils;
import com.example.sliding.mainview.View.Adapter.ItemSelectAdapter;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import okio.Buffer;

public class ItemSelectActivity extends Activity {

    private ArrayList<Item> itemList;
    private Item item;
    private ArrayList<MenuItem> menuItemList;
    private ListView listView;
    private ItemSelectAdapter itemSelectAdapter;

    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_select);
        listView = (ListView) findViewById(R.id.item_select_listview);
        okButton = (Button) findViewById(R.id.item_select_ok);
        menuItemList = (ArrayList<MenuItem>) this.getIntent().getSerializableExtra("list");
        itemList = new ArrayList<>();
        itemSelectAdapter = new ItemSelectAdapter(this);
        for (int i = 0; i < menuItemList.size(); i++){
            item = new Item();
            item.setItemName(menuItemList.get(i).getItemName());
            item.setTableId(menuItemList.get(i).getTableId());
            item.setFoodId(menuItemList.get(i).getFoodId());
            item.setFoodNum(menuItemList.get(i).getItemNum());
            item.setFoodPrice(menuItemList.get(i).getMoney());
            item.setPrice(String.valueOf(item.getFoodNum() * item.getFoodPrice()));
            itemList.add(item);
        }
        itemSelectAdapter.setList(itemList);
        listView.setAdapter(itemSelectAdapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                String url = Contant.ORDER;
                String list = new JsonUtils(itemList).getJsonObject().toJSONString();
                RequestBody body = new FormEncodingBuilder().add("order", list).build();
//                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
                Request request = new Request.Builder().url(url).post(body).build();
                client.newCall(request).enqueue(new Callback(){

                    @Override
                    public void onFailure(Request request, IOException e) {
                        System.out.println("onFailure == "+request.body());
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        System.out.println("onResponse == " + response.body());
                        finish();
                        Intent intent = new Intent();
                        intent.setAction(OrderAcitivity.BROADCAST_ACTION);
                        intent.putExtra("activity", "finish activity");
                        sendBroadcast(intent);
                    }
                });
            }
        });
//        okButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OkHttpClient client = new OkHttpClient();
//                String url = Contant.ORDER;
//                String list = new JsonUtils(itemList).getJsonObject().toJSONString();
////                RequestBody body = new FormEncodingBuilder().add("order", list).build();
//                //创建一个Request
////                String u =
//                Request request = null;
//                try {
//                    request = new Request.Builder().url(
//                            String.format(url, URLEncoder.encode(list, "GBK")))
//                            .build();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                //new call
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//
//                    @Override
//                    public void onFailure(Request request, IOException e) {
//                        System.out.println("onFailure == " + request.body());
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Response response) throws IOException {
//                        System.out.println("onResponse == " + response.body());
//                        finish();
//                        Intent intent = new Intent();
//                        intent.setAction(OrderAcitivity.BROADCAST_ACTION);
//                        intent.putExtra("activity", "finish activity");
//                        sendBroadcast(intent);
//                    }
//                });
//            }
//        });
    }
}
