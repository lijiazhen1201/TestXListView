# TestXListView


## 使用XListView实现下拉刷新，上拉加载更多

**XListView下载地址**

[https://github.com/Maxwin-z/XListView-Android](https://github.com/Maxwin-z/XListView-Android)

1. 把XListView-Android-master/src/me/maxwin/view下的`XListView.java`，`XListViewFooter.java`和`XListViewHeader.java`拷贝到自己项目中

2. 把XListView-Android-master/res/layout下的`xlistview_footer.xml`和`xlistview_header.xml`拷贝到自己项目layout布局文件夹中

3. 把XListView-Android-master/res/drawable-hdpi下的`xlistview_arrow.png`图片拷贝到自己项目的图片资源中

4. 把XListView-Android-master/res/values下的`strings.xml`文件打开，将要用到的字符串资源添加到自己项目中，主要会用到以下字符串资源

	    <string name="xlistview_header_hint_normal">下拉刷新</string>
	    <string name="xlistview_header_hint_ready">松开刷新数据</string>
	    <string name="xlistview_header_hint_loading">正在加载...</string>
	    <string name="xlistview_header_last_time">上次更新时间：</string>
	    <string name="xlistview_footer_hint_normal">查看更多</string>
	    <string name="xlistview_footer_hint_ready">松开载入更多</string>
	    
5. 把`XListView.java`，`XListViewFooter.java`和`XListViewHeader.java`中的`import me.maxwin.R;`删掉，导入自己的R文件

6. 在自己的布局文件中添加XListView控件,要写全包名

		<com.zhenmei.testxlistview.view.XListView
        	 android:id="@+id/xlistview"
        	 android:layout_width="match_parent"
        	 android:layout_height="match_parent" />
7. java代码中实例化XListView，添加虚拟数据

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
8. 添加初始模拟数据
 
	    public void initXListViewData() {
	        for (int i = 1; i <= 10; i++) {
	            list.add("item" + i);
	        }
	        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
	        xlistview.setAdapter(adapter);
	    }

9. 子线程处理耗时操作

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
10. 接受子线程发来的结果，处理信息

	    //Handler接受子线程发来的结果，处理信息
	    private Handler handler = new Handler() {
	        public void dispatchMessage(android.os.Message msg) {
	            switch (msg.what) {
	                case 1:
	                    //下拉刷新时，默认删除10条数据
	                    for (int i = 0; i < 10; i++) {
	                        list.remove(0);
	                        adapter.notifyDataSetChanged();
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

11. 模拟加载更多

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
	    
12. 实现下拉刷新和上拉加载更多

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