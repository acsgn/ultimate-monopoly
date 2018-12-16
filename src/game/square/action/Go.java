package game.square.action;

import game.Player;

public class Go extends Action{
	
	private static final int amount = 200;

	public Go() {
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
		player.increaseMoney(amount);
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
		player.increaseMoney(amount);
	}

}
