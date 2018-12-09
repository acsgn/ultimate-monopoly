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
import java.util.concurrent.ConcurrentLinkedQueue;

public class Discovery implements Runnable {

	private static final int DISCOVERY_PORT = 3021;
	private boolean destroy = false;
	private ConcurrentLinkedQueue<String> IPAddresses;

	public Discovery() {
		IPAddresses = new ConcurrentLinkedQueue<>();
		try {
			IPAddresses.add(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
		}
	}

	@Override
	public void run() {
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
				if (message.equals("ULTIMATE_MONOPOLY")) {
					String response = "";
					for (String ip : IPAddresses)
						response += ip + "/";
					byte[] sendData = response.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),
							packet.getPort());
					socket.send(sendPacket);
					String IP = sendPacket.getAddress().getHostAddress();
					if (!IPAddresses.contains(IP)) {
						IPAddresses.add(IP);
					}
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

			ArrayList<InetAddress> broadcastList = new ArrayList<>();
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback() || !networkInterface.isUp() || !networkInterface.getName().contains("wlan"))
					continue;
				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null)
						continue;
					broadcastList.add(broadcast);
				}
			}

			for (InetAddress broadcast : broadcastList) {
				try {
					System.out.println(broadcast);
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast,
							DISCOVERY_PORT);
					c.send(sendPacket);
				} catch (Exception e) {
				}
			}

			byte[] recvBuf = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			c.receive(receivePacket);
			String message = new String(receivePacket.getData()).trim();
			for (String ip : message.split("/")) {
				if (!ip.equals("") && !IPAddresses.contains(ip))
					IPAddresses.add(ip);
			}
			synchronized (this) {
				notify();
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
		ArrayList<String> IPs = new ArrayList<>();
		for (String IP : IPAddresses) {
			IPs.add(IP);
			System.out.println(IP);
		}
		return IPs;
	}

	public void destroy() {
		destroy = true;
	}

}
