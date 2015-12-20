package ucas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class IndexOperation {
	
	
	//返回HashMap，健为单词，词为倒排索引
	public static HashMap<String, String> getHashMap(String IndexPath) throws IOException
	{
		HashMap<String, String> resMap = new HashMap<String,String>();
		File f = new File(IndexPath);
		try {
			String s = null;
			BufferedReader rin = new BufferedReader(new FileReader(f));
			while ((s = rin.readLine()) != null) {
				String wordKey; // 要索引的单词
				String valueString; // 值
				String[] arrayStrings = s.split("&");
				wordKey = arrayStrings[0];
				valueString =arrayStrings[1];
				
				resMap.put(wordKey, valueString);
			}
			rin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resMap;
	}

	//构造函数，传入需要倒排索引记录，构造tfidf_index.txt使用
	public static String getTfidfString(String content) {
		String wordKey; //要索引的单词
		int numbersOfDocs; //出现次数
		String tfidfStrs; //tfidf的值
		tfidfStrs="";
		String[] arrayStrings = content.split("&");
		wordKey = arrayStrings[0];
		numbersOfDocs = Integer.parseInt(arrayStrings[2]);
		
		String[] docsListArray=arrayStrings[1].split("@");
		
		tfidfStrs+=wordKey;
		for (int i = 0; i < docsListArray.length; i++) {
			String[] valuesStrings = docsListArray[i].split("#");
			String filenameString = valuesStrings[0];
			int timesOfwords = Integer.parseInt(valuesStrings[1]);
		//	int numOfDoc = Integer.parseInt(valuesStrings[1]);
			
			DecimalFormat dec = new DecimalFormat("0.0000");
			double tfidf = Tf_idf( timesOfwords, numbersOfDocs);
			
			tfidfStrs+="&";
			tfidfStrs+=valuesStrings[0];
			tfidfStrs+="@";
			tfidfStrs+=dec.format(tfidf);
			if (i!=docsListArray.length) {
				tfidfStrs+="#next#";
			}
		}
		return tfidfStrs;
	}
	
	//返回某单词出现的文档列表
	public static void GetDocsList(String wordName){
		
	}
	
	//计算TF-IDF的公式
	public static double Tf_idf(int timeofWords,int timesOfDocs) {
		double d1 = ((double)Math.log10(timeofWords)+1.0) ;  //数值太小
		double d2= Math.log10(10000/timesOfDocs + 0.01);
		return d1*d2;
	}	
	
	//将Json转换成docs文档
	public static void Json2Docs() {
		String fileName = "";
		//读取文件
		for (int fileIndex = 1; fileIndex <= 100000; fileIndex++){
			fileName = fileIndex+".json";
			File mFile = new File("data/jsons2/"+fileName);
			if (!mFile.exists()) {
				System.out.println(fileName+"不存在");
				continue;
			}
			try {
				JsonObject Myobject = JsonUtil.readFile2JsonObject("data/jsons2/"+fileName);
				Myobject.fileName = fileName;  
				ReadAndWrite.writeFileByChars("data/docs/"+fileIndex+".txt", Myobject.articalString.toString());
				ReadAndWrite.WriteAppend("data/AttrIndex.txt",fileName +"\t"+ Myobject.timeString.toString() 
						+"\t"+Myobject.totalString.toString()+"\r\n");
				System.out.println(fileName);
			} catch (Exception e) {
				//continue;
				e.printStackTrace();
				// TODO: handle exception
			}
			
		//System.out.println("导出完成！！！");
		}
	}
}
