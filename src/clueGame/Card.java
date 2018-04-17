package clueGame;

public class Card {
	String cardName;
	CardType type;
	// equals() : if cardName is the same in both cards, returns true. 
	// else returns false.
	boolean equals(Card c1, Card c2) {
		if (c1.cardName == c2.cardName) {
			return true;
		}
		return false;
	}
}
