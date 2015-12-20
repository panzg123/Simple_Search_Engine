package APcluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jeasy.analysis.MMAnalyzer;

import java.io.*;

class DocSimilarity {  
	
	public static double ComputeDocSimilarity(String folderPath,int docid1,int docid2) 
	{
		if (docid1 == docid2)
			return -1.0;
		String[] text1 = new String[1];
		String[] text2 = new String[1];
		String path1 = String.valueOf(docid1);
		String path2 = String.valueOf(docid2);
		path1 = folderPath+path1 + ".txt";
		path2 = folderPath+path2 + ".txt";
        MyReadFile(path1,text1);
        MyReadFile(path2,text2);
        //System.out.println(text1[0]);
        //System.out.println(text2[0]);
        MMAnalyzer analyzer = new MMAnalyzer();
		Map<String,Integer> map1 = new HashMap<String,Integer>();
		Map<String,Integer> map2 = new HashMap<String,Integer>();
		try 
		{
			String[] str1 = analyzer.segment(text1[0], " ").split(" ");
			String[] str2 = analyzer.segment(text2[0], " ").split(" ");
			//System.out.println("doc1词数:"+str1.length);
			for(int j=0;j<str1.length;j++){
				String temp = str1[j];
				if (temp.length() == 1){//去除单个字符
					continue;
				}
				int tempCount=0;
				if ( map1.containsKey(temp) ){
					tempCount=map1.get(temp);
					tempCount++;
				}
				else{
					tempCount = 1;
				}
				map1.put(temp, tempCount);
			}
			//System.out.println("doc2词数:"+str2.length);
			for(int j=0;j<str2.length;j++){
				String temp = str2[j];
				if (temp.length() == 1){//去除单个字符
					continue;
				}
				int tempCount=0;
				if ( map2.containsKey(temp) ){
					tempCount=map2.get(temp);
					tempCount++;
				}
				else{
					tempCount = 1;
				}
				map2.put(temp, tempCount);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		//System.out.println("doc1 map size:"+map1.size());
		//System.out.println("doc2 map size:"+map2.size());

		List<Map.Entry<String, Integer>> list1 = sortMap(map1);
		List<Map.Entry<String, Integer>> list2 = sortMap(map2);
		return CosinSimilarity(list1,list2);
	}
	
	//read file
	public static void MyReadFile(String filePath,String[] text){
	    try {
	            String encoding="UTF-8";
	            File file=new File(filePath);
	            if(file.isFile() && file.exists()){ //判断文件是否存在
	                InputStreamReader read = new InputStreamReader(
	                new FileInputStream(file),encoding);//考虑到编码格式
	                BufferedReader bufferedReader = new BufferedReader(read);
	                String lineTxt = null;
	                while((lineTxt = bufferedReader.readLine()) != null){
	                    //System.out.println(lineTxt);
	                	text[0]+=lineTxt;
	                }
	                read.close();
	    }else{
	        System.out.println("找不到指定的文件");
	    }
	    } catch (Exception e) {
	        System.out.println("读取文件内容出错");
	        e.printStackTrace();
	    }
	}	
	
	//cosin similarity
	public static double CosinSimilarity(List<Map.Entry<String, Integer>> list1,List<Map.Entry<String, Integer>> list2){
		int index1=0;
		int index2=0;
		int len1=list1.size();
		int len2=list2.size();
		int cmpResult;
		double sim=0.0;
		double r1=0.0;
		double r2=0.0;
		double r3=0.0;
		while( index1<len1 && index2<len2 ) {
			cmpResult = ( list1.get(index1).getKey() ).compareTo( list2.get(index2).getKey() );
			if ( cmpResult == 0 ) //相等
			{
				r3 += (double)( list1.get(index1).getValue() * list2.get(index2).getValue() );
				r1 += (double)( list1.get(index1).getValue() * list1.get(index1).getValue() );
				r2 += (double)( list2.get(index2).getValue() * list2.get(index2).getValue() );
				index1++;
				index2++;
			}
			else if ( cmpResult > 0 ) //index1>index2
			{
				r2 += (double)( list2.get(index2).getValue() * list2.get(index2).getValue() );
				index2++;
			}
			else if ( cmpResult < 0 ) //index1<index2
			{
				r1 += (double)( list1.get(index1).getValue() * list1.get(index1).getValue() );
				index1++;
			}
		}
		r1 = Math.sqrt(r1);
		r2 = Math.sqrt(r2);
		if ( r1*r2 == 0 )
			return 0.0;
		sim = r3/(r1*r2);	
		return sim;		
	}
	
	//排序包含分词的Map
	public static List<Map.Entry<String, Integer>> sortMap(Map<String,Integer> map){
		List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		//排序
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {   
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
		        //return (o2.getValue() - o1.getValue()); 
		        return ( o1.getKey() ).toString().compareTo( o2.getKey() );
		    }
		});
		return infoIds;
	}
}
