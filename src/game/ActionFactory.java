package game;


import java.util.function.BiConsumer;
import java.util.function.Function;

import game.card.Card;
import game.square.Square;

public class ActionFactory {

	private static ActionFactory self;
	
	private BiConsumer<Player, Card> pickCard = (Player player, Card card) -> player.getName();

	public Function getAction(ActionType type) { 
		switch (type) {
		case CARD:
			return () -> pickCard;
		}
		return null;
	}

	private ActionFactory() {
	}

	public static synchronized ActionFactory getInstance() {
		if (self == null)
			self = new ActionFactory();
		return self;
	}


	
	

}
