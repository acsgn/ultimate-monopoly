package game.square;

import game.Player;

public abstract class Square {

	private boolean executeWhenPassed = false;

	public boolean executeWhenPassed() {
		return executeWhenPassed;
	}

	public abstract void executeAction(Player player);

	public abstract SquareType getType();

}