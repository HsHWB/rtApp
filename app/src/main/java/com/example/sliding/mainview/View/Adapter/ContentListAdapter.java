package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;

import java.util.HashMap;

/**
 * Created by bingoo on 2015/11/3.
 */
public class ContentListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private HashMap<Integer, Boolean> checkBoxStateMap = new HashMap<>();
    private Boolean isFirst;

    public ContentListAdapter(Context context){
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        String tag;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.fragment_empty_table_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textNum = (TextView) convertView.findViewById(R.id.empty_table_item_tvNum);
            viewHolder.textName = (TextView) convertView.findViewById(R.id.empty_table_item_tvName);
            viewHolder.textId = (TextView) convertView.findViewById(R.id.empty_table_item_tvId);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.empty_table_item_checkBox);
            convertView.setTag(viewHolder);
            checkBoxStateMap.put(position, false);
            isFirst = true;
        }else {
            isFirst = false;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textNum.setText(String.valueOf(position));
        viewHolder.textName.setText("第"+position+"号桌");
        viewHolder.textId.setText(String.valueOf(position));
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println(isChecked);
                if (isChecked){
                    checkBoxStateMap.put(position, true);
                }else{
                    checkBoxStateMap.put(position, false);
                }
            }
        });
        if (isFirst){
            viewHolder.checkBox.setChecked(false);
        }else{
            if (checkBoxStateMap.containsKey(position)) {
                viewHolder.checkBox.setChecked(checkBoxStateMap.get(position));
            }else {
                viewHolder.checkBox.setChecked(false);
            }
        }
        AbsListView.LayoutParams ll = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (WindowsUtils.getWindowHeight(mContext)/7)
        );
        convertView.setLayoutParams(ll);
        return convertView;
    }

    class ViewHolder{
        TextView textName;
        TextView textNum;
        TextView textId;
        CheckBox checkBox;
    }
}
