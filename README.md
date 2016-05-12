一个简单的面向新闻的搜索引擎原型
=================================================

信息检索作业程序，小组成果：pzg & zjw & ltq & ljs

具体程序注解请参考：[现代信息检索---搜索引擎大作业](http://www.cnblogs.com/panweishadow/p/4198149.html#3329697)

* sportNewsSpider为爬虫程序，爬取新闻文档
* Index为建立倒排索引程序
* SearchEngine为搜索程序，JSP，index.txt为倒排索引

###TODO
1. 用mongo来存储数据库
2. 用redis来存储倒排索引
3. 用tornado来构建网站系统
4. 重写前端代码