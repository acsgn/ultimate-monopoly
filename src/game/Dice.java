
public abstract class Dice {

	int val1;
	int val2; 
	int val3; 
	boolean rollDouble;

	int faceValue;

	public abstract void roll();

	public abstract int getFaceValue() ;



	public Dice() {
		super();
	}

	public int totalRoll(SpeedDie die1, RegularDie die2, RegularDie die3) {

		val1 = die1.getFaceValue();

		val2 = die2.getFaceValue();
		val3 = die3.getFaceValue();

		if(val2 == val3) {
			rollDouble = true;
		}

		return val1 + val2 + val3;

	}

//three times rolling sends to jail part will be implemented

	public void lastRollWasDouble(Player p) {
		if(rollDouble) {
			p.takeTurn();
		}
	}
}
