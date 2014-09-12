import java.io.IOException;


public class Server {
	
	// Attributes
	
	int listenPort;
	
	public Server(int listenPort) {
		this.listenPort = listenPort;
	}

	public void listenClients() throws IOException{
		
		
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
