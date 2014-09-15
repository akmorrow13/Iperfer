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
		String rateString = calculateRate(bytesSent, totalTime);
		printSummary(bytesSent, rateString);
		

	}
	
	// This method prints out the summary data of bytes sent and rate 
	// for the client
	
	public void printSummary(long sent, String rateString) {
		System.out.println("sent=" + sent/1000.0 + " KB rate=" + rateString);
	}
	
	// This method calculates bandwidth for the client's connection
	
	private String calculateRate(long bytesSent, long totalTime) {
		String rateString = null;
		float rate = (float) bytesSent/ totalTime;
		
		
		if(rate < 1024*10e0){
			rateString = rate + " KBps";
		}else if(rate > 1024*10e0 && rate < 1024*10e3){
			rateString = rate/10e3 + " MBps";
		}else if(rate > 1024*10e3 && rate < 1024*10e6){
			rateString = rate/10e6 + " GBps";
		}else if(rate > 1024*10e6 && rate < 1024*10e9){
			rateString = rate/10e9 + " TBps";
		}
		
		return rateString;
	}
	
}
