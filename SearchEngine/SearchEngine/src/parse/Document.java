package parse;

import java.util.HashMap;
import java.util.LinkedList;

public class Document {
	HashMap<String, Double> terms = new HashMap<String, Double>();

	public int size() {
		return terms.size();
	}
	public void setWeight(String term,double weight)
	{

		terms.put(term, weight);
	}
	public double getWeight(String term)
	{if(terms.containsKey(term))
		return terms.get(term);
	else
		return 0;
	}
}
