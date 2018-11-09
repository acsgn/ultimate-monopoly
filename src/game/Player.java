package game;

import java.util.ArrayList;
import java.util.List;

import game.building.Building;
import game.card.Card;
import game.dice.SingletonDice;
import game.square.PropertySquare;
import game.square.RailroadSquare;
import game.square.Square;
import game.square.UtilitySquare;


public class Player{
	private String name;
	private int money;
	private Square location;
	//private Board board; 
	boolean inJail; 
	boolean isBankrupt; 
	private List<PropertySquare> propertySquares;
	private List<RailroadSquare> railRoadSquares;
	private List<UtilitySquare> utilitySquares;
	private ArrayList<GameListener> listeners;
	private String message;
	
	public Player(String name, int money){
		this.name = name; 
		this.money = money;
		//this.board = board; 
		listeners = new ArrayList<>();
		location = Board.getInstance().getSquare(0);
	}
	
	public void play(){
		List<Integer> diceRolls = rollDice();
		//move(diceRolls);
		//location.executeAction();
		message = "DOMAIN/ACTION/";
		message += "Regular Die 1: "+diceRolls.get(0)+"\n";
		message += "Regular Die 2: "+diceRolls.get(1)+"\n";
		if(diceRolls.get(2)==4){
			message += "Speed Die : BusIcon\n";
		}else if(diceRolls.get(2)==5){
			message += "Speed Die : Mr.MonopolyBonusMove\n";
		}else{
			message += "Speed Die : "+diceRolls.get(2) +"\n";
		}
		publishGameEvent(message);
		move(diceRolls);

		//location.executeAction(this);
	}
	public List<Integer> rollDice(){
		SingletonDice.getInstance().rollDice();
		return SingletonDice.getInstance().getFaceValues();
	}
	public void move(List<Integer> diceRolls){
		// Mr.Monopoly AND Bus Icon will be handled in the nest phase
		// Now we just sum the first two regular dice/ 
		int sum = diceRolls.get(0)+ diceRolls.get(1);
		int newLocationIndex = (location.getNumber()+sum) % Board.getInstance().getNoOfSquares();
		location = Board.getInstance().getSquare(newLocationIndex);
		message = "";
		message+= "DOMAIN/";
		message+= "MOVE/";
		message+= newLocationIndex;
		publishGameEvent(message);
	}
	public Square getLocation(){
		return location;
	}
	public boolean isInJail(){
		return inJail;
	}
	public boolean isBankrupt(){
		return isBankrupt;
	}
	public List<PropertySquare> getProperties(){
		return this.propertySquares;
	}
	public boolean payRent(Square s){
		//publishGameEvent(message);
		int rent =0;
		if(s instanceof PropertySquare){
			rent = ((PropertySquare)s).getRent();
		}else if(s instanceof RailroadSquare){
			rent = ((RailroadSquare)s).getRent();
		}else if(s instanceof UtilitySquare){
			rent = ((UtilitySquare)s).getRent();
		}
		return reduceMoney(rent);

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
	
	public void addGamelistener(GameListener lis){
		listeners.add(lis);
	}
	
	public void publishGameEvent(String message){
		for(GameListener l : listeners){
			l.onGameEvent(message);
		}
	}

	public List<PropertySquare> getPropertySquares() {
		return propertySquares;
	}

	public void setPropertySquares(List<PropertySquare> propertySquares) {
		this.propertySquares = propertySquares;
	}

	public List<RailroadSquare> getRailRoadSquares() {
		return railRoadSquares;
	}

	public void setRailRoadSquares(List<RailroadSquare> railRoadSquares) {
		this.railRoadSquares = railRoadSquares;
	}

	public List<UtilitySquare> getUtilitySquares() {
		return utilitySquares;
	}

	public void setUtilitySquares(List<UtilitySquare> utilitySquares) {
		this.utilitySquares = utilitySquares;
	}
	
	public void setInJail(boolean inJail){
		this.inJail = inJail;
	}
	
}
