import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

class Client{
	public static void main(String[] arg){
		//Se ha i giusti parametri
		if(arg.length == 2){
			DatagramSocket sClient;
			DatagramPacket dp;
			
			//Lettura da tastiera
			InputStreamReader keyboard = new InputStreamReader(System.in);
			BufferedReader bf = new BufferedReader(keyboard);
			
			//Salva i parametri
			String server_name = arg[0];
			int server_port = Integer.parseInt(arg[1]);
			String in;
			byte[] buffer;
			
			try{
				sClient = new DatagramSocket();
				do{
					System.out.print("Message: ");
					in = bf.readLine();
					buffer = in.getBytes();
					
					dp = new DatagramPacket(buffer, 0, buffer.length, InetAddress.getByName(server_name), server_port);
					sClient.send(dp);
					sClient.receive(dp);
					System.out.println("Received: " + new String(buffer, 0, dp.getLength()));
				}while(!in.equals("end"));
				
				System.out.println("Closing cliend");
				sClient.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else
			System.out.println("Improper argument");
	}
}
