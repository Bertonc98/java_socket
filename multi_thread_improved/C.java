import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.io.*;

class C{
	public static void main(String[] args){
		if(args.length==2){
			Socket toS = null;
			byte[] buff = new byte[100];
			int letti=0;
			String[] ack; 
			String read;
			InputStream in;
			OutputStream out;
			try{
				InputStreamReader tastiera = new InputStreamReader(System.in);
				BufferedReader bf = new BufferedReader(tastiera);
				toS = new Socket(args[0], Integer.parseInt(args[1]));
				System.out.println("Socket opened on port " + toS.getLocalPort());
				in=toS.getInputStream();
				out=toS.getOutputStream();
				
				while(true){
					read = bf.readLine();
					out.write(read.getBytes(), 0, read.length());
					letti = in.read(buff);
					if(letti<0)
						break;
					ack = (new String(buff, 0, letti)).split("@", 0);
					if(!ack[0].equals("OK"))
						break;
					System.out.println("Response: " + ack[0] + " " + ack[1]);
					
					if(read.equals("."))
						break;
				}
				
			}
			catch(UnknownHostException uhe){
				System.out.println("Unable to reach server");
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try{toS.close();
					System.out.println("Connection closed");}
				catch(Exception e){e.printStackTrace();}
			}
		}
		else
			System.out.println("java C ip #port");
	}
}
