import java.util.ArrayList;

public class Player {

	public ArrayList<Card> hand; 
	public int anteBet;
	public int playBet;
	public int pairPlusBet;
	public int totalWinnings; 
	
	public Player() {
		hand = new ArrayList<Card>();
	}
}
