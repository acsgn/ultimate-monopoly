package game.square.action;

import game.Player;
import game.card.ActionCards;

public class CommunityChest extends Action {
	
	public CommunityChest() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		game.card.CommunityChest card = ActionCards.getInstance().getCommunityChestCard();
		player.pickCard(card);
	}
	
}