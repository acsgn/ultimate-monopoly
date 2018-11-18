package game.dice;
import java.util.Random;

public class RegularDie extends Dice {

	public static final int NORMAL_MAX = 6;

	public RegularDie() {
	}

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
