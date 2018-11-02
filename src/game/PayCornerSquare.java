package game;

public class PayCornerSquare extends Square {
	private int salary;
	private boolean landedOn;
	
	public PayCornerSquare(String name, int number) {
		super(name, number);
	}
	
	
	public int getSalary() {
		return salary;
	}


	public void setSalary(int salary) {
		this.salary = salary;
	}


	public boolean isLandedOn() {
		return landedOn;
	}


	public void setLandedOn(boolean landedOn) {
		this.landedOn = landedOn;
	}


	@Override
	public void executeAction() {
		// TODO Auto-generated method stub
		if (super.getName()=="Bonus") {
			if(landedOn) {
				salary=300;
			} else {
				salary=250;
			}
		} else if (super.getName()=="Go") {
			salary=200;
		} else if (super.getName()=="Pay Day") {
			if() {
				salary=400;
			} else {
				salary=300;
			}
		}
	}
	
	
}