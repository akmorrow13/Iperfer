import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

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
		long bytesSent = 0;
		
		try{
			clientSocket.connect(new InetSocketAddress(serverHost, serverPort));
		}catch(ConnectException ex){
			System.out.println("The program cannot conect to the server.");
			System.exit(-3);
		}
		
		// Prepare data of pure 0's to be sent
		
		byte[] dataBytes = ByteBuffer.allocate(1024).putInt(DATA).array();
		
		OutputStream out = clientSocket.getOutputStream(); 
		DataOutputStream dos = new DataOutputStream(out);		
		
		
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
			
			// It adds 1024 bytes.
			bytesSent += dataBytes.length;

		}
		
		// Close the socket connection
		dos.close();

		// Close socket at print summary information
		
		clientSocket.close();
		float rate = calculateRate(bytesSent, totalTime);
		printSummary(bytesSent, rate);
		

	}
	
	// This method prints out the summary data of bytes sent and rate 
	// for the client
	
	public void printSummary(long sent, float rate) {
		System.out.println("sent=" + sent/1000.0 + " KB rate=" + 8*rate/1000.0  + " Mbps");
	}
	
	// This method calculates bandwidth for the client's connection
	
	private float calculateRate(long bytesReceived, long totalTime) {

		float rate = (float) (bytesReceived/ totalTime);
		
		// This rate is in KBs
		return rate;
	}
	
}
