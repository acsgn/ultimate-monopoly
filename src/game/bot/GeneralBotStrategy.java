package game.bot;

import game.Player;
import game.square.Square;

public class GeneralBotStrategy implements botStrategy{

	@Override
	public String getActionMessage(Square s, Player player) {
		// TODO Auto-generated method stub
		String message = "BOT/NOTHING/"+player.getName();
		return message;
	}
}
