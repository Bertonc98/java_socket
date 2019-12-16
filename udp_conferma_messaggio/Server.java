import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Server{
	public static void main(String[] arg){
		InputStreamReader keyboard = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader(keyboard);
		try{
			//Crea la socket in autonomia e stampa indirizzo e porta scelti
			DatagramSocket sServer = new DatagramSocket();
			System.out.println("Server address: " + sServer.getLocalAddress() + "\nServer port: " + sServer.getLocalPort());
			
			//Crea il buffer in cui mettere il messaggio ricevuto
			int dim = 100;
			byte[] buffer = new byte[dim];
			
			String received;
			while(true){
				DatagramPacket dpIn = new DatagramPacket(buffer, 0, dim);
				DatagramPacket echo;
				//Si mette in attesa di un messaggio e lo salva nel pacchetto dpIn, di conseguenza nel suo buffer
				sServer.receive(dpIn);
				
				//Una volta ricevuto crea una stringa con ciò che è stato inserito nel buffer
				received = new String(buffer, 0, dpIn.getLength());
				System.out.println("Message received: " + received);
				
				//creazione messaggio di echo
				echo = new DatagramPacket(buffer, 0, dpIn.getLength(), 
										  InetAddress.getByName(dpIn.getAddress().getHostName()), 
										  dpIn.getPort());
				//invio echo
				sServer.send(echo);
				System.out.println("Address: " + dpIn.getAddress().getHostName() + "\nPort: " + dpIn.getPort());
				
			}
			//System.out.println("Server shutdown...");
			//sServer.close();
			}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
