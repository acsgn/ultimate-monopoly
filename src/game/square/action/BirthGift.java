package game.square.action;

import game.Player;

public class BirthGift extends Action{
	private static final long serialVersionUID = 1L;
	
	private static final int giftAmount = 100;
	public BirthGift() {
	}
	
	@Override
	public void executeWhenLanded(Player player) {
		//TODO
		player.delegateTask("BIRTHGIFT/"+giftAmount+"/"+player.getName());
	}

	@Override
	public void executeWhenPassed(Player player) {
	}

}
