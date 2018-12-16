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
	 * @overview The amount in the pool is increased by the 
	 * given amount
	 * @requires 
	 * @modifies Pool's 'amount' field.
	 * @effects Affects Player since usually a player pays the money
	 * to pool, decreasing their money.
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
