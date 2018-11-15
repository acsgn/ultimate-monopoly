package game.card.action;

import game.Player;

public class Chance extends Action {

	private static final ActionType type = ActionType.CHANCE;
	
	@Override
	public void executeAction(Player player) {
		
	}

	@Override
	public ActionType getActionType() {
		return type;
	}

}
