package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Discovery implements Runnable {

	private static final int DISCOVERY_PORT = 3021;
	private boolean destroy = false;
	private volatile ArrayList<String> IPAddresses;

	public Discovery() {
		IPAddresses = new ArrayList<>();
		try {
			IPAddresses.add(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
		}
	}

	@Override
	public void run() {
		broadcast();
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(DISCOVERY_PORT);
			socket.setBroadcast(true);
			while (true) {
				synchronized (this) {
					if (destroy)
						break;
				}
				byte[] recvBuf = new byte[2048];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				socket.receive(packet);
				String message = new String(packet.getData()).trim();
				InetAddress IP = packet.getAddress();
				if (message.equals("ULTIMATE_MONOPOLY")) {
					String response = "IP_ADDRESSES";
					for (String ip : IPAddresses)
						response += "/" + ip;
					byte[] sendData = response.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP, DISCOVERY_PORT);
					socket.send(sendPacket);
					IPAddresses.add(IP.getHostAddress());
					synchronized (this) {
						notify();
					}
					continue;
				}
				String[] parsed = message.split("/");
				if (parsed[0].equals("IP_ADDRESSES")) {
					for (int i = 1; i < parsed.length; i++)
						if (!IPAddresses.contains(parsed[i]))
							IPAddresses.add(parsed[i]);
					synchronized (this) {
						notify();
					}
				}
			}
		} catch (IOException ex) {
		}
	}

	public void broadcast() {
		DatagramSocket c;
		try {
			c = new DatagramSocket();
			c.setBroadcast(true);
			byte[] sendData = "ULTIMATE_MONOPOLY".getBytes();

			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback() || !networkInterface.isUp())
					continue;
				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null)
						continue;
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast,
							DISCOVERY_PORT);
					c.send(sendPacket);
				}
			}
			c.close();
		} catch (IOException ex) {
		}
	}

	public String getNumberOfPlayers() {
		try {
			synchronized (this) {
				wait();
			}
		} catch (InterruptedException e) {
		}
		System.out.println("PLAYERCOUNT/" + IPAddresses.size());
		return "PLAYERCOUNT/" + IPAddresses.size();
	}

	public ArrayList<String> getIPAddresses() {
		return IPAddresses;
	}

	public void destroy() {
		destroy = true;
	}

}
