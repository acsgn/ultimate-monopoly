package game.square.action;


public class ActionSquareFactory {
	
	private static ActionSquareFactory self;

	public void getActionSquare() {
		
	}

	private ActionSquareFactory() {
	}

	public static synchronized ActionSquareFactory getInstance() {
		if (self == null)
			self = new ActionSquareFactory();
		return self;
	}

}
