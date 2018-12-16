package game.square;

import java.io.Serializable;

import game.Player;

public abstract class Square implements Serializable{

	/**
	 * @overview This function is executed when the player passes
	 * the square
	 * @param player the player that passes the square
	 */
	public abstract void executeWhenPassed(Player player);

	/**
	 * @overview This function is executed when the player lands
	 * on the square
	 * @param player the player that lands on the square
	 */
	public abstract void executeWhenLanded(Player player);
	
	public abstract boolean isOwned();

	public abstract SquareType getType();

}