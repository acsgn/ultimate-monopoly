package game.bot;

import game.Player;
import game.square.*;
import game.square.estate.Property;

public class PropertyBotStrategy implements botStrategy{


	@Override
	public String getActionMessage(Square s, Player player) {
		// TODO Auto-generated method stub
		String message = "BOT/";
		Property ps = (Property) s;
		if(ps.getOwner()==null){
			message += "BUYPROPERTY";
		}else if(ps.getOwner().equals(player.getName())){
			message += "BUYBUILDING";
		}else{
			message += "NOTHING";
		}
		
		return message;
	}
}
