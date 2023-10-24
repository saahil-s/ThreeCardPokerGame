
public class Card {

	public char suit;
	public int value;
	
	public Card(char suit, int value) {
		this.suit = suit;
		this.value = value;
	}
	
	@Override
	public String toString() {
		String s = Integer.toString(this.value) + Character.toString(this.suit);
		return s;
	}
}
