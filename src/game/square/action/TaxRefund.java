package game.square.action;

import game.Player;
import game.Pool;

public class TaxRefund extends Action {
	private static final long serialVersionUID = 1L;

	public TaxRefund() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		int amount = Pool.getInstance().getAmount()/2;
		Pool.getInstance().payToPool(amount);
		player.increaseMoney(amount);
	}

	@Override
	public void executeWhenPassed(Player player) {
	}

}
