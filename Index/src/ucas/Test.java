package ucas;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Test {
	public static void main(String[] args) throws IOException 
	{
		
		//ReadAndWrite.CreateTfidf();
		Set<String> test =new HashSet<String>();
		test = IndexCreator.GetStopWordSet("data/stopwords.txt");
		System.out.println(test.size());
		for( Iterator it= test.iterator();  it.hasNext(); )
        {             
          //  System.out.println("value="+it.next().toString());            
        } 
	}
}
