package Web;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import Web.EdgeList.EdgeList;
import Web.EdgeList.Edge.Edge;
import Web.FileReadWrite.FileReadWrite;
import Web.GetReport.GetReport;
public class Web {
	private TreeMap<String, EdgeList> adjHash; 
	
	//
	public Web() {
		adjHash = new TreeMap<String,EdgeList>();
	}
	
	public TreeMap<String, EdgeList> getAdjHash() {
		return adjHash;
	}
	
	public FileReadWrite toFileReadWrite(){
		return new FileReadWrite(this);
	}
	public GetReport toGetReport(){
		return new GetReport(this);
	}
	

	/**
	 * 
	 * @param personName: input personName
	 * @return success or not
	 */
	public boolean addPerson(String personName) {
		try{
			if(new String(personName).trim().equals("")){
				return false;
			}
			
			if(!adjHash.containsKey(personName)){
				adjHash.put(personName, new EdgeList());
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @param personName: input personName
	 * @param edge: input reference
	 * @return boolean
	 */
	public boolean addReference(String personName, Edge edge){
		try{
			if(new String(edge.getPersonName().trim()).equals("")){
				return false;
			}
			
			if(!adjHash.containsKey(edge.getPersonName())){
				return false;
			}
				
			if(adjHash.get(personName).contains(edge.getPersonName()) || (personName.equals(edge.getPersonName()))){
				return false;
			}
			else {
				adjHash.get(personName).addEdge(edge);
				return true;
			}		
		}catch (Exception e) {
			return false;
		}
	}
	/**
	 * Delete person from the web.
	 * @param personName
	 * @return boolean
	 */
	public boolean deletePerson(String personName){
		try{
			if(!adjHash.containsKey(personName)){
				return false;
			}else{
				adjHash.remove(personName);
				for(Iterator<Entry<String, EdgeList>> iterator = adjHash.entrySet().iterator();iterator.hasNext();){
					iterator.next().getValue().removeEdgebyPersonName(personName);
				}
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	/**
	 * delete the reference from the web.
	 * @param personName
	 * @param targetPerson
	 * @return
	 */
	public boolean deleteReference(String personName, String target){
		try{
			if(adjHash.get(personName).removeEdgebyPersonName(target))
				return true;
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	/**
	 * change the reference of the web.
	 * @param personName
	 * @param edge
	 * @return
	 */
	public boolean changeReference(String personName, Edge edge){
		try{
			if(adjHash.get(personName).setEdge(edge))
				return true;

		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	/**
	 * output given reference of the person.
	 * @param personName
	 * @return String givenReference of the person.
	 */
	public String givenReference(String personName){
		return adjHash.get(personName).toString();
	}
	
	/**
	 * get the sum of received reference
	 * @param personName
	 * @return sum of the reference
	 */
	public int getReceivedRefSum(String personName){
		int sum = 0;
		Iterator<Entry<String, EdgeList>> iterator = adjHash.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,EdgeList> entry = iterator.next();
			int index = entry.getValue().getIndexbyPersonName(personName);
			if(index != -1){
				sum += entry.getValue().get(index).getLevel();
			}
		}
		return sum;
	}
	
	/**
	 * 
	 * @param personName
	 * @return
	 */
	public String getReceivedReference(String personName){
		EdgeList edgeList = new EdgeList();
		for(Iterator<Entry<String,EdgeList>> iterator = adjHash.entrySet().iterator();iterator.hasNext();){
			Entry<String,EdgeList> entry = iterator.next();
			int index = entry.getValue().getIndexbyPersonName(personName);
			if(index != -1){
				Edge edge = entry.getValue().get(index);
				edgeList.add(new Edge(entry.getKey(),edge.getLevel()));
			}
		}
		return edgeList.toString();
	}
	
	/**
	 * The person received highly recommendation from anybody or not.
	 * @param personName
	 * @return boolean
	 */
	public boolean ishighlyRecommend(String personName){
		for(Iterator<Entry<String,EdgeList>> iterator = adjHash.entrySet().iterator();iterator.hasNext();){
			Entry<String,EdgeList> entry = iterator.next();
			for(Iterator<Edge> edgeIterator = entry.getValue().iterator();edgeIterator.hasNext();){
				Edge edge = edgeIterator.next();
				if(edge.getPersonName().equals(personName) && edge.getLevel() ==3){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param personName
	 * @return
	 */
	public String allReferencesOf(String personName){
		String str = new String();
		for(Iterator<Entry<String,EdgeList>> iterator = adjHash.entrySet().iterator();iterator.hasNext();){
			Entry<String,EdgeList> entry = iterator.next();
			for(Iterator<Edge> edgeIterator = entry.getValue().iterator();edgeIterator.hasNext();){
				Edge edge = edgeIterator.next();
				if(edge.getPersonName().equals(personName) && edge.getLevel() ==3){

				}
			}
		}
		
		return str;
	}
	
	
	public String toString(){
		return adjHash.toString();
	}
}