import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;



public class Client {
	
	// Attributes
	
	private String serverHost;
	private int serverPort;
	private int time;
	private final int DATA = 0;
	
	// Constructor
	
	public Client(String serverHost, int serverPort, int time) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.time = time;
		
	}
	
	
	public void sendData() throws IOException, InterruptedException {
		
		Socket clientSocket = null;
		clientSocket = new Socket();
		int bytesSent = 0;
		
		clientSocket.connect(new InetSocketAddress(serverHost, serverPort));
		
		// Prepare data of pure 0's to be sent
		
		byte[] dataBytes = ByteBuffer.allocate(1024).putInt(DATA).array();
		
		OutputStream out = clientSocket.getOutputStream(); 
		DataOutputStream dos = new DataOutputStream(out);
		
		//PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		
		
		
		
		// Calculate current running time for sent messages
		
		long maxTime = (long) time;
		maxTime *= 1000;
		
		long startTime = System.currentTimeMillis();
		long currentTime = System.currentTimeMillis();
		long totalTime = 1;
			
		
		while ((totalTime = currentTime - startTime) < maxTime) {
		
			// Send data to server
			dos.write(dataBytes);
			dos.flush();
			
			currentTime = System.currentTimeMillis();
			
			bytesSent += dataBytes.length;
			TimeUnit.SECONDS.sleep(1);
		}
		
		dos.close();

		// Close socket at print summary information
		
		clientSocket.close();
		double rate = calculateRate(bytesSent, totalTime);
		printSummary(bytesSent, rate);
		

	}
	
	// This method prints out the summary data of bytes sent and rate 
	// for the client
	
	public void printSummary(int sent, double rate) {
		System.out.println("sent=" + sent + " KB rate=" + rate + " Mbps");
	}
	
	// This method calculates bandwidth for the client's connection
	
	private float calculateRate(long bytesReceived, long totalTime) {
		float rate = (float) bytesReceived/ totalTime;
		return rate;
	}
	
}
