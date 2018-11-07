package game.building;

import game.Player;
import game.square.PropertySquare;

public class House extends Building {

	public House(int price, int number, Player owner, PropertySquare ps) {
		super(price, number, owner, ps);
		// TODO Auto-generated constructor stub
	}
	
	//if a property has 4 houses, the 4 houses will be removed
	//and a hotel will be added

	
	@Override
	public void addBuilding() {
		// TODO Auto-generated method stub
		
	}

	//adding one more house to 4 house property requires the removal of 
	//4 houses
	@Override
	public void removeBuilding() {
		// TODO Auto-generated method stub
		
	}


}
