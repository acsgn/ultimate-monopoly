package game.square.action;

import game.Action;
import game.Player;

public class ActionSquareFactory {

	Action myAction = (Player player) -> player.collectRent(500);

	private static ActionSquareFactory self;

	public ActionSquare getActionSquare(ActionSquareType type) {

	}

	private ActionSquareFactory() {
	}

	public static synchronized ActionSquareFactory getInstance() {
		if (self == null)
			self = new ActionSquareFactory();
		return self;
	}

}
