import java.io.*;
import java.net.*;
public class Server {
	ServerSocket server;
	Socket clientSocket;
	BufferedReader inputClient;
	PrintStream outClient=null;	
	String clientData;
	String result;
	int numOp=0;
	public Server(int port) {
		try {
			server= new ServerSocket(port);
			clientSocket= server.accept();
			server.close();
			inputClient= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outClient = new PrintStream(clientSocket.getOutputStream(), true);
			
			} 
		catch (IOException e) {
			System.out.println("errore I/O");
		}
	}
	public boolean CheckData(String OpUno) {
	     try{
	    	 double a=(Double.valueOf(OpUno).doubleValue());
	     }
	     catch (Exception e) {
	 		return false;
	     }	 		
		return true;
	}
	
	
	public double exec(String op1, String op2, char operator) {
		double a,b,res=0;;
		a=(Double.valueOf(op1).doubleValue());
		b=(Double.valueOf(op2).doubleValue());
		switch(operator) {
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
		return res;
	}
	
	public void Calc() {
		System.out.println(".....in attesa di dati.");
		java.util.Stack formula= new java.util.Stack ();
		String op1,op2;
		double res=0;
		while(true)
		{
			try{
				clientData= inputClient.readLine();
				if (CheckData(clientData)) {
					formula.push(clientData);	
					outClient.println("OK");
				}
				else
				{
					op2=(String)formula.pop();
					op1=(String)formula.pop();
					res=exec(op1,op2,clientData.charAt(0));
					outClient.println("La risposta e': "+Double.toString(res));
					formula.push(Double.toString(res));
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
