package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Board;
import game.Player;
import game.square.action.Bonus;

public class BonusTest {
	
	Bonus b;
	@Test
	public void testBonusWhenLanded() {
		b = new Bonus();
		Player p1 = new Player(new Board());
		int money = p1.getMoney();
		b.executeWhenLanded(p1);
		assertEquals(p1.getMoney(), money+300);
	}
	@Test
	public void testBonusWhenPassed() {
		b = new Bonus();
		Player p1 = new Player(new Board());
		int money = p1.getMoney();
		b.executeWhenPassed(p1);
		assertEquals(p1.getMoney(), money+250);
	}

}
