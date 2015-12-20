package ucas;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	public static String ReadFile(String Path){
		BufferedReader reader = null;
		String laststr = "";
		try {
			
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}
	/*
	 * 对于每个读取的文件返回Object对象
	 */
	public static JsonObject readFile2JsonObject(String path) {
		String JsonContext = JsonUtil.ReadFile(path);
		JSONArray jsonArray = JSONArray.fromObject("["+JsonContext+"]");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		JsonObject MyObject = new JsonObject(jsonObject.get("Artical").toString(), jsonObject.get("Time").toString(), 
						jsonObject.get("Total").toString(), 
						jsonObject.get("Title").toString(), jsonObject.get("URL").toString());
		
		return MyObject;
	}	
}
