package ucas;

import java.lang.Character.Subset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class AutoAbstract {
	public static String getAbstract(String content,LinkedList wordsArray) {
		Set<Integer> posSet = new TreeSet();
		String ResultStr="";
		//String result = IKAnalzyerDemo.Spilt2Words(Query);
		//String result ="";
		//String[] wordsArray = result.split("\\|");
		for (int i = 0; i < wordsArray.size(); i++) {
			int myIndex=0,prePos=0;
			String fullContent=content;
			myIndex=fullContent.indexOf(wordsArray.get(i).toString());
			while(myIndex>0)
			{	
				prePos += myIndex;
				posSet.add(prePos);
				fullContent = fullContent.substring(myIndex+1);
				myIndex = fullContent.indexOf(wordsArray.get(i).toString());
			}	
		}
		int preIndex,nowIndex;
		preIndex=0;
		for(Integer i: posSet){
			nowIndex=i;
			System.out.println(nowIndex);
			//将位置的前后五个字写入摘要
			if((nowIndex - preIndex)<5) ResultStr+=content.substring(nowIndex,nowIndex+5);
			else if((nowIndex - preIndex)<10) ResultStr+=content.substring(preIndex+5,nowIndex+5);
			else { 
				ResultStr+=".....";
				ResultStr+=content.substring(nowIndex-5,nowIndex+5);
			}
			preIndex = nowIndex;
			System.out.println(i);
		}
		return ResultStr;
	}
	
}
