package game.dice; 
public abstract class Dice {
	protected int faceValue;

	/**
	 * @overview Rolls the dice
	 */
	public abstract void roll();
	
	public abstract int getFaceValue();
}
