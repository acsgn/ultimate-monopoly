package game.square.action;

import game.ActionCards;
import game.Player;
import game.Square;
import game.card.action.Chance;

public class ChanceSquare extends Square {
	
	
	public ChanceSquare(String name, int number) {
		super(name, number);
		
	}

	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		Chance chanceCard = ActionCards.getInstance().getChanceCard();
		player.pickCard(chanceCard);
	}
	
}