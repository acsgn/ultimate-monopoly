package ui;

import ObserverPattern.Observer;

public class UIController implements Observer {
	private Dispatching Dispatching;
	private String message;

	public UIController(UIScreen control) {
		Dispatching = new Dispatching(control);
	}

	public void dispatchRequest() {

		Dispatching.dispatch(message);

	}

	@Override
	public void update(String message) {
		// TODO Auto-generated method stub
		this.message = message;
		dispatchRequest();
	}

}