import java.io.*;
import java.net.*;
/**
 * Classe per l'implementazione di un Server
 * @author Patrissi Mathilde
 */

public class Server { 
	ServerSocket server;
	Socket clientSocket;
	BufferedReader inputClient;
	PrintStream outClient=null;	
	String clientData;
	String result;
	int numOp=0;
	/**
	 * Costruttore della classe Server
	 * 
	 * @param port  int
	 */
	public Server(int port) {
		try {
			server= new ServerSocket(port); //viene creato un socket data la porta
			clientSocket= server.accept(); //il server resta in attesa di richieste di connessione da parte del client
			server.close(); //viene chiuso il socket per non far restare il server in ascolto di ulteriori connessioni
			inputClient= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));// nelle due istruzioni seguenti vengono creati gli stream per leggere/scrivere i dati dal/al client
			outClient = new PrintStream(clientSocket.getOutputStream(), true);
			
			} 
		catch (IOException e) {
			System.out.println("errore I/O");
		}
	}
	/**
	 * Metodo per il controllo dell'operazione:
	 * possono essere inseriti solo numeri
	 * 
	 * @param OpUno  String
	 * @return True se Opuno e' un numero
	 */
	public boolean CheckData(String OpUno) {
	     try{
	    	 double a=(Double.valueOf(OpUno).doubleValue()); //controllo che l'utente abbia effettivamenteinserito dei numeri
	     }
	     catch (Exception e) {
	 		return false;
	     }	 		
		return true;
	}
	
	/**
	 * Metodo per calcolo dell'operazione
	 * 
	 * @param op1  String
	 * @param op2  String
	 * @param operator  char
	 * @return il risultato dell'operazione
	 */
	public double exec(String op1, String op2, char operator) {
		double a,b,res=0;;
		a=(Double.valueOf(op1).doubleValue()); //viene effettuata una conversione da striga a double
		b=(Double.valueOf(op2).doubleValue());
		switch(operator) { //in base all'operatore inserito viene eseguita l'oppurtuna operazione
		case '+' : 
			res=a+b;
			break;
		
		case '-' : 
			res=a-b;
			break;
		
		case '/' : 
			res=a/b;
			break;
		
		case '*' : 
			res=a*b;
			break;
		
		default : 
			System.out.println("Errore" + "\n"); 
		
		}
		numOp++;
		return res; //viene restituito il risultato
	}
	
	/**
	 * Metodo per la gestione del calcolo dell'operazione:
	 * dopo aver letto l'operazione richiesta vengono 
	 * richiamati e gestiti i metodi necessari per l'ottenimento
	 * del risultato e il suo invio al client
	 * 
	 */
	public void Calc() {
		System.out.println(".....in attesa di dati.");
		java.util.Stack formula= new java.util.Stack (); //viene utilizzato uno stack per implementare una calcolatrice rpn
		String op1,op2;
		double res=0;
		while(true)
		{
			try{
				clientData= inputClient.readLine(); //vengono letti i valori inseriti dal client
				if (CheckData(clientData)) { //se i valori letti sono numeri vengono inseriti nello stack
					formula.push(clientData);	
					outClient.println("OK");
				}
				else //se i valori inseriti non sono numeri vengono estratti gli ultimi due valori inseriti
				{
					op2=(String)formula.pop(); 
					op1=(String)formula.pop();
					res=exec(op1,op2,clientData.charAt(0)); //viene effettuata l'operazione
					outClient.println("La risposta e': "+Double.toString(res)); //il risultato viene scritto al client
					formula.push(Double.toString(res)); //viene inserito il risultato nello stack, per un'eventuale successiva operazione effettuabile con la calcolatrice rpn
					System.out.println("****************");
					System.out.println("Ecco il risultato della tua operazione n."+numOp+" :"+res);
					System.out.println("****************");
				}
			}
			catch(Exception e) {
				outClient.println("Il server risponde: errore lettura da client");
				System.out.println("errore lettura da client");
				break;
			}
		}			
		
	}
}
