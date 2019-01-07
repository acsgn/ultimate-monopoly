package game.square.action;

import game.Player;
import game.card.CardType;

public class Chance extends Action {
	private static final long serialVersionUID = 1L;
	
	
	public Chance() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		//game.card.Chance card = ActionCards.getInstance().getChanceCard();
		//player.pickCard(card);
		player.delegateTask("CARD/"+player.getName()+"/"+CardType.CHANCE.ordinal());
		//NetworkFacade.getInstance().sendMessage(player.getName()+"/CARD/"+card.getCardType().ordinal());
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}