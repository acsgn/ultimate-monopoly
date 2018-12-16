package game.dice;

public class SingletonDice {
	private RegularDie regularDie1;
	private RegularDie regularDie2;
	private SpeedDie speedDie;

	private static SingletonDice dice;

	private SingletonDice() {
		regularDie1 = new RegularDie();
		regularDie2 = new RegularDie();
		speedDie = new SpeedDie();
	}

	public static synchronized SingletonDice getInstance() {
		if (dice == null)
			dice = new SingletonDice();
		return dice;
	}

	/**
	 * {@inheritDoc}
	 */
	public void rollDice() {
		regularDie1.roll();
		regularDie2.roll();
		speedDie.roll();
	}

	public int[] getFaceValues() {
		int[] diceRolls = new int[3];
		diceRolls[0] = regularDie1.getFaceValue();
		diceRolls[1] = regularDie2.getFaceValue();
		diceRolls[2] = speedDie.getFaceValue();
		return diceRolls;
	}

	public boolean repOk() {
		if (speedDie == null || regularDie1 == null || regularDie2 == null)
			return false;
		return true;
	}

}
