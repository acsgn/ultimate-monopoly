package game;

public class Controller {
	private MonopolyGame monopolyGame;
	
	public Controller(MonopolyGame monopolyGame){
		this.monopolyGame = monopolyGame;
	}
	
	public void dispatchMessage(String message){
		monopolyGame.executeMessage(message);
	}
	
	//Who should control the game (the controller or MonopolyGame).
	// I mean who sould call the player funcitons to ececute based on the message received from UI. 
}
