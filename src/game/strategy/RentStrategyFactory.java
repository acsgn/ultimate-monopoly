package game.strategy;

import game.Square;
import game.square.estate.PropertySquare;
import game.square.estate.RailroadSquare;
import game.square.estate.UtilitySquare;

public class RentStrategyFactory {
	private static RentStrategyFactory rentStrategy;
	
	private RentStrategyFactory(){
		
	}
	
	public static synchronized RentStrategyFactory getInstance(){
		if(rentStrategy==null){
			rentStrategy = new RentStrategyFactory();
		}
		return rentStrategy;
	}
	
	public RentStrategy getStrategy(Square s){
		if(s instanceof PropertySquare){
			return new PropertyRentStrategy();
		}else if(s instanceof UtilitySquare){
			return new UtilityRentStrategy();
		}else if(s instanceof RailroadSquare){
			return new RailRoadRentStrategy();
		}
		return null;
	}
}
