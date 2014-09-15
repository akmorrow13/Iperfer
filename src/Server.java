import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	// Attributes

	private ServerSocket serverSocket;
	private long bytesReceived = 0;
	private String rateString = null;
	
	// Constructor

	public Server(int listenPort) {

		// Creating the server socket

		try {
			serverSocket = new ServerSocket(listenPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Methods
	

	public void listenClients() throws IOException{

		// Waiting a connection from a client
		Socket clientSocket = serverSocket.accept();
		
		// Moment in which the connection is established and the first packet is received

		long firstMoment = System.currentTimeMillis();
				
		InputStream in = clientSocket.getInputStream();
		byte[] clientData = new byte[1024];
	

		// While the server keep receiving data,the loop continues

		while(in.read() != -1) {
			in.read(clientData);
			// It adds 1024 bytes.
			bytesReceived += clientData.length;
		}
		
		// In this moment, the client finished its sending of packets

		long lastMoment = System.currentTimeMillis();
		long totalTime = lastMoment - firstMoment;

		rateString = calculateRate(bytesReceived, totalTime);

		printSummary();


	}

	// This method prints out the summary data of bytes sent and rate for the client

	private void printSummary() {
		System.out.println("received=" + bytesReceived/1000.0 + " KB rate=" + rateString);
	}

	// This method calculates bandwidth for the client's connection
	
		private String calculateRate(long bytesReceived, long totalTime) {
			String rateString = null;
			float rate = (float) bytesReceived/ totalTime;
			
			
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
