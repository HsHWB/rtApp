package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;

/**
 * Created by Administrator on 2016/2/17.
 */
public class MenuAdapter extends BaseAdapter {

    private String tag[] = {"寻常业务", "修改菜式", "个人设置", "关于我们"};
    private Context mContext;
    private float screenWidth;
    private float screenHeight;
    private LayoutInflater inflater;

    public MenuAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.screenHeight = WindowsUtils.getWindowHeight(mContext);
        this.screenWidth = WindowsUtils.getWindowWidth(mContext);
    }

    @Override
    public int getCount() {
        return 4;
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
        ViewHolder viewHolder = new ViewHolder();
        convertView = inflater.inflate(R.layout.menu_listview_item, null);
        viewHolder.textView = (TextView) convertView.findViewById(R.id.menu_listview_item_textview);
        viewHolder.textView.setText(tag[position]);

        AbsListView.LayoutParams al = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)screenHeight/15);

        convertView.setLayoutParams(al);
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
