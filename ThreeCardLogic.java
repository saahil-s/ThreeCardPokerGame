import java.util.ArrayList;
import java.util.Collections;

public class ThreeCardLogic {

	public static int evalHand(ArrayList<Card> hand) {
		ArrayList<Integer> values = new ArrayList<Integer>();
		values.add(hand.get(0).value);
		values.add(hand.get(1).value);
		values.add(hand.get(2).value);
		Collections.sort(values); // sorted card values low to high

		if ((values.get(0) == values.get(1)) && (values.get(1) == values.get(2))) {
			return 2; // 3 of a kind
		} else if ((hand.get(0).suit == hand.get(1).suit) && 
				(hand.get(1).suit == hand.get(2).suit)) {
			if ((values.get(0) + 1 == values.get(1)) && (values.get(1) + 1 == values.get(2))) {
				return 1; // straight flush
			} else  {
				return 4; // flush
			}
		} else if ((values.get(0) + 1 == values.get(1)) && (values.get(1) + 1 == values.get(2))) {
			return 3; // straight
		} else if ((values.get(0) == values.get(1)) || (values.get(1) == values.get(2)) || 
				(values.get(0) == values.get(2))) {
			return 5; // pair
		} else {
			return 0; // high card
		}
	}
	
	public static int evalPPWinnings(ArrayList<Card> hand, int bet) { 
		int handType = evalHand(hand);
	
		if (handType == 5) {
			return bet * 2; // 1 to 1 payout
		} else if (handType == 4){
			return bet * 4; // 3 to 1 payout
		} else if (handType == 3) {
			return bet * 7; // 6 to 1 payout
		} else if (handType == 2) {
			return bet * 31; // 30 to 1 payout
		} else if (handType == 1) {
			return bet * 41; // 40 to 1 payout
		} else {
			return 0; // lost PP bet
		}
	}
	
	public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
		int dealerHand = evalHand(dealer);
		if (dealerHand == 0) {
			dealerHand = 6;
		}
		int playerHand = evalHand(player);
		if (playerHand == 0) {
			playerHand = 6;
		}
		
		if (dealerHand < playerHand) {
			return 1; // dealer wins
		} else if (playerHand < dealerHand) {
			return 2; // player wins
		} else { // hands are same type
			ArrayList<Integer> playerValues = new ArrayList<Integer>();
			playerValues.add(player.get(0).value);
			playerValues.add(player.get(1).value);
			playerValues.add(player.get(2).value);
			Collections.sort(playerValues); // sorted card values low to high
			Collections.reverse(playerValues); // sorted card values high to low
			
			ArrayList<Integer> dealerValues = new ArrayList<Integer>();
			dealerValues.add(dealer.get(0).value);
			dealerValues.add(dealer.get(1).value);
			dealerValues.add(dealer.get(2).value);
			Collections.sort(dealerValues); // sorted card values low to high
			Collections.reverse(dealerValues); // sorted card values high to low
			// check cards to see who has the highest
			if (dealerValues.get(0) > playerValues.get(0)) {
				return 1;
			} else if (dealerValues.get(0) < playerValues.get(0)) {
				return 2;
			} else {
				if (dealerValues.get(1) > playerValues.get(1)) {
					return 1;
				} else if (dealerValues.get(1) < playerValues.get(1)) {
					return 2;
				} else {
					if (dealerValues.get(2) > playerValues.get(2)) {
						return 1;
					} else if (dealerValues.get(2) < playerValues.get(2)) {
						return 2;
					} else {
						return 0; // tied
					}
				}
			}
		}
	}
}
