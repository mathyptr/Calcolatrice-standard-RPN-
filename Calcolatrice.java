import java.io.BufferedReader;
import java.io.InputStreamReader;

class Calcolatrice {
	
	public static void SplashScreen() {
		String progname="Calcolatrice";
		System.out.println("********************************************************************************");
		System.out.println("*                           Calcolatore RPN                                    *");
		System.out.println("* Utilizzo da terminale:                                                       *");
		System.out.println("* "+progname+" s numport (per eseguire il server)                              *");
		System.out.println("* "+progname+" c servername numport rpnMODE (per eseguire il client)           *");
		System.out.println("*  Dove rpnMODE e':                                                            *");
		System.out.println("*   <y> (per utilizzare la modalita' rpn)                                      *");
		System.out.println("*   <altri caratteri> (per utilizzare una calcolatrice standard)               *");
		System.out.println("* Non fornire alcun parametro per immettere i parametri da tastiera non fornire*");
		System.out.println("* Esempio utilizzo non rpn: per 2+2 devi inserire 2<invio>+<invio>2<invio>     *");
		System.out.println("* Esempio utilizzo rpn: per 2+2 devi inserire 2<invio>2<invio>+<invio>         *");	
		System.out.println("* Con la modalita' rpn e' possibile effettuare piu' operazioni consecutive     *");	
		System.out.println("* Esempio(6+6)/(2+2): 6<invio>6<invio>+<invio>2<invio>2<invio>+<invio>/        *");
		System.out.println("********************************************************************************");
	}
	
	
	public static  void doServer(int port) {
		Server myServer= new Server(port);
		myServer.Calc();
	}
	
	public static void doClient(String serverName,int port,char rpn) {
		Client myClient= new Client(serverName,port,rpn);
		while(true)
			myClient.invioDati();	
	}
	
	public static void main(String[] args) {
		SplashScreen();
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);
		String daemon="", serverName="";
		String scelta="s";
		char rpn='n';
		int port=0,index=1;
		
		if (args.length >1) {
			if (args[0].length() >1) {
				SplashScreen();
				System.exit(1);
			}
			daemon=args[0];
			if(!daemon.toLowerCase().equals("s") && !daemon.toLowerCase().equals("c"))
			{
				System.out.println("Errore parametri!!!");
				SplashScreen();
				System.exit(1);
			}
			if(daemon.equals("c")) {
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
		    if (args.length ==4&&args[3].length() ==1)
		    	rpn=args[3].toLowerCase().charAt(0);
		}
		else if( args.length <= 1)	
		{
			System.out.println("s per server o altri caratteri per client");
			try {
				scelta=tastiera.readLine();
			}
	    	catch (Exception e) {
	    		System.out.println("errore input");
			}
		
			if(!scelta.toLowerCase().equals("s")) {
				System.out.println("Calcolatrice RPN (y/n)?");
				try {
					rpn=tastiera.readLine().toLowerCase().charAt(0);
				}
				catch (Exception e){
					System.out.println("errore input");
				}
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
			doClient(serverName,port,rpn);
					
	}

}
