package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sliding.mainview.Activity.OrderAcitivity;
import com.example.sliding.mainview.Activity.PayActivity;
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;

import java.util.ArrayList;

/**
 * Created by huehn on 16/2/12.
 */
public class NotedTableAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private LayoutInflater inflater;
    private Context mContext;
    private float screenWidth;
    private float screenHeight;
    private ArrayList<OrderTable> orderTableList = new ArrayList<>();

    public NotedTableAdapter(Context context) {
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.screenHeight = WindowsUtils.getWindowHeight(mContext);
        this.screenWidth = WindowsUtils.getWindowWidth(mContext);
    }

    public void setOrderTableList(ArrayList<OrderTable> orderTableList) {
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

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_noted_table_item, null);

            viewHolder.tableName = (TextView) convertView.findViewById(R.id.noted_table_item_tableName);
            viewHolder.tableNum = (TextView) convertView.findViewById(R.id.noted_table_item_tableId);
            viewHolder.tableStatus = (TextView) convertView.findViewById(R.id.noted_table_item_state);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (orderTableList.size() > 0) {
            viewHolder.tableName.setText(this.orderTableList.get(position).getTableName());
            viewHolder.tableNum.setText(String.valueOf(this.orderTableList.get(position).getTableNum()));
        }

        AbsListView.LayoutParams al = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) screenHeight / 10);

        convertView.setLayoutParams(al);
        return convertView;
    }


    class ViewHolder {
        TextView tableName;
        TextView tableNum;
        TextView tableStatus;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        System.out.println("click position == " + position);
        Intent intent = new Intent(mContext, PayActivity.class);
        intent.putExtra("tableId", orderTableList.get(position).getIdtable());
        intent.putExtra("tableName", orderTableList.get(position).getTableName());
        mContext.startActivity(intent);
    }

}
