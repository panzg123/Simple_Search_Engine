package ucas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadAndWrite {
	public static String readFileByChars(String fileName,String encoding) {
		 File f=new File(fileName);
		 StringBuffer result;
		    try {
		        String s=null;
		         result=new StringBuffer();
		        FileInputStream in=new FileInputStream(f);
		        InputStreamReader r=new InputStreamReader(in,encoding);
		        BufferedReader rin=new BufferedReader(r);
		        while((s=rin.readLine())!=null){
		            //System.out.println(s.getBytes("utf-8"));
		            result.append(s);
		           }
		        rin.close();
		        return result.toString();
		    }catch (FileNotFoundException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		     catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		     return "";
	    }
	public static void writeFileByChars(String fileName,String value)
	{
		
	      String path = fileName;  
	      ByteBuffer bb = ByteBuffer.wrap(value.getBytes());
	      value = null;
	      FileChannel out2;
		try {
			out2 = new FileOutputStream(path).getChannel();
			out2.write(bb);
			bb.clear();
			bb = null;
			out2.close();
		}  catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	       
	     
	      
	}
	public static void WriteAppend(String fileName, String value){
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(value);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	}
