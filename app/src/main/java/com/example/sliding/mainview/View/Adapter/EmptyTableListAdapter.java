package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sliding.mainview.Activity.OrderAcitivity;
import com.example.sliding.mainview.Beans.MenuItem;
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.Beans.Table;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 菜单的adapter
 */
public class EmptyTableListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private LayoutInflater inflater;
    private Context mContext;
    private float screenWidth;
    private float screenHeight;
    private ArrayList<OrderTable> orderTableList = new ArrayList<>();

    public EmptyTableListAdapter(Context context){
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.screenHeight = WindowsUtils.getWindowHeight(mContext);
        this.screenWidth = WindowsUtils.getWindowWidth(mContext);
    }

    public void setOrderTableList(ArrayList<OrderTable> orderTableList){
        this.orderTableList = orderTableList;
    }
    @Override
    public int getCount() {
        return orderTableList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_empty_table_item, null);

            viewHolder.tableName = (TextView) convertView.findViewById(R.id.empty_table_item_tableName);
            viewHolder.tableNum = (TextView) convertView.findViewById(R.id.empty_table_item_tableId);
            viewHolder.tableStatus = (TextView) convertView.findViewById(R.id.empty_table_item_state);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (orderTableList.size() > 0) {
            viewHolder.tableName.setText(this.orderTableList.get(position).getTableName());
            viewHolder.tableNum.setText(String.valueOf(this.orderTableList.get(position).getTableNum()));
        }

        AbsListView.LayoutParams al = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)screenHeight/10);

        convertView.setLayoutParams(al);
        return convertView;
    }


    class ViewHolder{
        TextView tableName;
        TextView tableNum;
        TextView tableStatus;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("click position == " + position);
        Intent intent = new Intent(mContext, OrderAcitivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putSerializable("orderTable", orderTableList.get(position));
        intent.putExtras(bundle);
        mContext.startActivity(intent);

//        //创建okHttpClient对象
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        //创建一个Request
//        Request request = new Request.Builder().url(
//                String.format("http://192.168.1.102:8080/rtService/test/login?name=%s&password=%s","斌","123"))
//                .build();
//        //new call
//        Call call = mOkHttpClient.newCall(request);
//        //请求加入调度
//        call.enqueue(new Callback()
//        {
//            @Override
//            public void onFailure(Request request, IOException e)
//            {
//                System.out.println(request.body());
//            }
//
//            @Override
//            public void onResponse(final Response response) throws IOException
//            {
//                String htmlStr =  response.body().string();
//                System.out.println(htmlStr);
//            }
//        });
        
//        List<Table> list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Table table = new Table();
//            table.setTableName("123");
//            list.add(table);
//        }
//        String url = "http://172.17.0.55:8080/rt/test/setTableState";
//
//        OkHttpClient client = new OkHttpClient();
//        String str = com.alibaba.fastjson.JSON.toJSONString(list);
//        System.out.println("json == "+str);
//        RequestBody body = new FormEncodingBuilder().add("value", str).build();
//        Request request = new Request.Builder().url(url).post(body).build();
//        client.newCall(request).enqueue(new Callback(){
//
//            @Override
//            public void onFailure(Request request, IOException e) {
//                System.out.println("onFailure == "+request.body());
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                System.out.println("onResponse == "+response.body());
//            }
//        });

//        new Thread() {
//            @Override
//            public void run() {
//                HttpGet getMethod = new HttpGet("http://172.17.0.:8080/rt/test/getData");
//
//                HttpClient httpClient = new DefaultHttpClient();
//
//                try {
//                    HttpResponse response = httpClient.execute(getMethod); //发起GET请求
//                    System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
//
//                } catch (ClientProtocolException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }.start();


    }
}