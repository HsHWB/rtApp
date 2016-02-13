package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sliding.mainview.Beans.Item;
import com.example.sliding.mainview.Beans.MenuItem;
import com.example.sliding.mainview.R;

import java.util.ArrayList;

/**
 * Created by huehn on 16/2/11.
 */
public class ItemSelectAdapter extends BaseAdapter {

    private ArrayList<Item> list;
    private Context mContext;
    private LayoutInflater inflater;

    public ItemSelectAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(ArrayList<Item> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_item_select_item, null);
            viewHolder.foodName = (TextView) convertView.findViewById(R.id.item_select_item_foodname);
            viewHolder.foodNum = (TextView) convertView.findViewById(R.id.item_select_item_foodNum);
            viewHolder.foodPrice = (TextView) convertView.findViewById(R.id.item_select_item_foodprice);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.foodName.setText(list.get(position).getItemName());
        viewHolder.foodNum.setText(String.valueOf(list.get(position).getFoodNum()));
        viewHolder.foodPrice.setText(String.valueOf(list.get(position).getFoodPrice()));
        System.out.println("postion == "+position);
        return convertView;
    }

    class ViewHolder{
        TextView foodName;
        TextView foodNum;
        TextView foodPrice;
    }
}
