package ucas;

import java.io.*;

public class DataProcess {

	public static void myReadFile(String filePath,String[] text){
	    try {
	            String encoding="UTF-8";
	            File file=new File(filePath);
	            if(file.isFile() && file.exists()){ //判断文件是否存在
	                InputStreamReader read = new InputStreamReader(
	                new FileInputStream(file),encoding);//考虑到编码格式
	                BufferedReader bufferedReader = new BufferedReader(read);
	                String line = null;
	                int i=0;
	                while((line = bufferedReader.readLine()) != null){ 
	                	if ( i != 0 && i != 11 )
	                	{
	                		if(line.length()<4)
	                		{
	                			text[i-1] += line;
	                			continue;
	                		}
	                		else if(line.charAt(4) != '\"')
	                		{
	                			text[i-1] += line;
	                			continue;
	                		}
	                	}
	                	text[i++] = line;
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
	
	public static void myWriteFile(String filePath,String[] text){
	    try {
	    	String encoding="UTF-8";
	    	FileOutputStream write = new FileOutputStream (filePath);
	    	OutputStreamWriter osw = new OutputStreamWriter(write,encoding);
	    	BufferedWriter bufferedwriter = new BufferedWriter(osw);
	        int len = text.length;
            for (int i=0; i<len ;i++)
            {
            	bufferedwriter.write(text[i]);
            	bufferedwriter.newLine();
            }
            bufferedwriter.close();
	    } catch (Exception e) {
	        System.out.println("写入文件内容出错");
	        e.printStackTrace();
	    }
	}
	
	public static void DataProcessFunc(String readFolderPath,String writeFolderPath,int begin,int end){
		String tempReadPath = readFolderPath;	//"D:\\temp\\";
		String tempWritePath = writeFolderPath;	//"D:\\temp2\\";
		String[] text = new String[12];
		for (int i=begin;i<=end;i++)
        {
        	String strtemp = String.valueOf(i);
        	tempReadPath += strtemp + ".json";
        	tempWritePath += strtemp + ".json";
            myReadFile(tempReadPath,text);
            text[1] = text[1].replace("\""," ");
            text[1] = text[1].substring(16);
            text[1] = "    \"Artical\": \"" + text[1];
            text[1] += "\",";
            myWriteFile(tempWritePath,text);
            tempReadPath = readFolderPath;
            tempWritePath = writeFolderPath;
            System.out.println(i);
        }
	}
	
	public static void main(String arg[])
	{
		//参数：输入文件夹路径，输出文件夹路径，开始文件号，结束文件号
		DataProcessFunc("data/jsons/","data/jsons2/",1,100000);
	}
}