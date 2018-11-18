package game.strategy;

import game.square.Square;
import game.square.estate.*;

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
		if(s instanceof Property){
			return new PropertyRentStrategy();
		}else if(s instanceof Utility){
			return new UtilityRentStrategy();
		}else if(s instanceof TransitStation){
			return new RailRoadRentStrategy();
		}
		return null;
	}
}
