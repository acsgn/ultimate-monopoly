package game;

import ObserverPattern.Observer;

public class FrontController implements Observer
{ 
    private Dispatching Dispatching; 
    private String message; 
  
    public FrontController(MonopolyGame g) 
    { 
        Dispatching = new Dispatching(g.getPlayers().get(0)); 
    } 
  
    private boolean isAuthenticUser() 
    { 
        System.out.println("Authentication successfull."); 
        return true; 
    } 
  
    private void trackRequest(String request) 
    { 
        System.out.println("Requested View: " + request); 
    } 
  
    public void dispatchRequest() 
    { 
        trackRequest(message); 
        
        if(isAuthenticUser()) 
        { 
            Dispatching.dispatch(message); 
        }     
    }

	@Override
	public void update(String message) {
		// TODO Auto-generated method stub
		this.message = message;
		dispatchRequest();
	} 

}