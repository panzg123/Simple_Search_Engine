
一个简单的面向新闻的搜索引擎原型
=================================================

信息检索作业程序，小组成果：pzg & zjw & ltq & ljs

具体程序注解请参考：[现代信息检索---搜索引擎大作业](http://panzhengguang.github.io/2016/06/15/IR_simple_search_engine/)

* sportNewsSpider为爬虫程序，爬取新闻文档
* Index为建立倒排索引程序
* SearchEngine为搜索程序，JSP，index.txt为倒排索引

### 数据

--------

用`sportNewsSpider`定向爬取的3-4体育新闻网站的10W篇体育新闻

### 倒排索引程序，Index

-------------------

运行`IndexCreator.java`,将对`data`目录下的文档进行格式转换、切词、创建倒排索引、计算词项的tfidf值，并将倒排索引保存到`data/index.txt`，tfidf数据保存到`data/tfidf_index.txt`文本中。

**格式说明**

`index.txt`保存倒排索引，由于是文本保存，采用`#&next`等特殊字符来分割位，比如`word1`的索引信息如下：

> word&#1.txt#13#14#15@times@totalwords#next#2.txt#13#14#15@times@totalwords&timesofdocs

其表示的信息是：word1出现在1.txt中的13、14、15行,一共出现了times次，1.txt中一共totalwords个单词；然后是word1在2.txt中的统计数据。

`tfidf_index.txt`保存tfidf信息，以此来后面的程序基于此来构建空间向量模型，也采用特殊字符来分割，比如:

> 文化大革命&572.txt@5.4638#next#&5003.txt@3.6990#next#

其表示的信息为：`文化大革命`出现再572号和5003号文档中，tfidf值分别是5.4和3.6。

### 文本检索程序，Search Engine

`向量空间模型`计算与查询文本的相似度，返回`Top K`

## TODO

- [ ] 用`tornado+redis+mongo`整套框架改造程序，提升用户体验和程序性能。
