package game.card;

import game.Player;

public class RollThree extends Card {

	private static final CardType type = CardType.ROLL_THREE;
	
	@Override
	public void executeAction(Player player) {
	}

	@Override
	public CardType getCardType() {
		return type;
	}

}
