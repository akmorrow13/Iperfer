import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	
	// Attributes
	
	private int listenPort;
	ServerSocket serverSocket;
	
	public Server(int listenPort) {
		this.listenPort = listenPort;
		
		// Create the server socket
		
		try {
			serverSocket = new ServerSocket(listenPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Constructor
	
	public void listenClients() throws IOException{
		
		while(true) {
           Socket receivingSocket = serverSocket.accept();
           
           InputStream is = null;
           int bufferSize = receivingSocket.getReceiveBufferSize();
           System.out.println("Buffer size: " + bufferSize);
           
           BufferedReader dataFromClient = new BufferedReader(new InputStreamReader(receivingSocket.getInputStream()));
           
           //DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
           
           //clientSentence = inFromClient.;
           //System.out.println("Received: " + dataFromClient.);
           //capitalizedSentence = clientSentence.toUpperCase() + '\n';
           //outToClient.writeBytes(capitalizedSentence);
        }
		
		
	}
	
	// This method prints out the summary data of bytes sent and rate 
	// for the client
	
	public void printSummary(int received, double rate) {
		System.out.println("received= " + received + " KB rate= " + rate + " Mbps");
	}
	
	// This method calculates bandwidth for the client's connection
	
	public double calculateRate(int received, double time) {
		
		double rate = (double) received/ time;
		
		return rate;
	}
	
}
