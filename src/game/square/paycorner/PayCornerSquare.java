package game.square.paycorner;

import game.Player;
import game.square.Square;

public class PayCornerSquare extends Square {
	private int salary;
	private boolean landedOn;
	
	public PayCornerSquare(String name, int number) {
		super(name, number);
	}
	
	
	public int getSalary() {
		return salary;
	}


	public void setSalary(int salary) {
		this.salary = salary;
	}


	public boolean isLandedOn() {
		return landedOn;
	}


	public void setLandedOn(boolean landedOn) {
		this.landedOn = landedOn;
	}


	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		
	}


	
	
}