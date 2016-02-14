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
	    
5. 把`XListView.java`中的`import me.maxwin.R;`删掉，导入自己的R文件