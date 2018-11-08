package game.square;

import java.util.List;

import game.Player;
import game.building.Building;

public class PropertySquare extends Square {
	private int rent;
	private int price;
	private Player owner;
	private List<Building> buildings;
	
	public PropertySquare(String name, int number, int rent, int price, Player owner) {
		super(name, number);
		this.rent = rent;
		this.price = price;
		this.owner = owner;
	}

	//The rent system is prone to change
	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getPrice() {
		return price;
	}

	public Player getOwner() {
		return owner;
	}
	
	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	@Override
	public void executeAction() {
		// TODO Auto-generated method stub
		/*if(owner==null) {
			buySquare(super.getName());
		} else {
			payRent(super.getName());
		}*/
	}
}