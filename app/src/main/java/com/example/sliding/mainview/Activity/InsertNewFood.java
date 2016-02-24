package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class InsertNewFood extends Activity {

    private Button okButton;
    private EditText nameText;
    private EditText priceText;
    private int foodId;
    private String name;
    private String price;
    private String tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_food);

        okButton = (Button) this.findViewById(R.id.activity_insert_food_button);
        nameText = (EditText) this.findViewById(R.id.activity_insert_food_name);
        priceText = (EditText) this.findViewById(R.id.activity_insert_food_price);

        tag = getIntent().getStringExtra("tag");

        if (tag.equals("insert")){

        }else if (tag.equals("update")){
            name = getIntent().getStringExtra("foodName");
            price = getIntent().getStringExtra("foodPrice");
            foodId = getIntent().getIntExtra("foodId", 0);
            nameText.setText(name);
            priceText.setText(price);
        }
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameText.getText().equals(null)){
                    Toast.makeText(InsertNewFood.this, "请输入菜名", Toast.LENGTH_SHORT).show();
                    return;
                }else if (priceText.getText().equals(null)){
                    Toast.makeText(InsertNewFood.this, "请输入价格", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tag.equals("insert")){
                    insertFood();
                }else if (tag.equals("update")){
                    updateFood();
                }
            }
        });
    }
    public void deleteFood(int foodId){

    }

    public void updateFood(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url(
                String.format(Contant.UPDATE_FOOD,
                        nameText.getText().toString(), priceText.getText().toString(), foodId))
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(InsertNewFood.this, "网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
                System.out.println("InsertNewFood onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                InsertNewFood.this.finish();
            }
        });

    }

    public void insertFood(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url(
                String.format(Contant.INSERT_NEW_FOOD,
                        nameText.getText().toString(), priceText.getText().toString()))
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(InsertNewFood.this, "网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
                System.out.println("InsertNewFood onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                InsertNewFood.this.finish();
            }
        });
    }
}
