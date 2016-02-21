package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;

import java.util.ArrayList;

/**
 * Created by huehn on 16/2/21.
 */
public class IOUDataTableAdapter extends BaseAdapter {

    private ArrayList<OrderTable> tablesList;
    private Context mContext;
    private LayoutInflater inflater;
    private float screenWidth;
    private float screenHeigh;

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
            viewHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("删除 "+tablesList.get(position).getTableName()+"的item");
                }
            });
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
        }else {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.whitesmoke));
        }

        AbsListView.LayoutParams al = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)screenHeigh/10);

        convertView.setLayoutParams(al);
        return convertView;
    }


    class ViewHolder{
        TextView tableName;
        TextView tableId;
        ImageView deleteItem;
        int tableid;
        int tableState;
    }
}
