package game;

import java.util.List;

public class Player {
	private String name;
	private int money;
	Piece piece; 
	Dice[] dice; 
	Board board; 
	boolean inJail; 
	boolean isBankrupt; 
	List<PropertySquare> properties;
	
	public Player(String name, int money, Dice[] dice, Board board){
		this.name = name; 
		this.money = money;
		this.dice = dice;
		this.board = board; 
	}
	
	public void play(){
		
	}
	public int rollDice(){
		return 0;
	}
	public void move(){
		
	}
	public Square getLocation(){
		return piece.getLocation();
	}
	public boolean isInJail(){
		return inJail;
	}
	public boolean isBankrupt(){
		return isBankrupt;
	}
	public List<PropertySquare> getProperties(){
		return this.properties;
	}
	public boolean payRent(Square s){
		return false;

	}
	public void collectRent(int rent){
		money+= rent;
	}
	public boolean buySquare(){
		return false;

	}
	public boolean passGo(int prevIndex, int newIndex){
		return false;
	}
	public boolean passPayDay(int prevIndex, int newIndex){
		return false;

	}
	public boolean passBonus(int prevIndex, int newIndex){
		return false;

	}
	
	public boolean buyBuilding(Building building, PropertySquare propertySquare){
		return false;

	}
	public void sellBuilding(Building building, PropertySquare propertySquare){
		
	}
	public void pickCard(Card card){
		card.executeAction();
	}
	public boolean reduceMoney(int m){
		if(money>m){
			this.money-=m;
			return true;
		}
		return false;
	}
	public void increaseMoney(int m){
		this.money+=m;
	}
	public String getName(){
		return name; 
	}
}
