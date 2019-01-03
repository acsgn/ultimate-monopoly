package game.square.action;

import game.Player;
import game.Pool;

public class LuxuryTax extends Action {
	private static final long serialVersionUID = 1L;
	
	private static final int amount = 75;

	public LuxuryTax() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		player.reduceMoney(amount);
		Pool.getInstance().payToPool(amount);
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}
