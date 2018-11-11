package game.dice; 
import java.util.Random;

public class SpeedDie extends Dice {

	public static final int SPEED_MAX = 6;

	public SpeedDie() {

	}

	public void roll() {
		Random rand = new Random();
		faceValue = rand.nextInt(SPEED_MAX)+1;
	}

	@Override
	public int getFaceValue() {
		if(faceValue==6){
			return 5;
		}
		return faceValue;
	}



}
