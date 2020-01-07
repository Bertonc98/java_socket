import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.io.*;

class H extends Thread{
	
	private Socket toC;
	
	public H(Socket tmpToC){
		toC = tmpToC;
		System.out.println("Connected to client: " + toC.getInetAddress() + " " + toC.getPort());
	}
	
	public void run(){
		byte[] buff = new byte[100];
		String read;
		boolean exit = false;
		int letti;
		try{
			InputStream in = toC.getInputStream();
			OutputStream out = toC.getOutputStream();
			while(true){
				letti = in.read(buff);
				if(letti<0)
					break;
				read = new String(buff, 0, letti);
				System.out.println("("+toC.getLocalPort()+")Received " + read + " from " + toC.getPort());
				exit = read.equals(".") ? true : false;
				read = "OK@".concat(read);
				out.write(read.getBytes(), 0, read.length());
				if(exit)
					break;
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{toC.close(); 
				System.out.println("Connection to " + toC.getInetAddress() + 
												" " + toC.getPort() + " closed");
				return;}
			catch(Exception e){e.printStackTrace();}
		}
	}
}
