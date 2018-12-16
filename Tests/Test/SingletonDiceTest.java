package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.dice.RegularDie;
import game.dice.SingletonDice;
import game.dice.SpeedDie;

public class SingletonDiceTest {

	RegularDie r1;
	SpeedDie s1;

	@Test
	public void testSingletonDice() {

		int[] values = new int[3];
		SingletonDice.getInstance().rollDice();
		values = SingletonDice.getInstance().getFaceValues();
		int[] values2 = new int[3];
		SingletonDice.getInstance().rollDice();
		values2 = SingletonDice.getInstance().getFaceValues();
		int value = values[0] * 7 + values[1] * 13 + values[2] * 23;
		int value1 = values2[0] * 7 + values2[1] * 13 + values2[2] * 23;

		assertNotEquals(value, value1);
		assertTrue(SingletonDice.getInstance().repOk());

	}

	@Test
	public void testRegularDice() {
		r1 = new RegularDie();
		

		r1.roll();

		int value = r1.getFaceValue();

		r1.roll();

		int value1 = r1.getFaceValue();

		r1.roll();

		int value2 = r1.getFaceValue();
		
		boolean test = false;
		if (value== value1 && value1==value2 ) 
			test = true;

		assertFalse(test);
		assertTrue(SingletonDice.getInstance().repOk());

	}

	@Test
	public void testSpeedDice() {
		s1 = new SpeedDie();
		

		s1.roll();

		int value = s1.getFaceValue();

		s1.roll();

		int value1 = s1.getFaceValue();

		s1.roll();

		int value2 = s1.getFaceValue();
		
		boolean test = false;
		if (value== value1 && value1==value2 ) 
			test = true;

		assertFalse(test);
		assertTrue(SingletonDice.getInstance().repOk());

	}

}
