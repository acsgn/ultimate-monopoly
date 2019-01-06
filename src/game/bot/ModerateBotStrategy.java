package game.bot;

import java.util.Random;

import game.Player;
import game.square.Square;
import game.square.estate.Property;

public class ModerateBotStrategy implements botStrategy{

	@Override
	public String getActionMessage(Square s, Player player) {
		// TODO Auto-generated method stub
		String message = "BOT/";
		if (s instanceof Property) {
			Property ps = (Property) s;
			if (ps.getOwner() == null) {
				Random n = new Random();
				int i = n.nextInt(2);
				if(i==0)
					message += "BUYPROPERTY";
				else
					message += "NOTHING";
			}else {
				message += "NOTHING";
			}
		}
		return message + "/" + player.getName();
	}

}
