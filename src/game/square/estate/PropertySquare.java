package game.square.estate;

import java.util.List;

import game.Player;
import game.building.Building;
import game.square.deed.TitleDeed;
import game.strategy.RentStrategyFactory;

public class PropertySquare extends Estate {
	private String name;
	private int rent;
	private int price;
	private Player owner;
	private List<Building> buildings;
	private TitleDeed titleDeed;
	
	public PropertySquare(String name, int number, int price, TitleDeed titleDeed) {
		this.name = name;
		this.price = price;
		this.titleDeed = titleDeed;
	}

	//The rent system is prone to change
	public int getRent() {
		rent = RentStrategyFactory.getInstance().getStrategy(this).getRent(this);
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}
	
	public String getName() {
		return name;
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
	public void executeAction(Player player) {
		if(owner==null){
			// We will send a message to UI to activate the Buy Property Button
			// So we will handle the process here. Then if the button is clicked the player.buySquare(location)
			// will be called. So here we only send message to UI
		}else{
			player.payRent(this);
		}
	}

	
}