package game.bot;

import game.square.Square;
import game.square.estate.Property;

public class botStrategyFactory {
	
	private static botStrategyFactory factory;
	
	private botStrategyFactory(){
		
	}
	public static botStrategyFactory getInstance(){
		if (factory==null){
			factory = new botStrategyFactory();
		}
		return factory;
	}
	public botStrategy getbotStrategy(Square location){
		if(location instanceof Property){
			return new PropertyBotStrategy();
		}else{
			return new GeneralBotStrategy();
		}
	}
}
