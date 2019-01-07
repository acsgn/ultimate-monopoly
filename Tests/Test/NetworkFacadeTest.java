package Test;

import static org.junit.Assert.*;

import java.net.ServerSocket;

import org.junit.Test;

import network.NetworkFacade;

public class NetworkFacadeTest {

	NetworkFacade nF;

	@Test
	public void testIsConnected() {
		nF = NetworkFacade.getInstance();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new ServerSocket(302).accept();
				} catch (Exception e) {
				}				
			}
		}).start();
		/*nF.connect("localhost");
		assertTrue(nF.isConnected());
		assertTrue(nF.repOk());*/
	}

	@Test
	public void testConnect() {
		nF = NetworkFacade.getInstance();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new ServerSocket(302).accept();
				} catch (Exception e) {
				}				
			}
		}).start();
		/*nF.connect("localhost");
		assertNotNull(nF.getMessageSocket());
		assertTrue(nF.repOk());*/
	}
	
	

}
