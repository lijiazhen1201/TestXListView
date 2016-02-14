package com.zhenmei.testxlistview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.os.Handler;

import com.zhenmei.testxlistview.R;
import com.zhenmei.testxlistview.view.XListView;
import com.zhenmei.testxlistview.view.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenmei on 16/2/14.
 */
public class MainActivity extends Activity implements IXListViewListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private XListView xlistview;

    public void initView() {
        //实例化XListView
        xlistview = (XListView) findViewById(R.id.xlistview);
        // 设置可以下拉刷新
        xlistview.setPullRefreshEnable(true);
        // 设置可以上拉加载更多
        xlistview.setPullLoadEnable(true);
        //设置下拉刷新，上拉加载的事件监听
        xlistview.setXListViewListener(this);
        //设置虚拟数据
        initXListViewData();
    }

    private List<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    //添加10条虚拟数据
    public void initXListViewData() {
        for (int i = 1; i <= 10; i++) {
            list.add("item" + i);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        xlistview.setAdapter(adapter);
    }


    //子线程处理耗时操作
    class MyThread extends Thread {

        private int what;

        public MyThread(int what) {
            this.what = what;
        }

        @Override
        public void run() {
            super.run();
            //模拟加载延时3秒
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //发送信息给主线程
            handler.sendEmptyMessage(what);
        }
    }

    //Handler接受子线程发来的结果，处理信息
    private Handler handler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    //下拉刷新时，默认删除10条数据
                    if (list.size() > 0) {
                        for (int i = 0; i < 10; i++) {
                            list.remove(0);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    //停止下拉刷新
                    xlistview.stopRefresh();
                    break;
                case 2:
                    //上拉加载更多时，添加10条新数据
                    moreXListViewData();
                    //停止加载更多
                    xlistview.stopLoadMore();
                    break;
                default:
                    break;
            }
        }
    };

    private int more = 10;

    //加载更多时，模拟添加10条数据
    public void moreXListViewData() {
        for (int i = 1; i <= 10; i++) {
            list.add("item" + (i + more));
        }
        more += 10;
        //刷新adapter
        adapter.notifyDataSetChanged();
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        new MyThread(1).start();
    }

    //上拉加载更多
    @Override
    public void onLoadMore() {
        new MyThread(2).start();
    }
}
