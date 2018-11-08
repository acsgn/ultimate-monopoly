package game.dice;

import java.util.ArrayList;
import java.util.List;

public class SingletonDice {
	private RegularDie regularDie1; 
	private RegularDie regularDie2;
	private SpeedDie speedDie; 
	
	private static SingletonDice dice;
	
	private SingletonDice(){
		regularDie1 = new RegularDie();
		regularDie2 = new RegularDie();
		speedDie = new SpeedDie();
	}
	
	public static synchronized SingletonDice getInstance(){
		if(dice==null){
			dice = new SingletonDice();
		}
		return dice;
	}
	
	public void rollDice(){
		regularDie1.roll();
		regularDie2.roll();
		speedDie.roll();
	}
	public List<Integer> getFaceValues(){
		List<Integer> diceRolls = new ArrayList<>();
		int r1 = regularDie1.getFaceValue();
		int r2 = regularDie2.getFaceValue();
		int r3 = speedDie.getFaceValue();
		diceRolls.add(r1);
		diceRolls.add(r2);
		diceRolls.add(r3);
		return diceRolls;
	}
	
}
