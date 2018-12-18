package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Discovery implements Runnable {

	private final int DISCOVERY_PORT = 3021;
	private boolean destroy = false;
	private volatile ArrayList<String> IPAddresses;
	private ArrayList<InetAddress> broadcastList;
	private final String discoveryMessage = "ULTIMATE_MONOPOLY";

	public Discovery() {
		IPAddresses = new ArrayList<>();
		broadcastList = new ArrayList<>();
		findBroadcasts();
	}

	@Override
	public void run() {
		broadcast();
		try {
			DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT);
			socket.setBroadcast(true);
			socket.setSoTimeout(1000);
			while (true) {
				synchronized (this) {
					if (destroy)
						break;
				}
				byte[] recvBuf = new byte[2048];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				try {
					socket.receive(packet);
					String message = new String(packet.getData()).trim();
					if (message.equals(discoveryMessage)) {
						InetAddress IP = packet.getAddress();
						if (!IPAddresses.contains(IP.getHostAddress())) {
							byte[] sendData = discoveryMessage.getBytes();
							DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP, DISCOVERY_PORT);
							socket.send(sendPacket);
							IPAddresses.add(IP.getHostAddress());
							broadcast();
							synchronized (this) {
								notify();
							}
						}
					}
				} catch (SocketTimeoutException e) {
					synchronized (this) {
						notify();
					}
				}
			}
			socket.close();
		} catch (Exception ex) {
		}
	}

	private void findBroadcasts() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback() || !networkInterface.isUp())
					continue;
				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null)
						continue;
					broadcastList.add(broadcast);
				}
			}
		} catch (Exception e) {
		}
	}

	private void broadcast() {
		try {
			DatagramSocket c = new DatagramSocket();
			c.setBroadcast(true);
			byte[] sendData = discoveryMessage.getBytes();
			for (InetAddress broadcast : broadcastList) {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, DISCOVERY_PORT);
				c.send(sendPacket);
			}
			c.close();
		} catch (Exception ex) {
		}
	}

	public String getNumberOfPlayers() {
		try {
			synchronized (this) {
				wait();
			}
		} catch (InterruptedException e) {
		}
		return "PLAYERCOUNT/" + IPAddresses.size();
	}

	public ArrayList<String> getIPAddresses() {
		return IPAddresses;
	}

	public void destroy() {
		destroy = true;
	}

}
