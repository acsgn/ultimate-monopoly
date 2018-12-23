package game.bot;

import game.Controller;
import game.Player;
import game.square.Square;

public class bot {
	
	Player currentPlayer;
	botStrategy strategy; 
	public bot(Player currentPlayer){
		this.currentPlayer = currentPlayer;
	}
	public void play(){
		//Roll the dice
		String message = "BOT/ROLLDICE";
		Controller.getInstance().dispatchMessage(message);
		
		// Do some Action,
		Square location = currentPlayer.getLocation();
		String message_1; 
		strategy = botStrategyFactory.getInstance().getbotStrategy(location);
		message_1 = strategy.getActionMessage(location, currentPlayer);
		Controller.getInstance().dispatchMessage(message_1);
		
		//End your Turn. 
		String message_2 = "BOT/ENDTURN";
		Controller.getInstance().dispatchMessage(message_2);
	}
}
