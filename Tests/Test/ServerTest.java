package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import network.Server;

public class ServerTest {

	Server s;
	
	@Test
	public void testGetPlayers() {
		s = new Server(0);
		assertTrue(s.getPlayers().isEmpty());
		assertNotNull(s.getPlayers());
		assertTrue(s.repOk());
	}
	
}
