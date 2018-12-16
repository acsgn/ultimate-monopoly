package game.square.action;

import game.Player;

public class Bonus extends Action {

	private static final int landedAmount = 300;
	private static final int passedAmount = 250;

	public Bonus() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @requires
	 * @modifies money value is changed
	 * @effects player's money is increased by the amount
	 */
	@Override
	public void executeWhenPassed(Player player) {
		player.increaseMoney(passedAmount);
	}

	
	/**
	 * {@inheritDoc}
	 * 
	 * @requires
	 * @modifies money value is changed
	 * @effects player's money is increased by the amount
	 */
	@Override
	public void executeWhenLanded(Player player) {
		player.increaseMoney(landedAmount);
	}

}
