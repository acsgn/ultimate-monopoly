package game.card.action;

import game.Player;

public class RollThree extends ActionCard {

	private static final ActionCardType type = ActionCardType.ROLL_THREE;
	
	@Override
	public void executeAction(Player player) {
	}

	@Override
	public ActionCardType getActionCardType() {
		return type;
	}

}
