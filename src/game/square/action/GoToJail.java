package game.square.action;

import game.Player;

public class GoToJail extends Action {

	public GoToJail() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @requires
	 * @modifies 
	 * @effects player is sent to jail
	 */
	@Override
	public void executeWhenLanded(Player player) {
		player.sendToJail();
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}