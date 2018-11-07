package game.building;

import game.Player;
import game.square.PropertySquare;

public abstract class Building {

	//the price
	int price;
	//player class type
	Player owner;
	//the Square it is built on
	PropertySquare ps;
	//number of buildings
	int number ;

	public Building(int price, int number, Player owner, PropertySquare ps) {
		super();
		this.price = price;
		this.owner = owner;
		this.ps = ps;
		this.number = number;

	}

	//to add new buildings evenly
	public abstract void addBuilding();

	//to sell buildings
	public abstract void removeBuilding();



}
