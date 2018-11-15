package game.card.action;

import game.Player;

public class RollThree extends Action {

	private static final ActionType type = ActionType.ROLL_THREE;
	
	@Override
	public void executeAction(Player player) {
	}

	@Override
	public ActionType getActionType() {
		return type;
	}

}
