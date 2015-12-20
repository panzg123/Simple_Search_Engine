package ucas;

public class JsonObject {
	String articalString;
	String timeString;
	String totalString;
	public String titleString;
	String fileName;
	String URL;
	
	public JsonObject(String artical,String time,String total,String title,String url) {
		articalString = artical;
		timeString = time;
		totalString = total;
		titleString = title;
		URL = url;
	}
}
