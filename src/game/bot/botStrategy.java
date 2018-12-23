package game.bot;

import game.Player;
import game.square.Square;

public interface botStrategy {
	public String getActionMessage(Square s, Player player);
}
