package game.square.action;

import game.Player;
import game.card.CommunityChest;
import game.square.Square;

public class CommunityChest extends Action {
	
	
	public CommunityChest() {
	}

	@Override
	public void executeAction(Player player) {
		CommunityChest card = ActionCards.getInstance().getCommunityChestCard();
		player.pickCard(card);
	}
	
}