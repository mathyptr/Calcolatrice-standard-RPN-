import java.io.*;
import java.net.*;

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
public Client(String serverName,int serverPort,char rpn) {
	try {
		this.rpn=rpn;
		operatori=new java.util.HashMap<String, String>();
		Op= new java.util.Vector<String>();
		operatori.put("+","+");
		operatori.put("-","-");
		operatori.put("/","/");
		operatori.put("*","*");
		clientSocket= new Socket (serverName, serverPort);
		inputServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outServer = new PrintStream(clientSocket.getOutputStream(), true);
	    System.out.println("Connessione stabilita");	    	    
	}
	catch(IOException e) {
		System.out.println("Host non riconosciuto");
	}
	
	
}
public boolean CheckOperator( String OpUno) {
	return operatori.containsKey(OpUno);
}
public void invioDati() {
	if(rpn=='y')
		invioDatiRPN();
	else
		invioDatiNoRPN();
}	
public void invioDatiNoRPN() {
	String OpUno="";
	InputStreamReader input = new InputStreamReader(System.in);
	keyboard = new BufferedReader(input);
	int i=0;
	
	while(i<3) {
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
	i++;
	Op.add(OpUno);

	} 
	try {
		outServer.println(Op.elementAt(0));
	    result=inputServer.readLine();
	    outServer.println(Op.elementAt(2));
	    result=inputServer.readLine();
	    outServer.println(Op.elementAt(1));
	    Op.clear();

	    result=inputServer.readLine();
	    System.out.println("La risposta e': "+result);
	}
      catch (Exception e) {
		System.out.println("errore input");
	}	
}

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

	 
	outServer.println(OpUno);
	
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
