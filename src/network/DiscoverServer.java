package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DiscoverServer {

	public static void main(String[] args) {
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(302, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			while (true) {
				byte[] recvBuf = new byte[2048];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				socket.receive(packet);
				System.out.println(">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
				System.out.println(">>>Packet received; data: " + new String(packet.getData()));
				String message = new String(packet.getData()).trim();
				if (message.equals("ULTIMATE_MONOPOLY_REQUEST")) {
					byte[] sendData = "ULTIMATE_MONOPOLY_RESPONSE".getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),
							packet.getPort());
					socket.send(sendPacket);
					System.out.println(">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
				}
			}
		} catch (IOException ex) {
		}

	}
}
