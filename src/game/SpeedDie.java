package game; 
import java.util.Random;

public class SpeedDie extends Dice {

	public static final int SPEED_MAX = 5;
	int MrMonopoly = 4;
	int BusIcon = 5;

	public SpeedDie() {

	}

	public void roll() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		faceValue = rand.nextInt(SPEED_MAX)+1;
	}

	@Override
	public int getFaceValue() {
		// TODO Auto-generated method stub

		if(faceValue == 4) {
			//to be implemented
			//move to the next unowned property
			//if all of the properties are owned
			//move ahead to the first property on which you need to pay rent
			return 0;
		} else if (faceValue == 5) {
			//to be implemented
			//move to the nearest chance or community chest
			return 0;
		}
		return faceValue;
	}



}
