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
public class ContentListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private HashMap<Integer, Boolean> checkBoxStateMap;//记录checkbox状态
    private HashMap<Integer, String> choiceItemsMap;//记录被算中的item name
    private Boolean isFirst;
    private MenuItem menuItem;
    private ArrayList<MenuItem> menuItemsList;//记录被算中的item name

    public ContentListAdapter(Context context){
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.checkBoxStateMap = new HashMap<>();
        this.choiceItemsMap = new HashMap<>();
        this.menuItemsList = new ArrayList<>();
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
        menuItem = new MenuItem();
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_empty_table_item, null);
            viewHolder.textNum = (TextView) convertView.findViewById(R.id.empty_table_item_tvNum);
            viewHolder.textName = (TextView) convertView.findViewById(R.id.empty_table_item_tvName);
            viewHolder.textId = (TextView) convertView.findViewById(R.id.empty_table_item_tvId);
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
        menuItem.setItemName(viewHolder.textNum.toString());
//        if (isFirst){
//            viewHolder.checkBox.setChecked(false);
//        }else{
//            if (checkBoxStateMap.containsKey(position)) {
//                viewHolder.checkBox.setChecked(checkBoxStateMap.get(position));
//                menuItem.setIsChoice(checkBoxStateMap.get(position));
//            }else {
//                viewHolder.checkBox.setChecked(false);
//                menuItem.setIsChoice(false);
//            }
//        }
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
    }
    class OnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{

        private ViewHolder viewHolder;
        private int position;

        public OnCheckedChangeListener(ViewHolder viewHolder, int position) {
            this.viewHolder = viewHolder;
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            System.out.println(isChecked);
            if (isChecked){
                checkBoxStateMap.put(position, true);
                choiceItemsMap.put(position, viewHolder.textName.getText().toString());
//                System.out.println(choiceItemsMap.get(position));
//                System.out.println("choiceItemsMap size == "+choiceItemsMap.size());
            }else{
                checkBoxStateMap.put(position, false);
                //                choiceItemsMap.remove(position);
            }
        }
    }
}