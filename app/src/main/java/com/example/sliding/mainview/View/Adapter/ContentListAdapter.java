package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;

/**
 * Created by bingoo on 2015/11/3.
 */
public class ContentListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        String lastTagText = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.fragment_empty_table_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textNum = (TextView) convertView.findViewById(R.id.empty_table_item_tvNum);
            viewHolder.textName = (TextView) convertView.findViewById(R.id.empty_table_item_tvName);
            viewHolder.textId = (TextView) convertView.findViewById(R.id.empty_table_item_tvId);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.empty_table_item_checkBox);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
            lastTagText = viewHolder.checkBox.getTag().toString();
        }
        viewHolder.textNum.setText(String.valueOf(position));
        viewHolder.textName.setText("第"+position+"号桌");
        viewHolder.textId.setText(String.valueOf(position));
        String str1 = String.valueOf(viewHolder.textName.getText());
        String str2 = (String)viewHolder.checkBox.getTag();
        if (String.valueOf(viewHolder.textName.getText())
                .equals(lastTagText)){
            viewHolder.checkBox.setChecked(true);
        }
//        else {
//            viewHolder.checkBox.setChecked(false);
//        }
        viewHolder.checkBox.setTag(String.valueOf(viewHolder.textName.getText()));
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
