本程序的目的是创建倒排索引
==================================
主程序是IndexCreator.java文件

###jar

主要是分词程序的jar包

###src
* IndexOperation.Json2Docs 将Json文件读取新闻内容，存储在txt文档中
* Split2Words 将docs文档，即切词过程
* CreateIndex 创建倒排索引
* CreateTfidf 计算tf-idf权重

可以分别运行，也可以直接运行。

###data
数据分别存储在data文件夹下，docs,json2,words,index.txt,tf_idf.txt等文件中

由于10W篇新闻文件过大，只保存10篇样本文档；index.txt大约41M，移动到SearchEngine目录下