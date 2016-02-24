package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sliding.mainview.Beans.Food;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class StartActivity extends Activity {

    private Button okButton;
    private TextView nameText;
    private TextView passwordText;
    private SharedPreferences sharedPreferences;
    private int GET_ADAPTER_DATA = 0x123456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        okButton = (Button) this.findViewById(R.id.start_button);
        nameText = (TextView) this.findViewById(R.id.start_name);
        passwordText = (TextView) this.findViewById(R.id.start_password);

        if (sharedPreferences.contains("user")&&sharedPreferences.contains("password")){
            nameText.setText(sharedPreferences.getString("user",""));
            passwordText.setText(sharedPreferences.getString("password",""));
            login();
        }
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameText.getText().equals("") || nameText.getText().equals(null)) {
                    Toast.makeText(StartActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordText.getText().equals("") || nameText.getText().equals(null)) {
                    Toast.makeText(StartActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
//                StartActivity.this.finish();
            }
        });
    }

    public void login(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder().url(
                String.format(Contant.LOGIN,
                        nameText.getText().toString(), passwordText.getText().toString()))
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(StartActivity.this, "网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
                System.out.println("InsertNewFood onFailure== " + request.body());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
//                animatedStartActivity();
                if (response.body().string().contains("false")){
                    Message message = new Message();
                    message.what = 123;
                    adapterHandler.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what = GET_ADAPTER_DATA;
                    adapterHandler.sendMessage(message);
                }
            }
        });
    }

    private void animatedStartActivity() {
        // we only animateOut this activity here.
        // The new activity will animateIn from its onResume() - be sure to implement it.
        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        // disable default animation for new intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ActivitySwitcher.animationOut(findViewById(R.id.first), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
            @Override
            public void onAnimationFinished() {
                startActivity(intent);
//                StartActivity.this.finish();
            }
        });
    }
//    @Override
//    public void finish() {
//        ActivitySwitcher.animationOut(findViewById(R.id.first), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
//            @Override
//            public void onAnimationFinished() {
//                StartActivity.super.finish();
//                // disable default animation
////                overridePendingTransition(0, 0);
//            }
//        });
//    }
private Handler adapterHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        if (msg.what == GET_ADAPTER_DATA){
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("user", nameText.getText().toString());
            editor.putString("password", passwordText.getText().toString());
            editor.commit();
            animatedStartActivity();
        }else if (msg.what == 123){
            Toast.makeText(StartActivity.this, "用户名或密码错误",Toast.LENGTH_SHORT).show();
        }
    }
};
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
