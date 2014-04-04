package Web.EdgeList;

import java.util.ArrayList;

import Web.EdgeList.Edge.Edge;

/**
 * 
 * @author Kazuma
 * EdgeList is ordered y, and containts Edge(personName,reference)
 * it is not request
 *
 */
public class EdgeList extends ArrayList<Edge> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param edge
	 * @return
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
	 * get the index of the array by personName
	 * @param targetName
	 * @return index
	 */
	public int getIndexbyPersonName(String targetName){
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
	 * get Edge by personName
	 * @param targetName
	 * @return edge
	 */
	public Edge getEdgebyPersonName(String targetName){
		int index= this.getIndexbyPersonName(targetName);
		if(index != -1)
		return this.get(index);
		else {
			return new Edge("null", -1);
		}
	}
	
	/**
	 * remove Edge by personName
	 * @param targetName
	 * @return
	 */
	public boolean removeEdgebyPersonName(String targetName){
		int index = getIndexbyPersonName(targetName);
		if(index !=-1){
			this.remove(index);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * change the reference
	 * old edge and new edge: their personName is different
	 * sort is required.
	 * @param edge
	 * @return
	 */
	public boolean setEdge(Edge edge){
		int index = getIndexbyPersonName(edge.getPersonName());
		if(index !=-1){
			this.set(index, edge);
			return true;
		}
		return false;
	}


	

}