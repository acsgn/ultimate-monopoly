package Test;

import static org.junit.Assert.*;

import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

import network.MessageSocket;

public class MessageSocketTest {

	MessageSocket mS;

	@Test
	public void testReceiveMessage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new MessageSocket(new ServerSocket(302).accept()).sendMessage("TEST");
				} catch (Exception e) {
				}				
			}
		}).start();
		try {
			mS = new MessageSocket(new Socket("localhost", 302));
		} catch (Exception e) {
		}
		assertEquals(mS.receiveMessage(), "TEST");
		assertTrue(mS.repOk());
	}
	
	@Test
	public void testXClose() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new MessageSocket(new ServerSocket(302).accept()).close();
				} catch (Exception e) {
				}				
			}
		}).start();
		try {
			mS = new MessageSocket(new Socket("localhost", 302));
		} catch (Exception e) {
		}
		mS.close();
		assertTrue(mS.getSocket().isClosed());
		assertTrue(mS.repOk());
	}

}
