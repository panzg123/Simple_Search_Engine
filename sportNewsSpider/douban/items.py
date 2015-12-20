# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy
from scrapy.item import Item, Field

class DoubanItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    ID = Field()
    URL = Field()
    Time = Field()
    Keyword = Field()
    Source = Field()
    #hotness
    Reply = Field() 
    Total = Field()
    Show = Field()
    #hotness
    Title = Field()
    Artical = Field()
	
    Comment = Field()
    Html = Field()
    
