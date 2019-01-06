package game.card;

import game.Player;
import game.dice.SingletonDice;

public class RollThree extends Card {
	private static final long serialVersionUID = 1L;

	private static final CardType type = CardType.ROLL_THREE;

	// fields for the dice values on the card
	private int die1;
	private int die2;
	private int die3;

	public RollThree(int die1, int die2, int die3) {
		this.die1 = die1;
		this.die2 = die2;
		this.die3 = die3;
	}

	@Override
	public void executeAction(Player player) {
		
		Card card = player.getRoll3();
		int[] values = ((RollThree)card).getValues();
		int[] diceValues = SingletonDice.getInstance().getFaceValues();
		int matchCount = matchCount(diceValues, values);
		
		if (matchCount == 3) {
			player.increaseMoney(1000);
		} else if (matchCount == 2) {
			player.increaseMoney(200);
		} else if (matchCount == 1) {
			player.increaseMoney(50);
		}
		
	}

	// getter for values on the roll three card
	private int[] getValues() {

		int[] values = new int[3];
		values[0] = die1;
		values[1] = die2;
		values[2] = die3;

		return values;

	}

	// returns the count of matches between the dice values and card values given
	private int matchCount(int[] diceValues, int[] cardValues) {

		int match = 0;

		for (int dice = 0; dice < 3; dice++) {
			for (int cardValIndex = 0; cardValIndex < 3; cardValIndex++) {
				if (diceValues[dice] == cardValues[cardValIndex])
					match++;
			}
		}

		return match;

	}

	@Override
	public CardType getCardType() {
		return type;
	}

}
