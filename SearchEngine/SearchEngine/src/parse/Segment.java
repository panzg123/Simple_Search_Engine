package parse;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Segment {
	String query;
   public Segment(String query)
   {
	   this.query=query;
   }
   public LinkedList<String> run() throws IOException
   {  

		Analyzer analyzer = new IKAnalyzer(true);
		LinkedList<String> list=new LinkedList<String>();

	    TokenStream ts = null;
		try {

			ts = analyzer.tokenStream("myfield", new StringReader(query));

		    CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
		    	    
			ts.reset(); 
			//杩唬鑾峰彇鍒嗚瘝缁撴灉
			while (ts.incrementToken()) {
			list.add(term.toString());
			}
			//鍏抽棴TokenStream锛堝叧闂璖tringReader锛�
			ts.end();   // Perform end-of-stream operations, e.g. set the final offset.

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//閲婃斁TokenStream鐨勬墍鏈夎祫婧�
			if(ts != null){
		      try {
				ts.close();
		      } catch (IOException e) {
				e.printStackTrace();
		      }
			}
	    }
		return list;
     
   }
}
