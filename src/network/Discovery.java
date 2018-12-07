package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;

public class Discovery implements Runnable {

	private boolean stop = false;
	private ArrayList<String> IPAddresses;

	public Discovery() {
		IPAddresses = new ArrayList<>();
	}

	@Override
	public void run() {
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(302, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			while (true) {
				synchronized (this) {
					if (stop)
						break;
				}
				byte[] recvBuf = new byte[2048];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				socket.receive(packet);
				String message = new String(packet.getData()).trim();
				if (message.equals("ULTIMATE_MONOPOLY_REQUEST")) {
					String response = InetAddress.getLocalHost().getHostAddress();
					for (String ip : IPAddresses)
						response += ip;
					System.out.println(response);
					byte[] sendData = response.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),
							packet.getPort());
					socket.send(sendPacket);
					IPAddresses.add(sendPacket.getAddress().getHostAddress());
					broadcast();
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
			byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();

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

			for (InetAddress broadcast : broadcastList) {
				try {
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
					c.send(sendPacket);
				} catch (Exception e) {
				}
			}

			byte[] recvBuf = new byte[15000];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			c.receive(receivePacket);
			String message = new String(receivePacket.getData()).trim();
			for (String ip : message.split("/")) {
				if (!IPAddresses.contains(ip)) 
					IPAddresses.add(ip);
			}
			c.close();

		} catch (IOException ex) {
		}
	}

	public void destroy() {
		stop = true;
	}

	public ArrayList<String> getIPAddresses() {
		return IPAddresses;
	}

}
