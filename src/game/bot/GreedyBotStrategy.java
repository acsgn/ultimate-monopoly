package game.bot;

import game.Player;
import game.square.*;
import game.square.estate.Property;

public class GreedyBotStrategy implements botStrategy {
	private static final long serialVersionUID = 1L;

	@Override
	public String getActionMessage(Square s, Player player) {
		// TODO Auto-generated method stub
		String message = "BOT/";
		if (s instanceof Property) {
			Property ps = (Property) s;
			if (ps.getOwner() == null) {
				message += "BUYPROPERTY";
			}else {
				message += "NOTHING";
			}
		}
		return message + "/" + player.getName();
	}
}
