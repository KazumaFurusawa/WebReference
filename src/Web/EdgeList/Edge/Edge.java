package Web.EdgeList.Edge;

/*
 * Reference recommendation level
 * 0-> Recommend 1 point
 * 1-> HighRecoomend 3 points
 * 2-> notRecommend -3 points
 * 
 */
public class Edge{
	private String personName;
	private int level;
	
	public Edge(String personName, int level) {
		super();
		this.personName = personName;
		this.level = level;
	}

	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * @param personName the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getReference(){
		switch(this.level){
		case 1: return "recommend";
		case 3: return "high recommend";
		case -3: return "not recommend";	
		}
		return null;
	}
	
	@Override
	public String toString(){
		return "("+this.personName+","+this.getReference()+")";
	}
	

	
}