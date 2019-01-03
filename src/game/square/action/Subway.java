package game.square.action;

import game.Player;

public class Subway extends Action {
	private static final long serialVersionUID = 1L;

	public Subway() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		player.setGoAnyWhere();
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}
