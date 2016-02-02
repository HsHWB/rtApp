package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.content.Intent;
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

import com.example.sliding.mainview.Activity.OrderAcitivity;
import com.example.sliding.mainview.Beans.MenuItem;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 菜单的adapter
 * 需解决问题：屏幕滑动之后，点击提交需要保证看不到的并且已经选中的item能计算。
 */
public class TableListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private LayoutInflater inflater;
    private Context mContext;
    private float screenWidth;
    private float screenHeight;
    private HashMap<Integer, String> tableDataMap;

    public TableListAdapter(Context context){
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.screenHeight = WindowsUtils.getWindowHeight(mContext);
        this.screenWidth = WindowsUtils.getWindowWidth(mContext);
    }

    @Override
    public int getCount() {
        return 30;
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
            viewHolder.tableId = (TextView) convertView.findViewById(R.id.empty_table_item_tableId);
            viewHolder.tableStatus = (TextView) convertView.findViewById(R.id.empty_table_item_state);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tableName.setText("第"+position+"台");
        viewHolder.tableId.setText(String.valueOf(position));

        AbsListView.LayoutParams al = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)screenHeight/10);

        convertView.setLayoutParams(al);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("click position == "+position);
        Intent intent = new Intent(mContext, OrderAcitivity.class);
        intent.putExtra("position", position);
        mContext.startActivity(intent);
    }

    class ViewHolder{
        TextView tableName;
        TextView tableId;
        TextView tableStatus;
    }
}