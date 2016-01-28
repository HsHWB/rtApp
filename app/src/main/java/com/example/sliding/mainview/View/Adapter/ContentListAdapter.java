package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    final private HashMap<Integer, MenuItem> menuMap;
    private ArrayList<MenuItem> menuItemsList;//记录被算中的item name

    public ContentListAdapter(Context context){
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.checkBoxStateMap = new HashMap<>();
        this.choiceItemsMap = new HashMap<>();
        this.menuItemsList = new ArrayList<>();
        this.menuMap = new HashMap<>();
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
        if (convertView == null) {
            menuItem = new MenuItem();
            convertView = inflater.inflate(R.layout.fragment_empty_table_item, null);
            menuItem.setItemIdText((TextView) convertView.findViewById(R.id.empty_table_item_tvId));
            menuItem.setItemNameText((TextView) convertView.findViewById(R.id.empty_table_item_tvName));
            menuItem.setTableNameText((TextView) convertView.findViewById(R.id.empty_table_item_tvNum));
            menuItem.setNumEditText((EditText) convertView.findViewById(R.id.empty_table_item_edittext));
//            menuItem.setItemId(position);
//            menuItem.getNumEditText().addTextChangedListener(new OnEditChangeListener(menuItem, menuMap));
//            menuItem.getNumEditText().setText("0");
//            menuMap.put(position, menuItem);
            menuMap.put(position, menuItem);
            convertView.setTag(menuItem);
        }else {
            menuItem = (MenuItem) convertView.getTag();
        }
//        menuMap.put(position, menuItem);
//        menuItem.setItemId(position);
        menuItem.setItemId(position);
        menuItem.getNumEditText().addTextChangedListener(new OnEditChangeListener(menuItem, menuMap));
//        menuItem.getNumEditText().setText("0");
        System.out.println("position == "+position);
        if (menuMap.containsKey(position)) {
            menuItem.getNumEditText().setText(String.valueOf(menuMap.get(position).getNum()));
            System.out.println(menuMap.get(position).getNum());
        }else{
            menuItem.getNumEditText().setText(String.valueOf(0));
        }
        menuMap.put(position, menuItem);
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
    class OnEditChangeListener implements TextWatcher{
        private MenuItem menuItem;
        private HashMap<Integer, MenuItem> menuMap;
        public OnEditChangeListener(MenuItem menuItem, HashMap<Integer, MenuItem> menuMap){
            this.menuItem = menuItem;
            this.menuMap = menuMap;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals(null)&&!s.toString().equals("")) {
//                menuItem.setNum(Integer.valueOf(s.toString()));
//                menuMap.put(menuItem.getItemId(), menuItem);
                menuMap.put(menuItem.getItemId(), menuItem);
            }
        }
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