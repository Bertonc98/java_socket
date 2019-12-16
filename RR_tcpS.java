import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.*;
import java.net.*;
import java.io.*;

class RR_tcpS{
	public static void main(String[] arg){
		ServerSocket ss;
		ArrayList<Socket> client = new ArrayList<Socket>();
		Socket curr;
		byte[] buff = new byte[100];
		int connected = 0;
		int max = 3;
		int wait = 5000;
		int i=0;
		try{
			//Creazione socket della socket server
			ss = new ServerSocket(0);
			System.out.println("Ip: " + ss.getInetAddress() + " Port : " + ss.getLocalPort());
			
			while(true){
				//Try catch per la parte di accettazione delle connessioni
				try{
					//setto il timeout per le accept
					ss.setSoTimeout(wait);
					//Finchè ci sono connessioni (entro 5 secondi) le accetta e le aggiunge all'arraylist
					//Se meno del numero massimo specificato
					while(connected<max){
						client.add(ss.accept());
						//connected++;
						System.out.println("Added: " + client.get(connected++).getPort());
						
					}
				}
				catch(SocketTimeoutException ste){
					System.out.println("Timeout accept for client");
				}
				catch(IOException ioe){
					ioe.printStackTrace();
				}
				
				//Uscito dalla parte di accettazione gestisce le richieste
				while(connected>0){
					//Stampo i dati della connessione corrente
					curr = client.get(i); 
					System.out.println("Address: " + curr.getInetAddress() + "/" + curr.getPort());
				
					try{
						//Timeout per la read
						curr.setSoTimeout(wait);
						InputStream in = curr.getInputStream();
						while(true){
							int letti = in.read(buff);
							//Se il canale cade
							if(letti<0){
								curr.close();
								client.remove(i);
								System.out.println("Removed client " + curr.getPort());
								connected--;
								break;
							}
							String read = (new String(buff, 0, letti)).trim();
							
							//Carattere di fine, chiusura socket client, rimozione da lista, diminuzione contatore
							if(read.equals("@")){
								curr.close();
								client.remove(i);
								System.out.println("Removed client " + curr.getPort());
								connected--;
								break;
							}
							System.out.println("Received: " + read);
							
						}
					}
					catch(SocketTimeoutException ste){
						System.out.println("Client time expired");
					}
					//Altri problemi generici vengono catturati e si cerca comunque di rimuovere e chiudere la socket corrente
					catch(Exception e){
						e.printStackTrace();
						try{
							curr.close();
							client.remove(i);
							System.out.println("Removed client " + curr.getPort());
							connected--;
						}
						catch(IOException ioe){
							ioe.printStackTrace();
						}
					}
					//Assegno il successivo (se c'è) a i
					i= connected!=0 ? (i+1)%connected:0;
				}
				//Fine gestione client
			}
			//chiusura del server
			//ss.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
