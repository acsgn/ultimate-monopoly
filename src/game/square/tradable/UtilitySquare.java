package game.square.tradable;

import game.Player;
import game.square.Square;
import game.strategy.RentStrategyFactory;

public class UtilitySquare extends Square {
	private int rent;
	private int price;
	private Player owner;
	
	public UtilitySquare(String name, int number, int rent, int price, Player owner) {
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
		if(owner==null){
			// We will send a message to UI to activate the Buy Property Button
			// So we will handle the process here. Then if the button is clicked the player.buySquare(location)
			// will be called. So here we only send message to UI
		}else{
			player.payRent(this);
		}
	}
	
	
}