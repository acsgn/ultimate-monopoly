package game.square.action;

import game.Player;
import game.dice.SingletonDice;

public class PayDay extends Action {
	
	private static final int evenAmount = 400;
	private static final int oddAmount = 300;

	public PayDay() {
	}

	@Override
	public void executeWhenPassed(Player player) {
		int[] diceRolls = SingletonDice.getInstance().getFaceValues();
		int sum = diceRolls[0] + diceRolls[1];
		if(sum % 2 == 0) {
			player.increaseMoney(evenAmount);
		} else 
			player.increaseMoney(oddAmount);
	}
	
	@Override
	public void executeWhenLanded(Player player) {
		int[] diceRolls = SingletonDice.getInstance().getFaceValues();
		int sum = diceRolls[0] + diceRolls[1];
		if(sum % 2 == 0) {
			player.increaseMoney(evenAmount);
		} else 
			player.increaseMoney(oddAmount);
	}
	
}