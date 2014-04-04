package Web.GetReport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import Web.Web;
import Web.EdgeList.EdgeList;
import Web.EdgeList.Edge.Edge;

public class GetReport {
	
	Web web = new Web();
	private TreeMap<String, EdgeList> adjHash;
	
	public GetReport(Web web){
		this.web = web;
		adjHash = web.getAdjHash();
	}
	
	/**
	 * get the report of input person.
	 * @param personName
	 * @return
	 */
	public String getPersonReportOf(String personName){
		return "{" +  personName + "=: The total received score is " + web.getReceivedRefSum(personName)+ ",\n"
				+ " The givenReference: " + web.givenReference(personName)+",\n"
				+ " The receivedReference: " + web.getReceivedReference(personName) +"\n"
				+"}";
	}
	
	/**
	 * This method get all highly recommend persons
	 * @return
	 */
	public String getAllHighlyRecommend(){
		String str = new String("{");
		EdgeList edgeList2 = new EdgeList();
		for(Iterator<Entry<String, EdgeList>> iterator = adjHash.entrySet().iterator();iterator.hasNext();){
			for(Iterator<Edge> iterator2 = iterator.next().getValue().iterator();iterator2.hasNext();){
				Edge edge = iterator2.next();
				int check = edgeList2.getIndexbyPersonName(edge.getPersonName());
				String check2 = String.valueOf(check);
				if(edge.getLevel() == 3 && check2.equals("-1")){
					str += edge.getPersonName() +  ", ";
					edgeList2.add(new Edge(edge.getPersonName(), 3));			
				}
			}
		}
		int ind = str.lastIndexOf(", ");
	    str = new StringBuilder(str).replace(ind, ind+1,"").toString().trim();
		return str+"}";
	}
	
	/**
	 * get all received reference of person.
	 * It is ordered by highly recommended recommended, not recommended
	 * @param personName
	 * @return getAllReference of input personName
	 */
	public String getAllReferenceOf(String personName){
		EdgeList recommendEdgeList = new EdgeList();
		EdgeList highlyRecommendEdgeList = new EdgeList();
		EdgeList notRecommendEdgeList = new EdgeList();
		String string = new String();
		for(Iterator<Entry<String, EdgeList>> iterator = adjHash.entrySet().iterator();iterator.hasNext();){
			Entry<String, EdgeList> entry = iterator.next();
			String targetNameString = entry.getKey();
			Edge edge = entry.getValue().getEdgebyPersonName(personName);
			switch(edge.getLevel()){
				case 1: recommendEdgeList.add(new Edge(targetNameString,edge.getLevel()));
						break;
				case 3: highlyRecommendEdgeList.add(new Edge(targetNameString,edge.getLevel()));
						break;
				case -3: notRecommendEdgeList.add(new Edge(targetNameString,edge.getLevel()));
						break;
				case -1: 
						break;
				default:
						break;
			}
		}
		if(!(recommendEdgeList.isEmpty())){
			string +=recommendEdgeList.toString()+"\n";
		}
		if(!(highlyRecommendEdgeList.isEmpty())){
			string +=highlyRecommendEdgeList.toString()+"\n";
		}
		if(!(notRecommendEdgeList.isEmpty())){
			string +=notRecommendEdgeList.toString();
		}
		return  string;
	}
	/**
	 * get all given reference by person
	 * @param personName
	 * @return getAllReferenceBy person
	 */
	public String getAllReferenceBy(String personName){
		String string = new String();
		EdgeList recommendEdgeList = new EdgeList();
		EdgeList highlyRecommendEdgeList = new EdgeList();
		EdgeList notRecommendEdgeList = new EdgeList();
		
		Iterator<Edge> iterator = adjHash.get(personName).iterator();
		while(iterator.hasNext()){
			Edge edge = iterator.next();
			switch(edge.getLevel()){
				case 1: recommendEdgeList.add(edge);
						break;
				case 3: highlyRecommendEdgeList.add(edge);
						break;
				case -3: notRecommendEdgeList.add(edge);
						break;
				case -1: 
						break;
				default:
						break;
			}
		}
		
		if(!(recommendEdgeList.isEmpty())){
			string +=recommendEdgeList.toString()+"\n";
		}
		if(!(highlyRecommendEdgeList.isEmpty())){
			string +=highlyRecommendEdgeList.toString()+"\n";
		}
		if(!(notRecommendEdgeList.isEmpty())){
			string +=notRecommendEdgeList.toString();
		}
		return string;
	}
	
	/**
	 * get TopX
	 * @param length(X)
	 * @return String: Top X;
	 */
	public String getTopX(int length){
		String string = new String();
		ArrayList<Edge> list = new ArrayList<Edge>();
		Iterator<Entry<String, EdgeList>> iterator = adjHash.entrySet().iterator();
		while(iterator.hasNext()){
			String personName = iterator.next().getKey();
			list.add(new Edge(personName, web.getReceivedRefSum(personName)));
		}
		
		Edge[] edges = new Edge[list.size()];
		for(int i = 0; i<list.size();i++){
			edges[i] = list.get(i);
		}
		quickSort(edges, 0, edges.length-1);
		string+="{";
		for(int i=0;i<list.size() && i<length;i++){
			string += "("+ edges[i].getPersonName() + "="+ edges[i].getLevel()+")";
			if(i==list.size()-1 || i==length-1){
				string+="}";
			}
			else{
				string+=",";
			}
		}
		return string;
	}
	
	/**
	 * QuickSort
	 * @param arr
	 * @param left
	 * @param right
	 */
	private void quickSort(Edge[] arr, int left, int right){
        if (left <= right) {
            int p = arr[(left+right) / 2].getLevel();
            int l = left;
            int r = right;
            
            while(l <= r) {
                while(arr[l].getLevel() > p){ l++; }
                while(arr[r].getLevel() < p){ r--; }
                
                if (l <= r) {
                    Edge tmp = arr[l];
                    arr[l] = arr[r];
                    arr[r] = tmp;
                    l++; 
                    r--;
                }
            }
            
            quickSort(arr, left, r);
            quickSort(arr, l, right);
        }
    }
	
	/**
	 * get stat of the web.
	 * @return string: stat of the web.
	 */
	public String getStat(){
		String str= new String();
		int total = 0;
		int suggest = 0;
		int highlySuggest =0;
		int notSuggest =0;
		for(Iterator<Entry<String, EdgeList>> iterator = adjHash.entrySet().iterator(); iterator.hasNext();){
			for(Iterator<Edge> edgeIterator = iterator.next().getValue().iterator();edgeIterator.hasNext();){
				Edge edge = edgeIterator.next();
				total++;
				switch (edge.getLevel()) {
				case 1:
					suggest++;
					break;
				case 3:
					highlySuggest++;
					break;
				case -3:
					notSuggest++;
				default:
					break;
				}
			}
		}
		str += "Total Reference is "+ total +"\n";
		str += "Total suggest reference is " + suggest +"\n";
		str += "Total Highly Suggest Reference is " + highlySuggest + "\n";
		str += "Total Not Suggest Reference is " + notSuggest +"\n";
		return str;
	}
}
