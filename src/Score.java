
public class Score implements Comparable <Score>{

	
	String username;
	int points;
	
	
	// Description: Constructor for Score class, creates a score entry with username and point value
		// Parameters: the player's username, the player's score
		// Return: none
	public Score (String username, int points) {
		this.username = username;
		this.points = points;
	}
	
	// Description: Compares this score to another score for sorting (higher points come first)
		// Parameters: the score to compare against
		// Return: positive if this score is less than the other, negative if greater, 0 if equal
	public int compareTo (Score s){
		return s.points - this.points;
	}
	
	public static void main(String[] args) {
		

	}

}
