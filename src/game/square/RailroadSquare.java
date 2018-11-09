package game.square;

import game.Player;
import game.strategy.RentStrategyFactory;

public class RailroadSquare extends Square {
	private int rent;
	private int price;
	private Player owner;
	
	public RailroadSquare(String name, int number, int rent, int price, Player owner) {
		super(name, number);
		this.rent = rent;
		this.price = price;
		this.owner = owner;
	}

	//The rent system is prone to change
	public int getRent() {
		rent = RentStrategyFactory.getInstance().getStrategy(this).getRent(this);
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

	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	
}