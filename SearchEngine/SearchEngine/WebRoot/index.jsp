<%@page import="parse.Segment"%>
<%@page import="parse.Parse"%>
<%@page import="ucas.AutoAbstract"%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%

request.setCharacterEncoding("UTF-8");

	//ArrayList<Engine.ResultModel> list=null;  
	// 获取分词器
	//LinkedList<String> list=null;
	LinkedList<String> time=null;
	LinkedList<String> total=null;
	LinkedList que=null;
	String strPath=null;
	
	Parse parse=new Parse();
	LinkedList<String> list=parse.LinkList();
	list.clear();
	
	TreeMap<String,String> map1 = new TreeMap<String,String>(new Comparator(){

			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				return o2.toString().compareTo(o1.toString());
			}
			
		});   
  
	    	 //按热度的TreeMap，新添加
	  TreeMap<String,String> map2 = new TreeMap<String,String>(	new Comparator(){

			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				return o2.toString().compareTo(o1.toString());
			}
			
		});   		
		
parse.Init();

	boolean isResult = false;
	String keyword = request.getParameter("keyWord");
	if(keyword!=null)
	{
		keyword = keyword.trim();
	
		if(keyword.equals(""))
		
			isResult = false;
		else
		{
		
			isResult = true;
			ServletContext app = (ServletContext) pageContext.getServletContext();
			strPath = app.getRealPath("/"); //获得根目录的绝对路径
			
			list=parse.Query(keyword);
			
			
			
			
			
			
			
			
			if(list==null)
			isResult = false;
			else
			{
/*			 String radio = request.getParameter("radio");
	    	 parse.PRINT(radio);
	    	 if(radio.equals("score"))
	    	   {  
	    	   list=parse.Query(keyword);
	    	    }
	    	   else if(radio.equals("time"))
	    	   {
	    	   list=parse.List(map1);

	    	   
	    	      }
	    	   else{
	    	   list=parse.List(map2);
	    	   }*/
			
			}
		}
	}
	else
	{
		keyword="";
	}
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>>新闻搜索引擎</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="Images/Zzk.css" rel="stylesheet" type="text/css" />
    <link href="Images/favicon.ico" rel="bookmark" />
    <link href="Images/ucas1.ico" rel="shortcut icon" />
 <script language="javascript" type="text/javascript">  
    function save() {  
        radios = document.getElementsByName("radio");  
        for (i = 0; i < radios.length; i++) {  
            if (radios[i].checked) document.cookie = 'radioindex =' + i;  
        }  
    }  
    window.onload = function () {  
        var cooki = document.cookie;  
        if (cooki != "") {  
            cooki = "{\"" + cooki + "\"}";  
            cooki = cooki.replace(/\s*/g, "").replace(/=/g, '":"').replace(/;/g, '","');  
            var json = eval("(" + cooki + ")"); //将coolies转成json对象    
            document.getElementsByName("radio")[json.radioindex].checked = true;  
        }  
        else  
            save();  
    }  
