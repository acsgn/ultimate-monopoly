package game.strategy;

import java.util.List;

import game.Player;
import game.square.RailroadSquare;
import game.square.Square;

public class RailRoadRentStrategy implements RentStrategy{

	@Override
	public int getRent(Square square) {
		// TODO Auto-generated method stub
		Player owner = ((RailroadSquare)square).getOwner();
		List<RailroadSquare> ownedRailRoads = owner.getRailRoadSquares();
		return (int) (25* (Math.pow(2, (ownedRailRoads.size()-1))));
	}

}
