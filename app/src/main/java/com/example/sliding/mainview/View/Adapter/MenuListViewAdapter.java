package com.example.sliding.mainview.View.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sliding.mainview.Beans.MenuItem;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.WindowsUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 菜单的adapter
 * 需解决问题：屏幕滑动之后，点击提交需要保证看不到的并且已经选中的item能计算。
 */
public class MenuListViewAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private LayoutInflater inflater;
    private Context mContext;
    private HashMap<Integer, Boolean> checkBoxStateMap;//记录checkbox状态
    private HashMap<Integer, String> choiceItemsMap;//记录被算中的item name
    private Boolean isFirst;
    private MenuItem menuItem;
    final private HashMap<Integer, MenuItem> menuMap;
    private ArrayList<MenuItem> menuItemsList;//记录被算中的item name
    private OnEditChangeListener onEditChangeListener;
    private ButtonOnClickListener reduceButtonListener;
    private ButtonOnClickListener addButtonListener;

    public MenuListViewAdapter(Context context){
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.checkBoxStateMap = new HashMap<>();
        this.choiceItemsMap = new HashMap<>();
        this.menuItemsList = new ArrayList<>();
        this.menuMap = new HashMap<>();
        for (int i = 0; i < 36; i++){
            choiceItemsMap.put(i, i+"hello");
        }
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            /**
             * 初始化7个item时,获取R.id,然后put入menuMap
             */
            menuItem = new MenuItem();
            convertView = inflater.inflate(R.layout.order_listview_item, null);
            viewHolder.textId = (TextView) convertView.findViewById(R.id.menu_listview_item_tvId);
            viewHolder.textNum = (TextView) convertView.findViewById(R.id.menu_listview_item_tvNum);
            viewHolder.textName = (TextView) convertView.findViewById(R.id.menu_listview_item_tvName);
            viewHolder.editText = (EditText) convertView.findViewById(R.id.menu_listview_item_edittext);
            viewHolder.reduceButton = (Button) convertView.findViewById(R.id.menu_listview_item_reducebutton);
            viewHolder.addButton = (Button) convertView.findViewById(R.id.menu_listview_item_addbutton);
            viewHolder.mPosition = position;
            viewHolder.editText.setFocusableInTouchMode(false);
            menuItem.setItemId(viewHolder.mPosition);
            menuItem.setItemNum(0);
            /**
             * 切记不要重复set多次listener(放if外面)
             */
            onEditChangeListener = new OnEditChangeListener(menuItem, menuMap);
            viewHolder.editText.addTextChangedListener(onEditChangeListener);
            viewHolder.onEditChangeListener = onEditChangeListener;

            reduceButtonListener = new ButtonOnClickListener(viewHolder.editText);
            reduceButtonListener.setType(1);
            viewHolder.reduceButton.setOnClickListener(reduceButtonListener);
            viewHolder.reduceButtonListener = reduceButtonListener;

            addButtonListener = new ButtonOnClickListener(viewHolder.editText);
            addButtonListener.setType(2);
            viewHolder.addButton.setOnClickListener(addButtonListener);
            viewHolder.addButtonListener = addButtonListener;

            menuMap.put(position, menuItem);
            convertView.setTag(viewHolder);
        }else {
            /**
             * 如果重复使用ViewHolder，此时的menuItem也是复用的menuItem，所以以后滑动时，menuItem的num会取最后
             * 一次的复用值，然后所有指向这个menuItem的viewHolder,都取一样的值。所以必须要new一个，并且在监听器中重复赋值
             */
            menuItem = new MenuItem();
            menuItem.setItemNum(0);
            viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.onEditChangeListener.setMenuItem(menuItem);
        }
        viewHolder.onEditChangeListener.setPosition(position);
        viewHolder.textNum.setText(String.valueOf(position));
        viewHolder.mPosition = position;
        menuItem.setItemId(viewHolder.mPosition);
        /**
         * 如果menuMap已经存过这个item，则取出来
         */
        if (menuMap.containsKey(viewHolder.mPosition)){
            viewHolder.editText.setText(String.valueOf(menuMap.get(viewHolder.mPosition).getItemNum()));
        }else {
            /**
             * 若不存在，则设为0
             */
            viewHolder.editText.setText(String.valueOf(0));
        }

        AbsListView.LayoutParams ll = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (WindowsUtils.getWindowHeight(mContext)/7)
        );
        convertView.setLayoutParams(ll);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class ViewHolder{
        TextView textName;
        TextView textNum;
        TextView textId;
        EditText editText;
        Button addButton;
        Button reduceButton;
        int num;
        int mPosition;
        OnEditChangeListener onEditChangeListener;
        ButtonOnClickListener reduceButtonListener;
        ButtonOnClickListener addButtonListener;
    }
    class OnEditChangeListener implements TextWatcher {
        private MenuItem menuItem;
        private HashMap<Integer, MenuItem> menuMap;
        private int position;

        public OnEditChangeListener(MenuItem menuItem, HashMap<Integer, MenuItem> menuMap) {
            this.menuItem = menuItem;
            this.menuMap = menuMap;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }

        public void setMenuItem(MenuItem menuItem) {
            this.menuItem = menuItem;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals(null) && !s.toString().equals("")) {
                getMenuItem().setItemNum(Integer.valueOf(s.toString()));
                menuMap.put(getPosition(), getMenuItem());
            }
        }
    }
    class ButtonOnClickListener implements View.OnClickListener{

        private EditText numText;
        private int type = 0;
        private int num;

        public ButtonOnClickListener(EditText editText) {
            this.numText = editText;
        }

        /**
         * 增减判断
         */
        public void setType(int type){
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            /**
             * 1减2增
             */
            num = Integer.valueOf(numText.getText().toString());
            if (type == 1 && num > 0){
                numText.setText(String.valueOf(num-1));
            }else if (type == 2 && num < 20){
                numText.setText(String.valueOf(num+1));
            }
        }
    }
}
