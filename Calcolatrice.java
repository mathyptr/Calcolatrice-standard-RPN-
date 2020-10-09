import java.io.BufferedReader;
import java.io.InputStreamReader;

class Calcolatrice {
	
	public static void SplashScreen() {
		String progname="Calcolatrice";
		System.out.println("***************************************");
		System.out.println("Calcolatore RPN");
		System.out.println("Utilizzo da terminale:");
		System.out.println(progname+" s numport (per eseguire il server)");
		System.out.println(progname+" c servername numport (per eseguire il client)");
		System.out.println("Esempio: per 2+2 devi inserire 2<invio>+<invio>2<invio>");
				
		System.out.println("***************************************");
				
	}
	
	
	public static  void doServer(int port) {
		Server myServer= new Server(port);
		myServer.Calc();
	}
	
	public static void doClient(String serverName,int port) {
		Client myClient= new Client(serverName,port);
		while(true)
			myClient.invioDati();	
	}
	
	public static void main(String[] args) {
		SplashScreen();
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);
		String daemon="", serverName="";
		String scelta="s";
		int port=0,index=1;
		
		if (args.length >1) {
			daemon=args[0];
			if(!daemon.equals("s")) {
				serverName=args[1];
				index=2;
				scelta="c";
			}
		    try {
		    	port=(Integer.valueOf(args[index]).intValue());
		    } catch (NumberFormatException e) {
		        System.err.println("L'argomento" + args[index] + " deve essere un intero (numero Porta)!");
		        System.exit(1);
		    }
		}
		else if( args.length == 0)	
		{
			System.out.println("s per server o altri caratteri per client");
			try {
				scelta=tastiera.readLine();
			}
	    	catch (Exception e) {
	    		System.out.println("errore input");
			}
		
			if(!scelta.equals("s")) {
				System.out.println("Inserisci l'indirizzo del server");
				try {
					serverName=tastiera.readLine();
				}
				catch (Exception e){
					System.out.println("errore input");
				}
			}
			System.out.println("Inserisci la porta del server");
			try {
				port=(Integer.valueOf(tastiera.readLine()).intValue());
			}
			catch (Exception e){
					System.out.println("errore input");
			}
		}		
		
		if(scelta.equals("s")) 
			doServer(port);
		else		
			doClient(serverName,port);
					
	}

}
