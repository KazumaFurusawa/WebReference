package Web.EdgeList;

import java.util.ArrayList;

import Web.EdgeList.Edge.Edge;

/**
 * 
 * @author Kazuma
 * EdgeList is ordered by y, and containts Edge(personName,reference)
 * it is not request
 *
 */
public class EdgeList extends ArrayList<Edge> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Add new edge to the array.
	 * @param edge
	 * @return boolean
	 */
	public boolean addEdge(Edge edge){
		if(this.size()==0){
			this.add(edge);
			return true;
		}
		String personName = edge.getPersonName();
		int tail = this.size()-1;
		int head =0;
		int mid =0;
		while(head<=tail){
			mid =(tail+head)/2;
			int checker = this.get(mid).getPersonName().compareTo(personName);
			if(checker == 0){
				return false;
			}
			else if(checker >0){
				tail = mid-1;
			}
			else{
				head = mid+1;	
			}
		}
		int checker = this.get(mid).getPersonName().compareTo(personName);
		if(checker >0){
			this.add(mid, edge);
		}
		else{
			this.add(mid+1,edge);
		}
		return true;
	}
	/**
	 * Get the index of the array with personName
	 * @param String: targetName
	 * @return int index of the array.
	 */
	public int getIndexWithPersonName(String targetName){
		int tail = this.size()-1;
		int head =0;
		//System.out.println(tail);
		while(head<=tail){
			int mid =(tail+head)/2;
			int checker = this.get(mid).getPersonName().compareTo(targetName);
			if(checker == 0){
				return mid;
			}
			else if(checker >0){
				tail = mid-1;
			}
			else{
				head = mid+1;
			}
		}
		return -1;
	}	
	
	/**
	 * Get Edge with personName
	 * @param String targetName
	 * @return Edge
	 */
	public Edge getEdgeWithPersonName(String targetName){
		int index= this.getIndexWithPersonName(targetName);
		if(index != -1)
		return this.get(index);
		else {
			return new Edge("null", -1);
		}
	}
	
	/**
	 * Remove Edge by personName
	 * @param String targetName
	 * @return Boolean
	 */
	public boolean removeEdgeWithPersonName(String targetName){
		int index = getIndexWithPersonName(targetName);
		if(index !=-1){
			this.remove(index);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Change the reference
	 * @param Edge
	 * @return Boolean
	 */
	public boolean setEdge(Edge edge){
		int index = getIndexWithPersonName(edge.getPersonName());
		if(index !=-1){
			this.set(index, edge);
			return true;
		}
		return false;
	}


	

}