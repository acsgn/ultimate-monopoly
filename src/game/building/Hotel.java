package game.building;

import game.card.CardType;

public class Hotel extends Building {
	
	private static final BuildingType type = BuildingType.HOTEL;
	
	public Hotel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BuildingType getBuildingType() {
		return type;
	}
}