package game.bot;

import java.io.Serializable;

import game.Player;
import game.square.Square;

public interface botStrategy extends Serializable {
	public String getActionMessage(Square s, Player player);
}
