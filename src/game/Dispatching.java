package game;

class Dispatching { 
	private Player player;
    public Dispatching(Player player) 
    { 
    	this.player = player;
    } 
  
    public void dispatch(String message) 
    { 
          player.receiveMessage(message);
    } 
} 