package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.View.Fragment.OrderFragment;

public class OrderAcitivity extends Activity {

    private FragmentManager fm;
    private FragmentTransaction transaction;
    private OrderFragment orderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
    }
    private void init(){
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        orderFragment = new OrderFragment();
        transaction.add(R.id.activity_order_framelayout, orderFragment, "menu");
        transaction.commit();
    }
}
