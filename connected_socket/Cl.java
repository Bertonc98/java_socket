import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;

class Cl{
	public static void main(String[] arg){
		Socket toSrv = new Socket();
		byte[] buffer = new byte[100];
		int letti;
		String message;
		
		try{
			//Inizializzazione socket con l'indirizzo locale e porta well known
			InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(arg[0]), Integer.parseInt(arg[1]));
			System.out.println(InetAddress.getByName(arg[0]) + " " + Integer.parseInt(arg[1]));
			toSrv.connect(isa);
			System.out.println("Client connected to server(Ip, Port): " + toSrv.getInetAddress() + "; " + toSrv.getPort());
			
			//Impostazione canale di invio al server
			OutputStream out = toSrv.getOutputStream();
			
			//Impostazione canale di ricezione messaggi dal server
			InputStream in = toSrv.getInputStream();
			
			//Impostazione buffer per lettura da tastiera
			InputStreamReader keyboard = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(keyboard);
			System.out.print("Insert message:");
			
			while(!((message=br.readLine()).equals("0"))){
				//Send message to server
				out.write(message.getBytes(), 0, message.length());
				System.out.println("Message sended");
				
				//Ciclo di attesa della notifica del server
				letti=in.read(buffer);
				if(letti<=0 || !(new String(buffer, 0, letti).equals("next")))
					break;
				
				System.out.print("Insert message:");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			}
		//All'uscita chiude la socket
		finally{
			try{
				toSrv.close();
				System.out.println("Connection closed");
				}
			catch(Exception e){
				e.printStackTrace();
				}
			}
		}
	}
