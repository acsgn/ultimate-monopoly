package game.square.action;

import game.Player;

public class Chance extends Action {
	
	
	public Chance() {
		
	}

	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		Chance chanceCard = ActionCards.getInstance().getChanceCard();
		player.pickCard(chanceCard);
	}
	
}