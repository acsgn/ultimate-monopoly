package game.square.action;

import game.Player;

public class Go extends Action{
	
	private static final int amount = 200;

	public Go() {
	}

	@Override
	public void executeWhenPassed(Player player) {
		player.increaseMoney(amount);
	}
	
	@Override
	public void executeWhenLanded(Player player) {
		player.increaseMoney(amount);
	}

}
