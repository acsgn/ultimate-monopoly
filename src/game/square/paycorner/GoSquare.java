package game.square.paycorner;

import game.Player;
import game.Square;

public class GoSquare extends Square{
	
	private static final int amount = 200;

	public GoSquare(String name, int number) {
		super(name, number);
	}

	@Override
	public void executeAction(Player player) {
		player.increaseMoney(amount);
	}



}
