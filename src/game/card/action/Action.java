package game.card.action;

import game.Player;

public interface Action {
	
	public String getText();

	public void executeAction(Player player);
	
}
