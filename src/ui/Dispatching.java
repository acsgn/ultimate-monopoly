package ui;

import game.Player;

class Dispatching { 
	private UIScreen control;
    public Dispatching(UIScreen control) 
    { 
    	this.control = control;
    } 
  
    public void dispatch(String message) 
    { 
    	control.receiveMessage(message);
    } 
} 