package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sliding.mainview.Beans.OrderTable;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class InsertNewTable extends Activity {

    private Button okButton;
    private EditText nameText;
    private EditText numText;
    private String name;
    private int num;

    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_table);

        tag = getIntent().getStringExtra("tag");
        if (tag.equals("insert")){
            nameText = (EditText) findViewById(R.id.activity_insert_tablename);
            numText = (EditText) findViewById(R.id.activity_insert_tablenum);
            okButton = (Button) findViewById(R.id.activity_insert_button);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameText.getText().equals(null)){
                    Toast.makeText(InsertNewTable.this, "请输入台名", Toast.LENGTH_SHORT).show();
                    return;
                }else if (numText.getText().equals(null)){
                    Toast.makeText(InsertNewTable.this, "请输入台号", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkHttpClient mOkHttpClient = new OkHttpClient();
                //创建一个Request
                Request request = new Request.Builder().url(
                        String.format(Contant.INSERT_NEW_TABLES,
                                nameText.getText().toString(), Integer.valueOf(numText.getText().toString())))
                        .build();
                //new call
                Call call = mOkHttpClient.newCall(request);
                //请求加入调度
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Toast.makeText(InsertNewTable.this, "网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
                        System.out.println("InsertNewTable onFailure== " + request.body());
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {
                        InsertNewTable.this.finish();
                    }
                });
            }
        });

    }

}
