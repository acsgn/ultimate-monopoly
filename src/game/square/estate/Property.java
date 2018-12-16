package game.square.estate;

import java.util.ArrayList;

import game.building.Building;
import game.strategy.RentStrategyFactory;

public class Property extends Estate {
	
	private static final EstateSquareType type = EstateSquareType.PROPERTY;
	
	private int rent;
	private ArrayList<Building> buildings;
	private TitleDeed titleDeed;
	private PropertyColor color;
	
	public Property(String name, int price, TitleDeed titleDeed) {
		super(name, price, type);
		this.titleDeed = titleDeed;
		buildings = new ArrayList<>();
	}

	//The rent system is prone to change
	public int getRent() {
		rent = RentStrategyFactory.getInstance().getStrategy(this).getRent(this);
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}
	
	
	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(ArrayList<Building> buildings) {
		this.buildings = buildings;
	}
	
}