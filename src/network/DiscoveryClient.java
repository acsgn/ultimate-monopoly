package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;

public class DiscoveryClient {

	public static void main(String[] args) throws UnknownHostException {
		DatagramSocket c;
		try {
			c = new DatagramSocket();
			c.setBroadcast(true);
			byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();
			// Broadcast the message over all the network interfaces

			ArrayList<InetAddress> broadcastList = new ArrayList<>();
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback() || !networkInterface.isUp()) {
					continue;
				}
				networkInterface.getInterfaceAddresses().stream().map(a -> a.getBroadcast()).filter(Objects::nonNull)
						.forEach(broadcastList::add);
			}

			// Send the broadcast package!
			for (InetAddress broadcast : broadcastList) {
				try {
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
					c.send(sendPacket);
				} catch (Exception e) {
				}
				System.out.println(">>> Request packet sent to: " + broadcast.getHostAddress());
			}

			System.out.println(">>> Done looping over all network interfaces. Now waiting for a reply!");
			// Wait for a response
			byte[] recvBuf = new byte[15000];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			c.receive(receivePacket);
			// We have a response
			System.out.println(">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
			// Check if the message is correct
			String message = new String(receivePacket.getData()).trim();
			if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
				// DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
				System.out.println(receivePacket.getAddress());
			}
			// Close the port!
			c.close();

		} catch (IOException ex) {
		}
	}
}
