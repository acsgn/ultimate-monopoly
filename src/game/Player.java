package game;

import java.util.ArrayList;
import java.util.List;

import ObserverPattern.Subject;
import game.building.Building;
import game.card.Card;
import game.dice.Dice;
import game.square.PropertySquare;
import game.square.Square;
import ObserverPattern.Observer;


public class Player implements Subject{
	private String name;
	private int money;
	Piece piece; 
	Dice[] dice; 
	Board board; 
	boolean inJail; 
	boolean isBankrupt; 
	List<PropertySquare> properties;
	ArrayList<Observer> observers;
	private String message;
	
	public Player(String name, int money, Dice[] dice, Board board){
		this.name = name; 
		this.money = money;
		this.dice = dice;
		this.board = board; 
		observers = new ArrayList<>();
	}
	
	public void play(){
		
		
		//location.executeAction(this);
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
		message = "You paid Rent";
		notifyObservers();
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
	
	/// Receive from the Dispatching
	public void receiveMessage(String message){
		if(message.equals("Pay Rent")){
			this.payRent( new PropertySquare("this", 3,3,3,this));
			System.out.println("Message recieved : "+ message);
		}
	}

	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for(Observer o: observers){
			o.update(message);
		}
	}
}
