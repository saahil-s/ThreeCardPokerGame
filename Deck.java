import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Deck() {
		char[] suits = {'C', 'D', 'S', 'H'};
		for (char c : suits) {
			for (int i = 2; i <= 14; i++) {
				this.add(new Card(c, i));
			}
		}
		Collections.shuffle(this);
	}
	
	public void newDeck() {
		this.clear();
		char[] suits = {'C', 'D', 'S', 'H'};
		for (char c : suits) {
			for (int i = 2; i <= 14; i++) {
				this.add(new Card(c, i));
			}
		}
		Collections.shuffle(this);
	}
}
