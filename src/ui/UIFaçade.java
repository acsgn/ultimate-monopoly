package ui;

public class UIFaçade {

	private static UIFaçade self;
	
	private UICreator uiC;
	private UIScreen uiS;
	
	private UIFaçade() {
	}
	
	public void connectionError() {
		uiC.serverNotFound();
	}

	public void connectionDone() {
		uiC.close();
		uiS.start();
	}
	
	public void initalize(UICreator uiC, UIScreen uiS) {
		this.uiC = uiC;
		this.uiS = uiS;
	}
	
	public static synchronized UIFaçade getInstance() {
		if (self == null) {
			self = new UIFaçade();
		}
		return self;
	}
	
}
