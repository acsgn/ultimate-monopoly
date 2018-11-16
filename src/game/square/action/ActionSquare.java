package game.square.action;

import game.Action;
import game.Player;
import game.Square;
import game.SquareType;

public class ActionSquare extends Square {
	
	private static final SquareType type = SquareType.ACTION;
	private Action action;
	private ActionSquareType actionType;
	
	public ActionSquare(Action action, ActionSquareType type) {
		this.action = action;
		this.actionType = type;
	}
	
	public void doAction(Player player) {
		action.perform(player);
	}
	
	public ActionSquareType getActionType() {
		return actionType;
	}
	
	@Override
	public SquareType getType() {
		return type;
	}	
	
}
