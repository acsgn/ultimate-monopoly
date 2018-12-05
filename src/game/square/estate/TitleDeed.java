package game.square.estate;

import java.io.Serializable;

public class TitleDeed implements Serializable{

	private int rent;
	private int oneHouseRent;
	private int twoHouseRent;
	private int threeHouseRent;
	private int fourHouseRent;

	private int hotelRent;
	private int skyscraperRent;

	private int mortgageValue;

	private int houseCost;
	private int hotelCost;
	private int skyscrapperCost;

	public TitleDeed(int rent, int oneHouseRent, int twoHouseRent, int threeHouseRent, int fourHouseRent, int hotelRent,
			int skyscraperRent, int mortgageValue, int houseCost, int hotelCost, int skyscrapperCost) {
		this.rent = rent;
		this.oneHouseRent = oneHouseRent;
		this.twoHouseRent = twoHouseRent;
		this.threeHouseRent = threeHouseRent;
		this.fourHouseRent = fourHouseRent;
		this.hotelRent = hotelRent;
		this.skyscraperRent = skyscraperRent;
		this.mortgageValue = mortgageValue;
		this.houseCost = houseCost;
		this.hotelCost = hotelCost;
		this.skyscrapperCost = skyscrapperCost;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getOneHouseRent() {
		return oneHouseRent;
	}

	public void setOneHouseRent(int oneHouseRent) {
		this.oneHouseRent = oneHouseRent;
	}

	public int getTwoHouseRent() {
		return twoHouseRent;
	}

	public void setTwoHouseRent(int twoHouseRent) {
		this.twoHouseRent = twoHouseRent;
	}

	public int getThreeHouseRent() {
		return threeHouseRent;
	}

	public void setThreeHouseRent(int threeHouseRent) {
		this.threeHouseRent = threeHouseRent;
	}

	public int getFourHouseRent() {
		return fourHouseRent;
	}

	public void setFourHouseRent(int fourHouseRent) {
		this.fourHouseRent = fourHouseRent;
	}

	public int getHotelRent() {
		return hotelRent;
	}

	public void setHotelRent(int hotelRent) {
		this.hotelRent = hotelRent;
	}

	public int getSkyscraperRent() {
		return skyscraperRent;
	}

	public void setSkyscraperRent(int skyscraperRent) {
		this.skyscraperRent = skyscraperRent;
	}

	public int getMortgageValue() {
		return mortgageValue;
	}

	public void setMortgageValue(int mortgageValue) {
		this.mortgageValue = mortgageValue;
	}

	public int getHouseCost() {
		return houseCost;
	}

	public void setHouseCost(int houseCost) {
		this.houseCost = houseCost;
	}

	public int getHotelCost() {
		return hotelCost;
	}

	public void setHotelCost(int hotelCost) {
		this.hotelCost = hotelCost;
	}

	public int getSkyscrapperCost() {
		return skyscrapperCost;
	}

	public void setSkyscrapperCost(int skyscrapperCost) {
		this.skyscrapperCost = skyscrapperCost;
	}

}
