package game.square.action;

import game.Player;
import game.Square;

public class GoToJailSquare extends Square {
	
	public GoToJailSquare(String name, int number) {
		super(name, number);
	}

	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		// player.setLocation(Board.getInstance().getSquare("Jail"))
		// player.setInJail(true)
	}


	
	
}