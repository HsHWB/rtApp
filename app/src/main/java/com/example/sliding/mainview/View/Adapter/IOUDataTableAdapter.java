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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sliding.mainview.Activity.InsertNewTable;
import com.example.sliding.mainview.Beans.OrderTable;
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
 * Created by huehn on 16/2/21.
 */
public class IOUDataTableAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private ArrayList<OrderTable> tablesList;
    private Context mContext;
    private LayoutInflater inflater;
    private float screenWidth;
    private float screenHeigh;
    private int GET_ADAPTER_DATA = 0x123456;

    public void setTablesList(ArrayList<OrderTable> tablesList){
        this.tablesList = tablesList;
    }

    public IOUDataTableAdapter(Context mContext){
        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.screenHeigh = WindowsUtils.getWindowHeight(mContext);
        this.screenWidth = WindowsUtils.getWindowWidth(mContext);
    }

    @Override
    public int getCount() {
        if (tablesList == null){
            return 0;
        }else {
            return tablesList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_ioudata_item, null);
            viewHolder.tableName = (TextView) convertView.findViewById(R.id.fragment_ioudata_item_tablename);
            viewHolder.deleteItem = (ImageView) convertView.findViewById(R.id.fragment_ioudata_item_reduceimg);
            viewHolder.tableStateText = (TextView) convertView.findViewById(R.id.fragment_ioudata_item_state);
            viewHolder.deleteItem.setOnClickListener(new DeleteButtonListener(mContext, position));
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tableName.setText(tablesList.get(position).getTableName());
        viewHolder.tableid = tablesList.get(position).getIdtable();
        viewHolder.tableState = tablesList.get(position).getTableState();
//        System.out.println("table  "+tablesList.get(position).getTableName()+" state 为:"+
//        tablesList.get(position).getTableState());
        if (tablesList.get(position).getTableState() == 1){
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            viewHolder.tableStateText.setText("使用中");
        }else {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.whitesmoke));
            viewHolder.tableStateText.setText("空闲");
        }

        AbsListView.LayoutParams al = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)screenHeigh/10);

        convertView.setLayoutParams(al);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, InsertNewTable.class);
        intent.putExtra("tag", "update");
        intent.putExtra("idtable", tablesList.get(position).getIdtable());
        intent.putExtra("tableName", tablesList.get(position).getTableName());
        intent.putExtra("tableNum",tablesList.get(position).getTableNum());
        mContext.startActivity(intent);
    }


    class ViewHolder{
        TextView tableName;
        TextView tableStateText;
        ImageView deleteItem;
        int tableid;
        int tableState;
    }
    private void deleteTable(int tableId, final DialogInterface customDialog){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url(
                String.format(Contant.DELETE_TABLES, tableId))
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
//                Toast.makeText(mContext, "删除失败，请检查网络", Toast.LENGTH_SHORT).show();
                customDialog.dismiss();
                System.out.println("emptyTableFragment onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                Message message = new Message();
                message.what = GET_ADAPTER_DATA;
                adapterHandler.sendMessage(message);
                customDialog.dismiss();
//                IOUDataTableAdapter.this.notifyDataSetChanged();
            }
        });
    }
    private Handler adapterHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_ADAPTER_DATA){
//                tableListAdapter.setOrderTableList((ArrayList<OrderTable>) msg.obj);
                IOUDataTableAdapter.this.notifyDataSetChanged();
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
            if (tablesList.get(position).getTableState() == 1){
                Toast.makeText(mContext, "该台正在使用中，暂时不能删除该台", Toast.LENGTH_SHORT).show();
                return;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
            builder.setMessage("您即将删除台名为:"+"\""+tablesList.get(position).getTableName()+"\""+"的餐台"
                    +"  id为"+tablesList.get(position).getIdtable());
            builder.setTitle("提示");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //设置你的操作事项
                    /**
                     * 在数据中删除这个项
                     */
                    deleteTable(tablesList.get(position).getIdtable(), dialog);
                    tablesList.remove(position);
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
