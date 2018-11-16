package game.square.action;

import game.ActionCards;
import game.Player;
import game.card.action.CommunityChest;
import game.square.Square;

public class CommunityChestSquare extends Square {
	
	
	public CommunityChestSquare(String name, int number) {
		super(name, number);
		
	}

	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		CommunityChest card = ActionCards.getInstance().getCommunityChestCard();
		player.pickCard(card);
	}
	
}