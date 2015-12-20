package parse;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument.Iterator;

public class Parse {

	static int N = 10000;
	static String filepath = "C:\\test\\index.txt";//倒排索引文件的目录位置
	static HashMap<String, LinkedList<String>> WDocs = new HashMap<String, LinkedList<String>>();// <Term,Documents>
	static HashMap<String, Document> docs = new HashMap<String, Document>();
	static HashMap<String, Double> queryWeight = new HashMap<String, Double>();// <Term,Weight>
	static LinkedList<String> KeyWords = null;
	LinkedList<String> liss=new LinkedList<String>();
	static boolean flag = true;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method st
		TreeMap<String,String> tree=new TreeMap<String,String>(new Comparator(){

			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				return o2.toString().compareTo(o1.toString());
			}
			
		});
tree.put("1", "1");
tree.put("2", "2");

	
	}

	public static LinkedList<String> ANDList(LinkedList<String> docs1,
			LinkedList<String> docs2) {
		// System.out.println("list1:" + docs1);
		// System.out.println("list2:" + docs2);
		if (docs1 != null && docs2 != null) {
			LinkedList<String> list = new LinkedList<String>();
			for (int i = 0; i < docs1.size(); i++) {

				if (docs2.contains(docs1.get(i)))
					list.add(docs1.get(i));
			}
			return list;
		} else if (docs1 == null)
			return docs2;
		else
			return docs1;

	}

	public static void Init() {
		if (flag == true) {
			Load();
			flag = false;
		}

	}

	public static void Load() {
		new HashMap<String, LinkedList<Document>>();
		BufferedReader br = null;
		int n = 0;
		try {
			br = new BufferedReader(new FileReader(filepath));

			String line;
			LinkedList<String> list;

			int z = 0;
			while ((line = br.readLine()) != null) {
				n++;
				list = new LinkedList<String>();
				// System.out.println(line);
				// System.out.println(line);
				String[] msg = line.split("&");
				String term = msg[0].trim();// TERM
				for (int i = 1; i < msg.length; i++) {
					msg[i] = msg[i].replace("#next#", "");
					String[] info = msg[i].split("@");
					String doc = info[0].trim();// DoucmentTitle

					double weight = Double.valueOf(info[1]);

					Document dos = docs.get(doc);
					if (dos != null) {
						dos.setWeight(term, weight);
						docs.put(doc, dos);
					} else {
						dos = new Document();
						dos.setWeight(term, weight);
						docs.put(doc, dos);
					}
					list.add(doc);
				}
				// System.out.println("Term:"+term+"   list"+list);
				WDocs.put(term, list);
			}

			// java.util.Iterator<String> it = WDocs.keySet().iterator();
			// java.util.Iterator<String> iter = docs.keySet().iterator();
			// while (it.hasNext()) {
			// String key = it.next();
			// System.out
			// .println("Key:" + key + "    Value:" + WDocs.get(key));
			// }
			// System.out.println("SIZE:" + docs.size());
			// while (iter.hasNext()) {
			// String key = iter.next();
			// System.out.println("key:" + key);
			// HashMap<String, Double> has = docs.get(key).terms;
			// java.util.Iterator<String> ir = has.keySet().iterator();
			// System.out.println("size:" + has.size());
			// while (ir.hasNext()) {
			// String k = ir.next();
			// System.out
			// .println("term:" + k + "    weight:" + has.get(k));
			// }
			// }

		} catch (IOException e) {
			e.printStackTrace();
		}
		N = n;
		System.out.println("载入内存中文档的数目为：" + n);
		System.out.println("我在执行中");
	}

	public static LinkedList<String> Query(String query) throws IOException {
		LinkedList<String> list1 = null;
		LinkedList Quer = new Segment(query).run();
		queryWeight.clear();
		LinkedList lis = new LinkedList();
		for (int i = 0; i < Quer.size(); i++) {
			System.out.println(Quer.get(i) + "|");
			if (WDocs.containsKey(Quer.get(i))) {

				System.out.println("倒排索引中有：" + Quer.get(i));
				if (queryWeight.containsKey(Quer.get(i))) {
					String key = (String) Quer.get(i);
					double count = queryWeight.get(key);
					count++;
					queryWeight.put(key, count);
				} else {
					String key = (String) Quer.get(i);
					queryWeight.put(key, 1.0);
				}

				lis.add((String) Quer.get(i));//
			} else {
				System.out.println("倒排索引中没有：" + Quer.get(i));
			}

		}
		System.out.println();
		KeyWords = lis;
		System.out.println("索引中的关键词有：" + lis);
		// Start Compute the weight of terms
		java.util.Iterator<String> it = queryWeight.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (WDocs.containsKey(key)) {
				int n = WDocs.get(key).size();
				double idf = Math.log10(N / n);
				double tf = queryWeight.get(key);
				double w = (1 + Math.log10(tf)) * idf;
				queryWeight.put(key, w);
				// System.out.println("Term:"+key+"    weight:"+w);
			}
		}

		// End Compute the weight of terms;

		if (lis.size() > 0) {
			LinkedList list = WDocs.get(lis.get(0));

			for (int i = 1; i < lis.size(); i++) {

				if (WDocs.containsKey(lis.get(i))) {
					list = ANDList(WDocs.get(lis.get(i)), list);// 閹垫儳鍤柅鍌氭値閻ㄥ嫭鏋冨锝夋肠閸氾拷
				}
			}

			if (list != null && list.size() > 1) {
				list1 = Top10(lis, list);// lis娴狅綀銆冮惃鍕Ц閹碉拷瀵橀崥顐ゆ畱閸楁洝鐦濋弫甯礉閼板ist娴狅綀銆冮惃鍕Ц閸氬牓锟介惃鍕瀮濡楋絾鏆�
			} else if (list.size() == 1) {
				// System.out.println(list);
				list1 = list;
			} else {
				System.out.println("没有所需的文档");

			}
		} else
			System.out.println("No Suitable Documents");

		System.out.println("返回的文档有:" + list1);
		return list1;
	}

	public static double Similarity(LinkedList query, String doc) {
		java.util.Iterator<String> it = docs.get(doc).terms.keySet().iterator();
		LinkedList list = new LinkedList();
		double d = 0.0;
		while (it.hasNext()) {
			String term = it.next();
			d = Weight(doc, term) * Weight(doc, term) + d;
			list.add(term);
		}
		d = Math.sqrt(d);
		double q = 0.0;
		for (int i = 0; i < query.size(); i++) {
			String term = (String) query.get(i);
			double weight = queryWeight.get(term);
			q = q + weight * weight;
		}
		q = Math.sqrt(q);
		list = ANDList(query, list);// 閹垫儳鍤�鍐ф粦閸忓崬鎮撻崥顐ｆ箒閻ㄥ嫯鐦濋弶锟�
		double sim = 0.0;
		for (int i = 0; i < list.size(); i++) {
			String term = (String) list.get(i);
			sim = sim + Weight(doc, term) * queryWeight.get(term);
		}
		double score = sim / d / q;
		return score;
	}

	public static double Weight(String doc, String term) {
		Document dos = docs.get(doc);
		double weight = dos.getWeight(term);
		// System.out.println("Term:" + term + "     WEIGHT:" + weight);
		return weight;
	}

	public static LinkedList<String> Top10(LinkedList query, LinkedList lists) {
		Map map = new TreeMap();
		LinkedList<String> list1 = new LinkedList<String>();
		for (int i = 0; i < lists.size(); i++) {
			String docname = (String) lists.get(i);
			double score = Similarity(query, docname);
			// System.out.println("Score=" + score);
			map.put(docname, score);
		}

		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(
				map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Entry<String, Double> o1,
					Entry<String, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		int i = 0;
		for (Map.Entry<String, Double> mapping : list) {
			if ((i++) < 30) {
				// System.out.println(mapping.getKey()+":"+mapping.getValue());
				list1.add(mapping.getKey());
			} else
				break;
		}

		return list1;
	}



	public String HighLightKey(String content) {
		content = content.replaceAll(" ", "");
		for (int i = 0; i < KeyWords.size(); i++) {
			String word = KeyWords.get(i).toString();
			content = content.replaceAll(word,
					"<font style='color:#ff0000;font-weight:bold;'>" + word
							+ "</font>");
		}

		return content.replaceAll(
				"</font>[\\W]*<font style='color:#ff0000;font-weight:bold;'>",
				"");
	}

	public String Path(String path, String o) {

		o = o.replace(".txt", ".json");
		String data = "data\\";
		path = path + data;
		return path + o;
	}

    public LinkedList<String> List(TreeMap<String,String> tree)
    {
    	LinkedList<String> link=new LinkedList<String>();
 
    	List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String,String>>(
				tree.entrySet());
       for(Map.Entry<String, String> mapping:list)
       {
    	   link.add(mapping.getValue());
    	   System.out.println("文档："+mapping.getValue());
       }
       System.out.println("LINK:"+link);
       return link;
    }
    
    public void PRINT(String str)
    {
    	System.out.println("这里的输出结果是："+str);
    }
    public LinkedList<String> LinkList()
    {
    	return liss;
    }
}
