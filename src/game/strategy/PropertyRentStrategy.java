package game.strategy;

import java.util.List;

import game.building.Building;
import game.building.Hotel;
import game.building.House;
import game.building.Skyscraper;
import game.square.Square;
import game.square.estate.Property;

public class PropertyRentStrategy implements RentStrategy{

	@Override
	public int getRent(Square square) {
		// TODO Auto-generated method stub
		List<Building> buildings = ((Property)square).getBuildings();
		if(buildings.size()!=0){
			// Needs to be implemented!
			switch(buildings.size()){
			case 2: 
				return ((Property)square).getTitleDeed().getTwoHouseRent();
			case 3: 
				return ((Property)square).getTitleDeed().getThreeHouseRent();
			case 4: 
				return ((Property)square).getTitleDeed().getFourHouseRent();
			case 1: 
				if(buildings.get(0) instanceof House){
					return ((Property)square).getTitleDeed().getOneHouseRent();
				}else if(buildings.get(0) instanceof Hotel){
					return ((Property)square).getTitleDeed().getHotelRent();
				}else if(buildings.get(0) instanceof Skyscraper){
					return ((Property)square).getTitleDeed().getSkyscraperRent();
				}
			}
			return 0;
		}else{
			// We will implement the Monopoly and Majority case next week 
			// Monopoly -> (rent is tripled) / Majority -> (rent is doubled)
			int rent = ((Property)square).getTitleDeed().getRent();
			if(((Property)square).getOwner().getName().equals(((Property)square).getColorGroup().getMonopolyOwnerName())){
				rent = rent * 3;
			}
			if(((Property)square).getOwner().getName().equals(((Property)square).getColorGroup().getMajorityOwnerName())){
				rent = rent * 2;
			}
			return rent;
		}
	}

}
