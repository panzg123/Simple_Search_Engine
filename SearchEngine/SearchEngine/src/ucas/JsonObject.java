package ucas;

public class JsonObject {
	public String articalString;
	public String timeString;
	public String totalString;
	public String titleString;
	public String fileName;
	public String URL;
	
	public JsonObject(String artical,String time,String total,String title,String url) {
		this.articalString = artical;
		this.timeString = time;
		this.totalString = total;
		this.titleString = title;
		this.URL = url;
	}
}
