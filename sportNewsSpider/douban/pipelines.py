# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

import json
import codecs

import unicodedata

class DoubanPipeline(object):

    def __init__(self):
	global var_count
	global var_set
	var_count = 1
	#var_set = set("")
	

    def process_item(self, item, spider):
	global var_count
	#global var_set
	str1 = 'data/sportnews/'
	str2 = str(var_count)
	str3 = '.json'
	str1 += str2
	str1 += str3
	self.file = codecs.open(str1, mode='wb',encoding='utf-8')
	d = dict(item)
	del d['Comment']
	del d['Html']
	line = json.dumps(d,sort_keys=True,skipkeys=True,indent=4)
        self.file.write(line.decode("unicode_escape"))

	str4 = 'data/newscomment/'
	str4 += str2
	str4 += '.json'
	self.file2 = codecs.open(str4, mode='wb', encoding='utf-8')
	line = json.dumps(item['Comment'],indent=4)
        self.file2.write(line.decode("unicode_escape"))

	str5 = 'data/newshtml/'
	str5 += str2
	str5 += '.html'
	self.file3 = codecs.open(str5, mode='wb', encoding='utf-8')
	h = "".join(item['Html'])
	#h.decode('gbk', 'ignore').encode('utf-8')
	#print unicode(h, "gbk")
	#h.encode('gbk','ignore')
	#h.decode("gbk")
        self.file3.write(h)

	print var_count,d['Title'],d['Time'],"total:",d['Total']
	print "ID:",d['ID'],d['URL']
	print d['Artical'][0:40]
	#var_set.add(item['URL'])
	var_count += 1
	return item
