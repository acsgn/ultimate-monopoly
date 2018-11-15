package game.strategy;

import java.util.List;

import game.building.Building;
import game.square.Square;
import game.square.estate.PropertySquare;

public class PropertyRentStrategy implements RentStrategy{

	@Override
	public int getRent(Square square) {
		// TODO Auto-generated method stub
		List<Building> buildings = ((PropertySquare)square).getBuildings();
		if(buildings.size()!=0){
			// Needs to be implemented!
			return 0;
		}else{
			// We will implement the Monopoly and Majority case next week 
			// Monopoly -> (rent is tripled) / Majority -> (rent is doubled)
			return 20;
		}
	}

}
