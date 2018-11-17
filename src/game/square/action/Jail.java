package game.square.action;

import game.Player;

public class Jail extends Action{

	public Jail() {
	}

	@Override
	public void executeAction(Player player) {
		player.setInJail(true);
	}



}
