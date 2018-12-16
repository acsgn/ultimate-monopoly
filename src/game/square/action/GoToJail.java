package game.square.action;

import game.Player;

public class GoToJail extends Action {

	public GoToJail() {
	}

	@Override
	/**
	 * Sends the player to jail
	 * @param player the player that will go to jail
	 */
	public void executeWhenLanded(Player player) {
		player.sendToJail();
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}