package game.square.action;

import game.ActionCards;
import game.Player;
import game.card.action.Chance;
import game.square.Square;

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