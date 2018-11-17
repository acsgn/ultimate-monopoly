package game.square.action;

import game.Player;

public class LuxuryTax extends Action {
	
	private static final int amount = 75;

	public LuxuryTax() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		player.reduceMoney(amount);
	}

}
