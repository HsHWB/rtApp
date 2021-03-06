package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sliding.mainview.Beans.MenuItem;
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Service.EndActivityBroadCast;
import com.example.sliding.mainview.View.Fragment.OrderFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderAcitivity extends Activity {

    private FragmentManager fm;
    private FragmentTransaction transaction;
    private OrderFragment orderFragment;
    private Button orderButton;
    private TextView tableNameText;

    private ImageView goBack;
    private int postion;
    private OrderTable orderTable;
    private EndActivityBroadCast endActivityBroadCast;

    public static String BROADCAST_ACTION = "finishOrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
    }
    private void init(){

        Intent intent = this.getIntent();
        postion = intent.getIntExtra("position",0);
        orderTable = (OrderTable)intent.getSerializableExtra("orderTable");
        orderButton = (Button) findViewById(R.id.order_listview_orderbutton);
        goBack = (ImageView) findViewById(R.id.order_goback);
        tableNameText = (TextView) findViewById(R.id.order_listview_head);
        tableNameText.setText(orderTable.getTableName());
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MenuItem> list = orderFragment.getOrderListViewAdapter().getItem();
                if (list.size() == 0){
                    Toast.makeText(OrderAcitivity.this, "请选择菜式", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intentItemSelect = new Intent(OrderAcitivity.this, ItemSelectActivity.class);
                intentItemSelect.putExtra("list", (Serializable)list);
                startActivity(intentItemSelect);
            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        orderFragment = new OrderFragment();
        orderFragment.setOrderTable(orderTable);
        transaction.add(R.id.activity_order_framelayout, orderFragment, "order");
        transaction.commit();

        endActivityBroadCast = new EndActivityBroadCast(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION);
        registerReceiver(endActivityBroadCast, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(endActivityBroadCast);
    }
}



