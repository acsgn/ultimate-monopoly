package game.square.action;

import game.Action;
import game.Player;

public class ActionSquareFactory {

	 //there will be all actions that is possible
	Action myAction = (Player player) -> player.collectRent(500); 
	

	private static ActionSquareFactory self;

	public ActionSquare getActionSquare(ActionSquareType type) {
		switch (type) {
		case CHANCE: return new ActionSquare(myAction, type);
		default: return new ActionSquare(null, type);
		}
	}

	private ActionSquareFactory() {
	}

	public static synchronized ActionSquareFactory getInstance() {
		if (self == null)
			self = new ActionSquareFactory();
		return self;
	}

}
