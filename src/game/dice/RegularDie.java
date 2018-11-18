package game.dice;
import java.util.Random;

public class RegularDie extends Dice {

	public static final int NORMAL_MAX = 6;

	public RegularDie() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void roll() {
		// TODO Auto-generated method stub
		Random rand = new Random(5000);
		faceValue = rand.nextInt(NORMAL_MAX)+1;
	}

	@Override
	public int getFaceValue() {
		// TODO Auto-generated method stub
		return faceValue;
	}

}
