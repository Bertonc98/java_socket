import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.*;
import java.io.*;

class Server{
	public static void main(String[] args){
		ServerSocket ss;
		Socket toC;
		try{
			ss = new ServerSocket(0);
			System.out.println("Server started\nAddress/port : " + ss.getInetAddress() + " / " + ss.getLocalPort());
			
			while(true){
				toC = ss.accept();
				System.out.println("Open connection with " + toC.getInetAddress() + " / " + toC.getPort());
				Thread t = new ClientHandler(toC);
				t.start();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{ss.close();}
			catch(Exception e){e.printStackTrace()}
		}
	}
}
