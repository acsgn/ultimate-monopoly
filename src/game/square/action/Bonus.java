package game.square.action;

import game.Player;

public class Bonus extends Action {

	private static final int amount = 300;

	public Bonus() {
	}

	@Override
	public void executeAction(Player player) {
		player.increaseMoney(amount);
	}

}
