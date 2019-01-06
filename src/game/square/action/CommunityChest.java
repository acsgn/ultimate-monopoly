package game.square.action;

import game.Player;
import game.card.ActionCards;
import network.NetworkFacade;

public class CommunityChest extends Action {
	private static final long serialVersionUID = 1L;
	
	public CommunityChest() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		game.card.CommunityChest card = ActionCards.getInstance().getCommunityChestCard();
		//player.pickCard(card);
		player.delegateTask("CARD/"+player.getName()+"/"+card.getCardType().ordinal());
		//NetworkFacade.getInstance().sendMessage(player.getName()+"/CARD/"+card.getCardType().ordinal());
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}