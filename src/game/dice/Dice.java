package game.dice; 
public abstract class Dice {
	protected int faceValue;

	/**
	 * Rolls the dice
	 */
	public abstract void roll();
	
	public abstract int getFaceValue();
}
