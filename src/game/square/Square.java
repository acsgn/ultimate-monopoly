package game.square;

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
	
	public void executeAction() {
		
	}
}