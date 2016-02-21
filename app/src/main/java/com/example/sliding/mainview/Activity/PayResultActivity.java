package com.example.sliding.mainview.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class PayResultActivity extends AppCompatActivity {

    private int tableId;
    private String price;
    private TextView moneyText;
    private Button moneyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        tableId = getIntent().getIntExtra("tableId",0);
        price = getIntent().getStringExtra("price");
        moneyText = (TextView) this.findViewById(R.id.pay_result_moneytext);
        moneyButton = (Button) this.findViewById(R.id.pay_result_button);

        moneyText.setText(price);


        moneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient mOkHttpClient = new OkHttpClient();
                //创建一个Request
                Request request = new Request.Builder().url(
                        String.format(Contant.PAY, tableId))
                        .build();
                //new call
                Call call = mOkHttpClient.newCall(request);
                //请求加入调度
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        System.out.println("showItemFragment onFailure== " + request.body());
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {
                        System.out.println("showItemFragment onSuccess == " + response.body().string());
                        finish();
                    }
                });
            }
        });
    }
}
