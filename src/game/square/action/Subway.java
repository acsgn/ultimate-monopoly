package game.square.action;

import game.Player;

public class Subway extends Action {

	public Subway() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		player.setGoAnyWhere();
	}

}
