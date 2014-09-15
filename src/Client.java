import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;



public class Client {
	
	// Attributes
	
	String serverHost;
	int serverPort;
	int time;
	static int data = 0;
	
	public Client(String serverHost, int serverPort, int time) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.time = time;
		
	}
	
	
	public void sendData() throws IOException {
		
		Socket clientSocket = null;
		clientSocket = new Socket();
		int bytesSent = 0;
		
		// this is not right.. bind to localhost?
		//clientSocket.bind(new InetSocketAddress(serverHost, serverPort));
		
		clientSocket.connect(new InetSocketAddress(serverHost, serverPort));
		
		
		// Prepare data of pure 0's to be sent
		
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		byte[] dataBytes = ByteBuffer.allocate(1024).putInt(data).array();
		
		
		// Calculate current running time for sent messages
		
		long maxTime = (long) time;
		maxTime *= 1000;
		
		long startTime = System.currentTimeMillis();
		long currentTime = System.currentTimeMillis();
		long totalTime;
		
		while ((totalTime = currentTime - startTime) < maxTime) {
		
			// Send data to server
			
			//out.println(dataBytes);
			
			out.write(dataBytes);

			currentTime = System.currentTimeMillis();
			
			System.out.println("");
			
			bytesSent += dataBytes.length;
		}

		// Close socket at print summary information
		
		clientSocket.close();
		double rate = calculateRate(bytesSent, totalTime);
		printSummary(bytesSent, rate);
		

	}
	
	// This method prints out the summary data of bytes sent and rate 
	// for the client
	
	public void printSummary(int sent, double rate) {
		System.out.println("sent= " + sent + " KB rate= " + rate + " Mbps");
	}
	
	// This method calculates bandwidth for the client's connection
	
	public double calculateRate(int sent, double time) {
		
		double rate = (double) sent/ time;
		
		return rate;
	}
	
}
