import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.*;
import java.io.*;

class RR_tcpC{
	public static void main(String[] arg){
		if(arg.length==2){
			
			InputStreamReader keyboard = new InputStreamReader(System.in);
			BufferedReader bf = new BufferedReader(keyboard);
			
			try{
				InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(arg[0]), Integer.parseInt(arg[1]));
				Socket toS = new Socket();
				toS.connect(isa);
				OutputStream out = toS.getOutputStream();
				String read;
				while(true){
					System.out.print("Insert message: ");
					read = bf.readLine();
					out.write(read.getBytes(), 0, read.length());
					if(read.equals("@"))
						break;
				}
				toS.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
		else
			System.out.println("java RR_tcpC Ip Port");
	}
}
