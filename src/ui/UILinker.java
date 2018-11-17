package ui;

public class UILinker {

	private static UILinker self;
	
	private UICreator uiC;
	private UIScreen uiS;
	
	private UILinker() {
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
	
	public static synchronized UILinker getInstance() {
		if (self == null) {
			self = new UILinker();
		}
		return self;
	}
	
}
