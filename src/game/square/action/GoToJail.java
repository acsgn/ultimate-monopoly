package game.square.action;

import game.Player;

public class GoToJail extends Action {

	public GoToJail() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		player.setInJail();
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}