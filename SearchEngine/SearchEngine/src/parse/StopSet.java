package parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * @author Administrator
 *
 */
public class StopSet extends HashSet<String>
{
	static final long serialVersionUID = 999568822134L;
	private static StopSet stopSet = new StopSet();

	/**
	 * 
	 * @return the singleton of basic dictionary
	 */
	public static StopSet getInstance()
	{
		return stopSet;
	}

	public static String getDir()
	{
		String dir = System.getProperty("dic.dir");
		if (dir == null)
			dir = "/dic/";
		else if( !dir.endsWith("/"))
			dir += "/";
		return dir;
	}
	
	private StopSet()
	{

		super(1000);
		String sParagraph;
		try{
			//BufferedReader fpSource = 
			//	new BufferedReader(new FileReader(System.getProperty("dic.dir")+"/stopword.txt"));
			String dic = "/stopword.txt";
			InputStream file = null;
			if (System.getProperty("dic.dir") == null)
				file = getClass().getResourceAsStream(getDir()+dic);
			else
				file = new FileInputStream(new File(getDir()+dic));
			
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(file,"GBK"));
			
	    	while( true )
	    	{
	    		sParagraph = in.readLine();
	    		if (sParagraph == null )
	    			break;
	    		
				if (!"".equals(sParagraph))
				{
					this.add(sParagraph);
				}
	    	}
	    	in.close();
		}catch (Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
}
