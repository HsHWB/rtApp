package com.example.pulltorefresh;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.dy.pull2refresh.view.Pull2RefreshListView;
import com.dy.pull2refresh.view.Pull2RefreshListView.OnLoadMoreListener;
import com.dy.pull2refresh.view.Pull2RefreshListView.OnRefreshListener;
import com.dy.pulltorefresh.R;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private static final int LOAD_DATA_FINISH = 10;
	private static final int REFRESH_DATA_FINISH = 11;
	private List<AppInfo> mList = new ArrayList<AppInfo>();
	private MyListAdapter adpter;
	private Pull2RefreshListView _ListView;//定义自动加载更多listview
	private int curPage = 0;// 当前所在页数

	
	
	
	@SuppressLint("HandlerLeak")
	public Handler _Handler = new Handler() {

		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			Log.i(TAG, "handleMessage:"+msg.what);
			if (curPage == 0) {// 当加载的页数等总页数时，去掉加载最多的view,测试为5
				Log.i(TAG, "remove load more");
				_ListView.setCanLoadMore(false);
				
			}
			switch (msg.what) {
			case REFRESH_DATA_FINISH:
				if (adpter != null) {
					adpter.mList = (ArrayList<AppInfo>) msg.obj;
					adpter.notifyDataSetChanged();
				}

				_ListView.onRefreshComplete(); // 下拉刷新完成
				break;
			case LOAD_DATA_FINISH:
				if (adpter != null) {
					adpter.mList.addAll((ArrayList<AppInfo>) msg.obj);
					adpter.notifyDataSetChanged();
				}
				_ListView.onLoadMoreComplete(); // 加载更多完成
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_activity_main);

		adpter = new MyListAdapter(this, mList);
		_ListView = (Pull2RefreshListView) findViewById(R.id.mListView);
		_ListView.setAdapter(adpter);

		loadData(0, _Handler);
		initPullToRefresh();
	}

	private void initPullToRefresh() {

		_ListView.setCanLoadMore(true);
		_ListView.setCanRefresh(true);
		_ListView.setAutoLoadMore(true);
		_ListView.setMoveToFirstItemAfterRefresh(true);
		_ListView.setDoRefreshOnUIChanged(false);
		_ListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO 下拉刷新
				curPage = 0;
				_ListView.setCanLoadMore(true);
				Log.e(TAG, "onRefresh");
				loadData(0, _Handler);
			}
		});

		_ListView.setOnLoadListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				// TODO 加载更多
				++curPage;// 模拟加载到下一页
				Log.e(TAG, "onLoad");
				loadData(1, _Handler);
			}
		});

		_ListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 此处传回来的position和mAdapter.getItemId()获取的一致;
				Log.e(TAG, "click position:" + position);
				// Log.e(TAG,
				// "__ mAdapter.getItemId() = "+mAdapter.getItemId(position));
			}
		});
	}

	public void loadData(final int pType, final Handler pHandler) {

		Log.i(TAG, "loadData");
		new Thread() {
			@Override
			public void run() {
				List<AppInfo> _List = null;
				switch (pType) {
				case 0:
					_List = new ArrayList<AppInfo>();
					for (int i = 1; i <= 4; i++) {
						AppInfo ai = new AppInfo();
						ai.setAppName("应用Demo_" + i);
						ai.setAppVer("版本: " + (i % 10 + 1) + "." + (i % 8 + 2)
								+ "." + (i % 6 + 3));
						ai.setAppSize("大小: " + i * 10 + "MB");

						_List.add(ai);
					}
					break;

				case 1:
					_List = new ArrayList<AppInfo>();
					int _Index = 4;

					for (int i = 0 + 1; i <= _Index; i++) {
						AppInfo ai = new AppInfo();

						ai.setAppName("应用Demo_" + i);
						ai.setAppVer("版本: " + (i % 10 + 1) + "." + (i % 8 + 2)
								+ "." + (i % 6 + 3));
						ai.setAppSize("大小: " + i * 10 + "MB");

						_List.add(ai);
					}
					break;
				}
				Log.i(TAG, "current page :"+curPage);
				if (curPage == 0) {// 当加载的页数等总页数时，去掉加载最多的view,测试为5
					Log.i(TAG, "remove load more");
					_ListView.setCanLoadMore(false);
					
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 后台返回数据后，根据不同类型，通知不同的listview更新
				if (pType == 0) { // 下拉刷新
					Message _Msg = pHandler.obtainMessage(REFRESH_DATA_FINISH,
							_List);
					pHandler.sendMessage(_Msg);
				} else if (pType == 1) {//自动加载、下拉刷新
					Message _Msg = pHandler.obtainMessage(LOAD_DATA_FINISH,
							_List);
					pHandler.sendMessage(_Msg);
				}
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
