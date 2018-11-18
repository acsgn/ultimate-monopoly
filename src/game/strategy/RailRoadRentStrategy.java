package game.strategy;

import java.util.List;

import game.Player;
import game.square.Square;
import game.square.estate.TransitStation;

public class RailRoadRentStrategy implements RentStrategy{

	@Override
	public int getRent(Square square) {
		// TODO Auto-generated method stub
		Player owner = ((TransitStation)square).getOwner();
		List<TransitStation> ownedRailRoads = owner.getTransitStations();
		return (int) (25* (Math.pow(2, (ownedRailRoads.size()-1))));
	}

}
