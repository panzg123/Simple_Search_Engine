package parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class APcluster{

	static int dimension;
	int pa[];
	ArrayList<Integer> list;
	Map<Integer,List<Integer>> allPoint = new HashMap<Integer,List<Integer>>();
	public static double s[][];
	public static double r[][];
	public static double rold[][];
	public static double a[][];
	public static double aold[][];
	public static double m[][]; 
	double lamp = 0.5;
	static int iter = 100;
	static double MINVALUE = -100000.0;
	
	public void initialization(int [] p , int len){
		dimension = len;
		pa = new int[dimension];
		r = new double[dimension][dimension];
		rold = new double[dimension][dimension];
		a = new double[dimension][dimension];
		aold = new double[dimension][dimension];
		m = new double[dimension][dimension]; 
		s = new double[dimension][dimension];
		for (int i=0;i<dimension;i++){
			pa[i]=p[i];
		}
	}
	
	public void similarity(String foderPath){
		for(int i=0;i<dimension;i++){
			for(int j=i+1;j<dimension;j++){
				s[i][j] = DocSimilarity.ComputeDocSimilarity(foderPath,pa[i],pa[j]);
				s[j][i] = s[i][j];
			}		
		}
		this.average();
	}
	
	public void average(){
		double sum = 0.0;
		for(int i=0;i<dimension;i++){
			for(int j=0;j<dimension;j++){
				sum += s[i][j];
			}
		}
		
		sum = sum / (dimension*dimension);
		for(int i=0;i<dimension;i++){
			s[i][i] = sum;
		}
	}
	
	public void calRes(){
		for(int i=0;i<dimension;i++){	
			double max = MINVALUE;
			for(int k=0;k<dimension;k++){			
				for(int j=0;j<dimension;j++){
					if(j != k){
						if(max < a[i][j]+ s[i][j])
						{
							max = a[i][j]+ s[i][j];
						}
					}
				}
				r[i][k] = s[i][k] - max;
				max = MINVALUE;
			}
		}
		for(int i=0; i<dimension;i++){
			for(int j=0; j<dimension; j++){
				r[i][j] = (r[i][j]*(1-lamp) + rold[i][j]*lamp);
				rold[i][j] = r[i][j];
			}
		}
	}
	
	public void calAvail(){
		for(int i=0;i<dimension;i++){
			double sum = 0.0;
			for(int k=0;k<dimension;k++){
				if(i == k){
					for(int j=0;j<dimension;j++){
						if(j != k){
							if(r[j][k]>0){
								sum += r[j][k];
							}
						}
					}
					a[k][k] = sum;
					sum = 0.0;
				}
				else{
					for(int j=0;j<dimension;j++){
						if(j != k && j!=i){
							if(r[j][k]>0){
								sum += r[j][k];
							}
						}
					}
					a[i][k] = r[k][k] + sum;
					if(a[i][k] >0)
						a[i][k] = 0;
					sum = 0.0;
				}
			}
		}
		for(int i=0; i<dimension;i++){
			for(int j=0; j<dimension; j++){
				a[i][j] = (a[i][j]*(1-lamp) + aold[i][j]*lamp);
				aold[i][j] = a[i][j];
			}
		}
	}

	public Map<Integer,List<Integer>> Cluster(String foderPath,int [] p,int len) {	
		initialization(p,len);
		similarity(foderPath);
		for(int i=0;i<iter;i++){
			calRes();
			calAvail();
			System.out.println("Cluster iter:"+i+"/"+iter);
		}
		for(int i=0;i<dimension;i++){
			for(int j=0;j<dimension;j++){
				m[i][j] = a[i][j] + r[i][j];
			}
		}
		for(int i=0;i<dimension;i++){
			double max = MINVALUE;
			int loc = 0;
			for(int j=0;j<dimension;j++){
				if(max < m[i][j]){
					max = m[i][j];
					loc = j;
				}
			}
			if(pa[loc] != pa[i] ){
				if(allPoint.containsKey(pa[loc])){
					allPoint.get(pa[loc]).add(pa[i]);
				}else{
					list = new ArrayList<Integer>();
					list.add(pa[i]);
					allPoint.put(pa[loc], list);
				}
			}else{
				if( !allPoint.containsKey( pa[loc] ) ){
					list = new ArrayList<Integer>();
					allPoint.put(pa[loc], list);
				}
			}
		}
		return allPoint;
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis(); //获取开始时间
		System.out.println("note: program begins.");
		
		int len = 50;
		int []p = new int[len];
		for (int i=0;i<len;i++){
			p[i]=i+1;
		}
		
		APcluster apcluster = new APcluster();
		//输入：< 数据存放路径，存放文档编号的整型数组，数组长度 >
		//输出：< 返回聚类之后的结果，存放在map中，具体使用如下 >
		Map<Integer,List<Integer>> allPoint = apcluster.Cluster("D:\\temp\\",p,len);
		
		System.out.println("Map个数:"+allPoint.size());
		Iterator<Entry<Integer, List<Integer>>>  i = allPoint.entrySet().iterator();
		while(i.hasNext()){
			System.out.println("-----------------------------------------------------");
			Entry<Integer, List<Integer>> e = i.next();
		    System.out.println("中心点："+e.getKey());
		    for(int j=0;j<e.getValue().size();j++){
		    	System.out.println("普通点："+e.getValue().get(j));
			  } 
		}
		
		long endTime = System.currentTimeMillis(); //获取结束时间
		System.out.println("note: running time = " +(endTime - startTime) + "ms.");
		System.out.println("note: program ends.");
	}
}