package game.square.estate;

import game.strategy.RentStrategyFactory;

public class Utility extends Estate {
	private static final long serialVersionUID = 1L;

	private static final EstateSquareType type = EstateSquareType.UTILITY;
	private static final int UTILITY_PRICE = 150;

	private int rent;

	public Utility(String name) {
		super(name, UTILITY_PRICE, type);
	}

	// The rent system is prone to change
	public int getRent() {
		rent = RentStrategyFactory.getInstance().getStrategy(this).getRent(this);
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	@Override
	public String information() {
		String information;
		if (getOwner() != null) {
			information = "UTILITY/" + getOwner().getName() + "/";
			information += getOwner().getUtilitySquares().size();
		} else
			information = "NOOWNER";
		return information;
	}

}