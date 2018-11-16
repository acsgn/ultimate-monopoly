package game.square.paycorner;

import game.Player;
import game.Square;

public class BonusSquare extends Square {

	private static final int amount = 300;

	public BonusSquare(String name, int number) {
		super(name, number);
	}

	@Override
	public void executeAction(Player player) {
		player.increaseMoney(amount);
	}

}
