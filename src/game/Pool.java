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
	
	/**
	 * The amount in the pool is increased by the given
	 * amount
	 * @param amount the given amount to increase the overall
	 * amount in the pool
	 */
	public void payToPool(int amount) {
		this.amount+=amount;
	}
	
	public int getAmount() {
		int result = amount;
		amount = 0;
		return result;
	}
	
}
