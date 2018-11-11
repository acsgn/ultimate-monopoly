package game;

public class Pool {
	
	private int amount;
	
	private static Pool pool;
	
	private Pool ()	{
		amount = 0;
	}
	
	public static synchronized Pool getInstance(){
		if(pool==null){
			pool = new Pool();
		}
		return pool;
	}
	
	public void payToPool(int amount) {
		this.amount+=amount;
	}
	
	public int getAmount() {
		int result = amount;
		amount = 0;
		return result;
	}
	
}
