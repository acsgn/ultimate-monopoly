package game.square;

import game.Player;

public class BonusSquare extends Square{

	public BonusSquare(String name, int number) {
		super(name, number);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		player.increaseMoney(300);
	}



}
