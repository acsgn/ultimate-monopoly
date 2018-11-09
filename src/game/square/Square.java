package game.square;

import game.Player;

public abstract class Square {
	private String name;
	private int number;
	
	public Square(String name, int number) {
		super();
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}
	
	public abstract void executeAction(Player player);
}