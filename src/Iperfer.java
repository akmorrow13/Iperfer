import java.io.IOException;


public class Iperfer {
	

	public static void main(String[] args) throws IOException, InterruptedException {
		
		int argCount = args.length;
		
		if(argCount == 0){
			System.out.println("Error: missing or additional arguments");
			System.exit(-2);
		}
		
		String mode = args[0];
		
		String serverHost = "";
		int port = -1;
		int time = -1;
		
		// Parse parameters
		
		for (int i = 1; i < argCount; i+= 2) {
			
			if (args[i].equals("-h")) {
				serverHost = args[i+1];
			} else if (args[i].equals("-p")) {
				port = Integer.parseInt(args[i+1]);
				if(!isPortValid(port)){
					System.out.println("Port out of bounds");
					System.exit(-1);
				}
			} else if (args[i].equals("-t")) {
				time = Integer.parseInt(args[i+1]);
				if (!isTimeValid(time)) {
					System.out.println("Invalid time");
					System.exit(-1);
				}
			} else {
				System.out.println("Invalid option " + args[i]);
				System.exit(-1);
			}
		}
		
		

		
		// Server mode
		
		if (argCount == 3 && mode.equals("-s")) { 
			Server server = new Server(port);
			server.listenClients();
		
		// Client mode
		} else if (argCount == 7 && mode.equals("-c")) { 
			Client client = new Client(serverHost, port, time);
			client.sendData();
			
		// Invalid parameters
		} else {
			
			System.out.println("Error: missing or additional arguments");
			System.exit(-2);
		}
			 
		// If everything finished OK
		System.exit(0);	
		
	}
	
	//Method to check if the port is valid
	
	private static boolean isPortValid(int port){
		if (port >= 1024 && port < 65536) {
			return true;
		} else {
			return false;
		}
	}
	
	// Method to check if time for client is valid
	
	private static boolean isTimeValid(int time) {
		if (time >= 0) {
			return true;
		} else {
			return false;
		}
	}

}
