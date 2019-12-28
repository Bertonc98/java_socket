import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.*;
import java.io.*;

public class ClientHandler extends Thread{
	
	private Socket s2C;
	
	public ClientHandler(Socket toC){
		this.s2C = toC;
	}
	
	public void run(){
		int dim = 100;
		byte[] buff = new byte[dim];
		int letti;
		String[] words = new String[2];
		double result;
		String res;
		
		while(true){
			try{
				InputStream fromCl = s2C.getInputStream();
				OutputStream toCl = s2C.getOutputStream();
				letti = fromCl.read(buff);
				//Mettiamo che passino due double per fare una somma, divisi da un +
				if(letti>0){
					String curr = new String(buff, 0, letti);
					System.out.println("Received: " + curr);
					words = curr.split("\\+",0);
					res = "result:".concat(String.valueOf(Double.parseDouble(words[0]) + Double.parseDouble(words[1])));
					toCl.write(res.getBytes(), 0, res.length());
				}
				else{
					System.out.println("Closing connection with: " + s2C.getPort());
					s2C.close();
					return;
				}
				
			}
			catch(Exception e){
				try{
					System.out.println("Client expired :" + s2C.getPort());
					s2C.close();
					return;}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
			
		}
	
	}
}
