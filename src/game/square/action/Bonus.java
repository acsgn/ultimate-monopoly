package game.square.action;

import game.Player;

public class Bonus extends Action {

	private static final int landedAmount = 300;
	private static final int passedAmount = 250;

	public Bonus() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeWhenPassed(Player player) {
		player.increaseMoney(passedAmount);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeWhenLanded(Player player) {
		player.increaseMoney(landedAmount);
	}

	public boolean repOk(){
		if(this == null){
			return false;
		}
		return true;
	}
}
