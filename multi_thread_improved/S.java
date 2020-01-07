import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

class S{
	public static void main(String[] args){
		ServerSocket passive = null;
		Socket toC = null;
		Thread t;
		try{
			passive = new ServerSocket(0);
			System.out.println("Server open on port: " + passive.getLocalPort());
			
			while(true){
				t = new H(passive.accept());
				t.start();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{passive.close();
				System.out.println("Server closed");}
			catch(Exception e){e.printStackTrace();}
		}
	}
} 
