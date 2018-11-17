package game.square;

import game.Player;

public abstract class Square {

	public void executeWhenPassed(Player player) {
	}

	public void executeWhenLanded(Player player) {
	}

	public abstract SquareType getType();

}