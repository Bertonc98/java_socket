import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

class ServerS{
	public static void main(String arg[]){
		ServerSocket ss;
		Socket toClient;
		
		try{
			//Istanzio e bindo la server socket ad una porta scelta dal S.O.
			ss = new ServerSocket(0);
			//Stampa 0.0.0.0 perchè va in attesa su tutte le schede di rete, una volta accettato lo definisce
			System.out.println("Server socket allocata\nIndirizzo: " + ss.getInetAddress() + "\nPorta: " + ss.getLocalPort());
			
			toClient = ss.accept(); //In attesa di connessione da parte di un client
			System.out.println("Indirizzo client: " + toClient.getInetAddress() + "\nPorta: " + toClient.getPort());
			
			Thread.sleep(60*10);
			System.out.println("Ciao");
			}
		catch(Exception e){
			System.out.println(e.toString());
			}
		}
	}
