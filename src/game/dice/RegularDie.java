package game.dice;
import java.util.Random;

public class RegularDie extends Dice {

	public static final int NORMAL_MAX = 6;

	public RegularDie() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @requires
	 * @modifies a random value is created,
	 * faceValue is changed
	 * @effects value for a regular dice is determined
	 */
	@Override
	public void roll() {
		Random rand = new Random(System.nanoTime());
		faceValue = rand.nextInt(NORMAL_MAX)+1;
	}

	@Override
	public int getFaceValue() {
		return faceValue;
	}

}
