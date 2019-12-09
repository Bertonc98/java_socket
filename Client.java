import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

class Client{
	public static void main(String arg[]){
		Socket sClient = new Socket();
		
		try{
			InetAddress ia = InetAddress.getLocalHost();
			InetSocketAddress isa = new InetSocketAddress(ia, 58913); //0 fa scegleire all'OS
			//sClient.bind(isa);
			sClient.connect(isa);
			System.out.println("Indirizzo: " + sClient.getInetAddress() + "\nPorta allocata: " + sClient.getLocalPort());
			Thread.sleep(1000);
			}
		catch(Exception e){
			e.printStackTrace();
			}
	}
	}
