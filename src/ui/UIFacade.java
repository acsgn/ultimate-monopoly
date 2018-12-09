package ui;

public class UIFacade {

	private static UIFacade self;
	
	private UICreator uiC;
	private UIScreen uiS;
	
	private UIFacade() {
	}

	public void connectionDone() {
		uiC.close();
		uiS.start();
	}
	
	public void initalize(UICreator uiC, UIScreen uiS) {
		this.uiC = uiC;
		this.uiS = uiS;
	}
	
	public static synchronized UIFacade getInstance() {
		if (self == null) {
			self = new UIFacade();
		}
		return self;
	}
	
}
