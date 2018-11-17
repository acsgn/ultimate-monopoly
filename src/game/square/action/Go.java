package game.square.action;

import game.Player;

public class Go extends Action{
	
	private static final int amount = 200;

	public Go() {
	}

	@Override
	public void executeAction(Player player) {
		player.increaseMoney(amount);
	}

}
