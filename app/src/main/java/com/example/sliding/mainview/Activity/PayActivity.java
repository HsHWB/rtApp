package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.View.Fragment.PayFragment;
import com.example.sliding.mainview.View.Fragment.ShowItemFragment;

public class PayActivity extends Activity {

    private PayFragment payFragment;
    private ShowItemFragment showItemFragment;
    private int tableId;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        init();
    }
    public void init(){
        payFragment = new PayFragment();
        showItemFragment = new ShowItemFragment();
        tableId = getIntent().getIntExtra("tableId", 0);
        Bundle data = new Bundle();
        data.putInt("tableId", tableId);

        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        showItemFragment.setArguments(data);
        transaction.replace(R.id.activity_pay_framelayout, showItemFragment, "showItemFragment");
        transaction.commit();
    }
}
