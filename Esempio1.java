import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

//Codice client per sevizio orientato alla connessione (TCP)

class Esempio1{
	public static void main(String arg[]){
		Socket sClient;
		InetAddress ia; //Indirizzo ip del client
		InetSocketAddress isa; //socket address del client <IP, port>
		
		sClient= new Socket();
		
		try{
			ia=InetAddress.getLocalHost();
			isa=new InetSocketAddress(ia, 50000);
			
			System.out.println(isa.toString());
			}
		catch(UnknownHostException uhe){
			uhe.printStackTrace();
			}
		}
	}
