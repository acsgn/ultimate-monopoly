package game.square.estate;

import game.strategy.RentStrategyFactory;

public class Utility extends Estate {
	
	private static final EstateSquareType type = EstateSquareType.UTILITY;
	
	private int rent;
	
	public Utility(String name, int price) {
		super(name, price, type);
	}

	//The rent system is prone to change
	public int getRent() {
		rent = RentStrategyFactory.getInstance().getStrategy(this).getRent(this);
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}
	
}