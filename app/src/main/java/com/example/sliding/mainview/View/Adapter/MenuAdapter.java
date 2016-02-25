package com.example.sliding.mainview.View.Adapter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.example.sliding.mainview.View.CustomView.SlidingMenu;

/**
 * Created by Administrator on 2016/2/17.
 */
public class MenuAdapter extends BaseAdapter{

    private String tag[] = {"开台点餐", "修改餐桌", "修改菜式","个人设置", "关于    我"};
    private Context mContext;
    private float screenWidth;
    private float screenHeight;
    private LayoutInflater inflater;
    private int colorP = 0;
    private SlidingMenu slidingMenu;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private FrameLayout childContent;

    public MenuAdapter(Context mContext, SlidingMenu slidingMenu) {
        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.screenHeight = WindowsUtils.getWindowHeight(mContext);
        this.screenWidth = WindowsUtils.getWindowWidth(mContext);
        this.slidingMenu = slidingMenu;
    }

    public void setFragmentController(FragmentManager fm, FragmentTransaction transaction, FrameLayout childContent){
        this.fm = fm;
        this.transaction = transaction;
        this.childContent = childContent;
    }

    @Override
    public int getCount() {
        return 5;
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
        viewHolder.textView.setCompoundDrawables(getDrawble(position), null , null , null ); //设置TextView的drawableleft
        viewHolder.textView.setCompoundDrawablePadding( 10 ); //设置图片和text之间的
        if (colorP == position) {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.blueblue));
        }else {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }
        AbsListView.LayoutParams al = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)screenHeight/15);

        convertView.setLayoutParams(al);
        return convertView;
    }

    public void setItemColor(int p){
        this.colorP = p;
        this.notifyDataSetChanged();
    }

    public Drawable getDrawble(int position){
        Drawable drawable = null;
        if (position == 0){
            drawable = mContext.getResources().getDrawable(R.mipmap.usual);
            drawable.setBounds( 0 ,  0 , 35, 35);
        }else if (position == 1){
            drawable = mContext.getResources().getDrawable(R.mipmap.change_table);
            drawable.setBounds( 0 ,  0 , 35, 35);
        }else if (position == 2){
            drawable = mContext.getResources().getDrawable(R.mipmap.change_food);
            drawable.setBounds( 0 ,  0 , 35, 35);
        }else if (position == 3){
            drawable = mContext.getResources().getDrawable(R.mipmap.setting);
            drawable.setBounds( 0 ,  0 , 35, 35);
        }else if (position == 4){
            drawable = mContext.getResources().getDrawable(R.mipmap.about_me);
            drawable.setBounds( 0 ,  0 , 35, 35);
        }
        return drawable;
    }

    class ViewHolder {
        TextView textView;
    }
}
