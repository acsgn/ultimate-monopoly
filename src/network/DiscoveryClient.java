package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class DiscoveryClient {

	public static void main(String[] args) throws UnknownHostException {
		DatagramSocket c;
		try {
			c = new DatagramSocket();
			c.setBroadcast(true);
			byte[] sendData = "ULTIMATE_MONOPOLY_REQUEST".getBytes();
			try {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						InetAddress.getByName("255.255.255.255"), 302);
				c.send(sendPacket);
				System.out.println(">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
			} catch (Exception e) {
			}
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback() || !networkInterface.isUp())
					continue;
				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null)
						continue;
					try {
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 302);
						c.send(sendPacket);
					} catch (Exception e) {
					}
					System.out.println(">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: "
							+ networkInterface.getDisplayName());
				}
			}
			System.out.println(">>> Done looping over all network interfaces. Now waiting for a reply!");
			byte[] recvBuf = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			c.receive(receivePacket);
			String message = new String(receivePacket.getData()).trim();
			if (message.equals("ULTIMATE_MONOPOLY_RESPONSE"))
				System.out.println(receivePacket.getAddress());
			c.close();
		} catch (IOException ex) {
		}
	}
}
