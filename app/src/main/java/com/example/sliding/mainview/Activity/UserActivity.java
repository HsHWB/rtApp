package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.ActivityClass;
import com.example.sliding.mainview.Utils.Contant;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2016/2/25.
 */
public class UserActivity extends Activity {

    private String tag;
    private EditText nameText;
    private EditText password;
    private TextView titleText;
    private Button okButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ActivityClass.activityList.add(this);
        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        titleText = (TextView) this.findViewById(R.id.activity_setting_tv);
        nameText = (EditText) this.findViewById(R.id.activity_setting_name);
        password = (EditText) this.findViewById(R.id.activity_setting_password);
        okButton = (Button) this.findViewById(R.id.activity_setting_button);
        tag = getIntent().getStringExtra("tag");

        if (tag.equals("register")){
            titleText.setText("注册");
        }else if(tag.equals("changePassword")){
            titleText.setText("修改密码");
            if (sharedPreferences.contains("user")&&sharedPreferences.contains("password")){
                nameText.setText(sharedPreferences.getString("user",""));
                nameText.setKeyListener(null);
                password.setText(sharedPreferences.getString("password", ""));
            }
        }

        okButton.setOnClickListener(new OkOnClickListener());

    }
    class OkOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (nameText.getText().toString().equals("")||nameText.getText().toString().equals(null)
                    ||password.getText().toString().equals("")||password.getText().toString().equals(null)){
                Toast.makeText(UserActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (tag.equals("register")){
                register();
            }else if(tag.equals("changePassword")){
                change();
            }
        }
    }
    public void register(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(
                String.format(Contant.REGISTER,
                        nameText.getText().toString(), password.getText().toString()))
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message message = new Message();
                message.what = 111;
                adapterHandler.sendMessage(message);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (response.body().string().contains("false")){
                    Message message = new Message();
                    message.what = 112;
                    adapterHandler.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what = 113;
                    adapterHandler.sendMessage(message);
                    UserActivity.this.finish();
                }
            }
        });
    }
    public void change(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(
                String.format(Contant.CHANGE_PASSWORD,
                        nameText.getText().toString(), password.getText().toString()))
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message message = new Message();
                message.what = 111;
                adapterHandler.sendMessage(message);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
//                animatedStartActivity();
                if (response.body().string().contains("false")){
                    Message message = new Message();
                    message.what = 111;
                    adapterHandler.sendMessage(message);
                }else {
                    Intent intent = new Intent(UserActivity.this, StartActivity.class);
                    UserActivity.this.startActivity(intent);
                    ActivityClass.delete();
                }
            }
        });
    }
    private Handler adapterHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 111){
                Toast.makeText(UserActivity.this, "网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
            }else if (msg.what == 112){
                Toast.makeText(UserActivity.this, "已存在此用户，请更换用户名", Toast.LENGTH_SHORT).show();
            }else if (msg.what == 113){
                Toast.makeText(UserActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
