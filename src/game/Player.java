package game;

import java.util.ArrayList;
import java.util.List;

import game.building.Building;
import game.card.Card;
import game.square.PropertySquare;
import game.square.RailroadSquare;
import game.square.Square;
import game.square.UtilitySquare;


public class Player{
	private String name;
	private int money;
	private Square location;
	Board board; 
	boolean inJail; 
	boolean isBankrupt; 
	private List<PropertySquare> propertySquares;
	private List<RailroadSquare> railRoadSquares;
	private List<UtilitySquare> utilitySquares;
	private ArrayList<GameListener> listeners;
	private String message;
	
	public Player(String name, int money, Board board){
		this.name = name; 
		this.money = money;
		this.board = board; 
		listeners = new ArrayList<>();
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
		message = "You paid Rent";
		publishGameEvent(message);
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
	
	
	
}
