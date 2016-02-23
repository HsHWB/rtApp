package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sliding.mainview.Activity.InsertNewFood;
import com.example.sliding.mainview.Activity.InsertNewTable;
import com.example.sliding.mainview.Beans.Food;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.example.sliding.mainview.View.CustomView.CustomDialog;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/23.
 */
public class IOUFoodAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private Context mContext;
    private ArrayList<Food> foodList = null;
    private LayoutInflater inflater;
    private float screenWidth;
    private float screenHeigh;
    private int GET_ADAPTER_DATA = 0x123456;

    public IOUFoodAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.screenHeigh = WindowsUtils.getWindowHeight(mContext);
        this.screenWidth = WindowsUtils.getWindowWidth(mContext);
    }

    public void setFoodList(ArrayList<Food> foodList){
        this.foodList = foodList;
    }

    @Override
    public int getCount() {
        if (foodList == null){
            return 0;
        }else {
            return foodList.size();
        }
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
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_ioufood_item, null);
            viewHolder.foodName = (TextView) convertView.findViewById(R.id.fragment_ioufood_item_foodname);
            viewHolder.deleteItem = (ImageView) convertView.findViewById(R.id.fragment_ioufood_item_reduceimg);
            viewHolder.foodPrice = (TextView) convertView.findViewById(R.id.fragment_ioufood_item_price);
            viewHolder.deleteItem.setOnClickListener(new DeleteButtonListener(mContext, position));
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.foodName.setText(foodList.get(position).getFoodName());
        viewHolder.foodid = foodList.get(position).getFoodId();
        viewHolder.foodPrice.setText(foodList.get(position).getFoodPrice());

        AbsListView.LayoutParams al = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)screenHeigh/10);

        convertView.setLayoutParams(al);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, InsertNewFood.class);
        intent.putExtra("tag", "update");
        intent.putExtra("foodId", foodList.get(position).getFoodId());
        intent.putExtra("foodName", foodList.get(position).getFoodName());
        intent.putExtra("foodPrice",foodList.get(position).getFoodPrice());
        mContext.startActivity(intent);
    }

    class ViewHolder{
        TextView foodName;
        TextView foodPrice;
        ImageView deleteItem;
        int foodid;
    }

    private void deleteTable(int foodId, final DialogInterface customDialog){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url("")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                customDialog.dismiss();
                System.out.println("IOUFoodAdapter onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                Message message = new Message();
                message.what = GET_ADAPTER_DATA;
                adapterHandler.sendMessage(message);
                customDialog.dismiss();
            }
        });
    }
    private Handler adapterHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_ADAPTER_DATA){
//                tableListAdapter.setOrderTableList((ArrayList<OrderTable>) msg.obj);
                IOUFoodAdapter.this.notifyDataSetChanged();
                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
            }
        }
    };
    class DeleteButtonListener implements View.OnClickListener{

        private int position;
        private Context mContext;

        public DeleteButtonListener(Context mContext, int position) {
            this.mContext = mContext;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
            builder.setMessage("确定要删除菜名为:"+"\""+foodList.get(position).getFoodName()+"\""+"这款菜吗？"
                    +"\nid为"+foodList.get(position).getFoodId());
            builder.setTitle("提示");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //设置你的操作事项
                    /**
                     * 在数据中删除这个项
                     */
                    deleteTable(foodList.get(position).getFoodId(), dialog);
                    foodList.remove(position);
                }
            });

            builder.setNegativeButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.create().show();
        }
    }
}
