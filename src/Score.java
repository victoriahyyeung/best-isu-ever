
public class Score implements Comparable <Score>{

	
	String username;
	int points;
	
	public Score (String username, int points) {
		this.username = username;
		this.points = points;
	}
	
	public int compareTo (Score s){
		return s.points - this.points;
	}
	
	public static void main(String[] args) {
		

	}

}
