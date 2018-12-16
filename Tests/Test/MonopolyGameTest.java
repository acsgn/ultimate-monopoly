package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Board;
import game.MonopolyGame;
import game.Player;

public class MonopolyGameTest {

	
	MonopolyGame mg;
	
	@Test
	public void testtoInt() {
		mg = new MonopolyGame();
		assertEquals(mg.toInt("3"), 3);
		assertTrue(mg.repOk());
	}

	@Test
	public void testupdateCurrentPlayer() {
		mg = new MonopolyGame();
		mg.getCurrentPlayer().setName("bb");
		Player player1 = new Player(new Board());
		player1.setName("aa");
		mg.getPlayers().add(player1);
		mg.updateCurrentPlayer(player1.getName());
		assertEquals(player1.getName(), mg.getCurrentPlayer().getName());
		assertTrue(mg.repOk());
	}

}
