package game.square.action;

import game.Action;
import game.Player;
import game.square.Square;
import game.square.SquareType;

public class ActionSquare extends Square {
	
	private static final SquareType type = SquareType.ACTION;
	
	private Action action;
	
	public ActionSquare(Action action, boolean doWhenPassed) {
		this.action = action;
	}
	
	@Override
	public SquareType getType() {
		return type;
	}

	@Override
	public void executeAction(Player player) {
		action.doAction(player);
	}	
	
}
