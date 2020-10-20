import java.io.*;
import java.net.*;
/**
 * Classe per l'implementazione di un Client
 * @author Patrissi Mathilde
 */
public class Client {
String serverName;
int serverPort;
DataInputStream inKeyboard;
PrintStream outServer=null;
Socket clientSocket;
BufferedReader keyboard;
BufferedReader inputServer;
String data;
String result;
char rpn='n';
java.util.HashMap<String, String> operatori;
java.util.Vector<String> Op;
/**
 * Costruttore della classe Client
 * 
 * @param serverName	String
 * @param serverPort  int
 * @param rpn char
 */
public Client(String serverName,int serverPort,char rpn) {
	try {
		this.rpn=rpn;
		operatori=new java.util.HashMap<String, String>(); //viene utilizzata un'hashmap contenente gli operatori fondamentali
		Op= new java.util.Vector<String>();
		operatori.put("+","+");
		operatori.put("-","-");
		operatori.put("/","/");
		operatori.put("*","*");
		clientSocket= new Socket (serverName, serverPort);//viene creato un socket dato il nome del server e la sua porta
		inputServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outServer = new PrintStream(clientSocket.getOutputStream(), true);
	    System.out.println("Connessione stabilita");	    	    
	}
	catch(IOException e) {
		System.out.println("Host non riconosciuto");
	}
	
	
}
/**
 * Metodo per l'identificazione dell'operatore richiesto
 * 
 * @param OpUno String
 * @return True se Opuno è un operatore
 */
public boolean CheckOperator( String OpUno) {
	return operatori.containsKey(OpUno); //viene controllato che il valore inserito sia uno dei quattro operatori fondamentali
}

/**
 * Metodo per la gestione dell'invio dell'operazione:
 * controlla se e' stato richiesta una calcolatrice rpn
 * e richiama i metodi necessari.
 */
public void invioDati() {
	if(rpn=='y')
		invioDatiRPN();
	else
		invioDatiNoRPN();
}	

/**
 * Metodo per l'invio dell'operazione con la richiesta 
 * di una calcolatrice non rpn
 */
public void invioDatiNoRPN() {
	String OpUno="";
	InputStreamReader input = new InputStreamReader(System.in);
	keyboard = new BufferedReader(input);
	int i=0;
	
	while(i<3) {
	System.out.print("> ");
	try {
	  OpUno=keyboard.readLine(); //viene effettuata la lettura dall'utente
	  if(!CheckOperator(OpUno)) //viene controllato che il valore inserito non sia un operatore 	  
		  {  
	     try{
	    	 double a=(Double.valueOf(OpUno).doubleValue()); //viene convertito il valore letto da stringa a double
	     }
	     catch (Exception e) {
	 		System.out.println("errore input");
	 	    System.out.println("Caratteri inseriti non validi");
	 	 }	 	 
	  }
	}
      catch (Exception e) {
		System.out.println("errore input");
	}
	i++;
	Op.add(OpUno);//viene inserito il numero nell'array

	} 
	try { //vengono inviati i dati al server nel seguente ordine: primo numero, secondo numero, operatore. Tale ordine e' dovuto alla modalita' rpn
		outServer.println(Op.elementAt(0));
	    result=inputServer.readLine();
	    outServer.println(Op.elementAt(2));
	    result=inputServer.readLine();
	    outServer.println(Op.elementAt(1));
	    Op.clear();//viene svuotato l'array

	    result=inputServer.readLine(); //viene lettol il risultato fornito dal server
	    System.out.println("La risposta e': "+result);
	}
      catch (Exception e) {
		System.out.println("errore input");
	}	
}
/**
 * Metodo per l'invio dell'operazione con la richiesta 
 * di una calcolatrice rpn
 */
public void invioDatiRPN() {
	String OpUno="";
	InputStreamReader input = new InputStreamReader(System.in);
	keyboard = new BufferedReader(input);
	int i=0;
	
	System.out.print("> ");
	try {
	  OpUno=keyboard.readLine(); 
	  if(!CheckOperator(OpUno)) 
	  {  
	     try{
	    	 double a=(Double.valueOf(OpUno).doubleValue());
	     }
	     catch (Exception e) {
	 		System.out.println("errore input");
	 	    System.out.println("Caratteri inseriti non validi");
	 	 }	 	 
	  }
	}
      catch (Exception e) {
		System.out.println("errore input");
	}

	 
	outServer.println(OpUno); //dopo aver effettuato i vari controlli il valore viene inviato al server
	
	try {
	  result=inputServer.readLine();
	  if(!result.equalsIgnoreCase("ok"))
			  System.out.println(result);
	}
      catch (Exception e) {
		System.out.println("errore input");
	}	
}

}
