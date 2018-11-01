package game;

public class GoToJailSquare extends Square {
	
	public GoToJailSquare(String name, int number) {
		super(name, number);
	}

	@Override
	public void executeAction() {
		// TODO Auto-generated method stub
		goToJail();
	}
	
	
}