import java.util.ArrayList;

public class Dealer {

	public Deck theDeck; 
	public ArrayList<Card> dealersHand;
	
	public Dealer() {
		theDeck = new Deck();
		dealersHand = new ArrayList<Card>();
	}
	
	public ArrayList<Card> dealHand() {
		if (theDeck.size() <= 34) {
			theDeck.newDeck();
		}
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(theDeck.remove(0));
		hand.add(theDeck.remove(0));
		hand.add(theDeck.remove(0));
		return hand;
	}
}
