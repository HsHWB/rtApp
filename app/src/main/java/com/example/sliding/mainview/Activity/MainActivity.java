package com.example.sliding.mainview.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.FragmentManager;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.example.sliding.mainview.Beans.Item;
import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.Contant;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.example.sliding.mainview.View.CustomView.SlidingMenu;
import com.example.sliding.mainview.View.Fragment.ContentFragment;
import com.example.sliding.mainview.View.Fragment.MenuFragment;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
//import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends FragmentActivity {

    public static float screenWidth;
    public static float screenHeight;
    private FragmentManager fm;
    private FragmentTransaction transaction;

    private SlidingMenu slidingMenu;
    private FrameLayout childMenu;
    private FrameLayout childContent;
    private int menuWidth;
    private int quarterMenuWidth;
    private ContentFragment contentFragment;
    private MenuFragment menuFragment;
    private LinearLayout.LayoutParams menull;
    private LinearLayout.LayoutParams contentll;

    /**
     * 判断是否第一次开activity，如果是第一次，onStart()就不刷新数据
     */
    private boolean activityIsOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rt_main_activity);
        screenHeight = WindowsUtils.getWindowHeight(getApplicationContext());
        screenWidth = WindowsUtils.getWindowWidth(getApplicationContext());
        activityIsOn = false;
        initView();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityIsOn){
            contentFragment.netWork();
        }
        activityIsOn = true;
    }

    private void initView(){
        /**
         * 菜单栏的宽度
         */
        menuWidth = 2*(int)screenWidth/3;
        quarterMenuWidth = menuWidth / 4;

        /**
         * 获取关于侧滑控件的子控件id,这里还没封装好
         */
        slidingMenu = (SlidingMenu) this.findViewById(R.id.rt_main_slidingMenu);
        childMenu = (FrameLayout) slidingMenu.findViewById(R.id.slidingview_select_item);
        childContent = (FrameLayout) slidingMenu.findViewById(R.id.slidingview_content);

        /**
         * 设置侧滑控件的子控件大小
         */
        menull = new LinearLayout.LayoutParams(2*(int)screenWidth/3,(int)screenHeight);
        contentll = new LinearLayout.LayoutParams((int)screenWidth,(int)screenHeight);
        childMenu.setLayoutParams(menull);
        childContent.setLayoutParams(contentll);

        /**
         * 替换fragment
         */
        contentFragment = new ContentFragment();
        contentFragment.setSlidingMenu(slidingMenu);
        menuFragment = new MenuFragment();
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(childMenu.getId(), menuFragment, "menu");
        transaction.add(this.childContent.getId(), contentFragment, "content");
        transaction.commit();

        /**
         * 首次打开app通知  内容fragment  用空余餐桌fragment代替
         */
        contentFragment.replaceView();

//        StringRequest stringRequest = new StringRequest(
//                String.format("http://192.168.199.207:8080/ContactsBackupSystem/action/user_add.action" +
//                        "?account=122112&password=123qwe&nickname=%s", "你好"),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("TAG", response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("TAG", error.getMessage(), error);
//            }
//        });
//        RequestQueue requestQueue =  Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        //创建一个Request
//        Request request = new Request.Builder().url(
//                String.format(Contant.INSERT_NEW_TABLES, "你好", 123456))
//                .build();
//        //new call
//        Call call = mOkHttpClient.newCall(request);
//        //请求加入调度
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                System.out.println("showItemFragment onFailure== " + request.body());
//            }
//
//            @Override
//            public void onResponse(final Response response) throws IOException {
//                System.out.println("showItemFragment onSuccess == " + response.body().string());
//            }
//        });

    }
//    /**
//     * 监听Back键按下事件,方法1:
//     * 注意:
//     * super.onBackPressed()会自动调用finish()方法,关闭
//     * 当前Activity.
//     * 若要屏蔽Back键盘,注释该行代码即可
//     */
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        System.out.println("按下了back键   onBackPressed()");
//    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (slidingMenu.isMenuOpen()){
                slidingMenu.menuClose();
                return false;
            }else {
                return super.onKeyDown(keyCode, event);
            }
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
