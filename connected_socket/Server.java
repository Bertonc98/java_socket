import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;

class Server{
	public static void main(String[] arg){
		ServerSocket ss;
		Socket toClient;
		int dim = 100;
		byte[] buffer = new byte[dim];
		String next = "next";
		
		try{
			ss = new ServerSocket(50000);
			System.out.println("Server started on <Ip;Port> =" + InetAddress.getLocalHost() + " ; " + "50000" );
			String result="";
			while(true){				
				toClient = ss.accept();
				System.out.println("Client connecton established <ClientIp;ClientPort> =" + toClient.getInetAddress() + " ; " + toClient.getPort() );
				
				//impostazione canale di lettura messaggi dal client
				InputStream in = toClient.getInputStream();
				int letti;
				
				//Impostazione canale invio al client
				OutputStream out = toClient.getOutputStream();
				
				//Ciclo per leggere frase per frase
				while((letti=in.read(buffer))>=0){
					result = new String(buffer, 0, letti);
					System.out.println("Message: " + result);
					
					//Notifica al client 
					out.write(next.getBytes(), 0, next.length());				
					}
					
				System.out.println("Start shutdown connection");	
				toClient.close();
				if(result.equals("exit"))
					break;
			}
			ss.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