</script> 
    
  </head>
  
  <body>
   
    <div id="head">
           <a herf="index.jsp" id="input_a"><img alt="体育新闻搜索" src="Images/ucas2.ico" class="inputimg"></a>
            <form id="fmSearch"  method="post"  action="index.jsp">
            <input type="text" name="keyWord" class="inputText" value="<%=keyword %>"/>
        &nbsp;
        	<input type="submit" value="搜索" class="inputsubmit"/>
        	<form method="post" action="index.jsp" class="formRadio">
        	<div style="clear:both;margin-left:230px;">
       <input type="radio" name="radio" value="score"  onclick="save()"/>相关度
       <input type="radio" name="radio" value="time" onclick="save()"/>时间
       <input type="radio" name="radio" value="hottop" onclick="save()"/>热度
       <!--input type="submit" value="提交"/-->
       </form>
       </div>
        	</form>
      <div>
       
      </div>
    </div>
    
    <div class="resultBoby">
    
        <div class="leftBoby">
        <div >
        <!-- 测试内容 -->
  
    	<%
    	 if(isResult)
    	 {%>
    	 	<a href="#">搜索结果</a> 找到相关内容<%=list.size() %>篇
    	 <%
    
    	 }
    	 %>
        
    </div>
            <%
            for(String o:list)
            {
             String path= parse.Path(strPath,o);
	    	 		ucas.JsonObject myObject = ucas.JsonUtil.readFile2JsonObject(path);
	    	 		String str = myObject.articalString;
	    	 		//LinkedList query=getSplit2Words(keyword);
	    	 		LinkedList<String> query=new parse.Segment(keyword).run();
	    	 		String partContent=AutoAbstract.getAbstract(str,query);
	    	 		map1.put(myObject.timeString,o.toString());
	    	 	    map2.put(myObject.totalString,o.toString());
	    	 	    
            }
	    	 if(isResult)
	    	 {
	    	 String radio = request.getParameter("radio");
	    	
	    	 if(radio.equals("score"))
	    	   {  
	    	   list=parse.Query(keyword);
	    	    }
	    	   else if(radio.equals("time"))
	    	   {
	    	   list=parse.List(map1);

	    	   
	    	      }
	    	   else{
	    	   list=parse.List(map2);
	    	   } 
	    	 	for(String o:list)
	    	 	{
	    	 		//Engine.ResultModel mod = (Engine.ResultModel)o;
	    	 		//Engine.Result mod=new Result(o+".txt");
	    	 		//ucas.JsonObject myObject = ucas.JsonUtil.readFile2JsonObject("/data/"+o+".json");
                   
	    	     String path= parse.Path(strPath,o);
	    	 		ucas.JsonObject myObject = ucas.JsonUtil.readFile2JsonObject(path);
	    	 		String str = myObject.articalString;
	    	 		//LinkedList query=getSplit2Words(keyword);
	    	 		LinkedList<String> query=new parse.Segment(keyword).run();
	    	 		String partContent=AutoAbstract.getAbstract(str,query);
	    	 		
	    	 	    
	    	 		
	    	 %>
	    	
	    	 	<div class="bobyTitle">
                        <a href="<%=myObject.URL%>" target="_blank">
                            <%= parse.HighLightKey(myObject.titleString) %></a></div>
                    <div class="bobyContent">
                        <%= parse.HighLightKey(partContent) %>
                    </div>
                    <div class="bobyUrl">
                        <span style="color: Gray;"><%=myObject.URL %></span>&nbsp;&nbsp;
                        <span><%=myObject.timeString %></span>>
                    </div>
	    	 <%
	    	 	}
	    	 }else
	    	 {
	    	 %>
             	<div class="bobyTitle">
                        
                    <div class="bobyContent">
                       	 没有查到任何东西！！！
                    </div>
               </div>    
             <%} %>      
               
            <div class="leftBobyPager">
                
            </div>
        </div>
        <div class="rightBoby">
            <table border="0">
            	<tr>
            		<td>
            			<form action="http://www.baidu.com/baidu">
							<input type=hidden value='<%=keyword %>' name=word>
							<input type="submit" value="Baidu 搜索">
							<input name=tn type=hidden value="bds">
							<input name=cl type=hidden value="3">
							<input name=ct type=hidden value="2097152">
							<input name=si type=hidden value="www.williamlong.info">
						</form>
            		</td>
            		<td>
	            		<form method=get action="http://www.google.com/search">
							<input type=hidden value='<%=keyword %>' name=q>
							<input type=submit name=btnG value="Google 搜索">
							<input type=hidden name=ie value=GB2312>
							<input type=hidden name=oe value=GB2312>
							<input type=hidden name=hl value=zh-CN>
							<input type=hidden name=domains value="www.williamlong.info">
							<input type=hidden name=sitesearch value="www.williamlong.info">
						</form>
            		</td>
            	</tr>
            </table>
                
        </div>
    </div>
    <div id="relativeSearch">
       
    </div>
   
    
</body>
</html>
