package game.square;

import java.io.Serializable;

import game.Player;

public abstract class Square implements Serializable{

	public abstract void executeWhenPassed(Player player);

	public abstract void executeWhenLanded(Player player);
	
	public abstract boolean isOwned();

	public abstract SquareType getType();

}