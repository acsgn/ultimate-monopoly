package game.square;

import game.Player;

public class JailSquare extends Square{

	public JailSquare(String name, int number) {
		super(name, number);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		player.setInJail(true);
	}



}
