import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.io.*;

class RRC{
	public static void main(String[] args){
		if(args.length==2){
			Socket toS = null;
			InputStreamReader keyboard = new InputStreamReader(System.in);
			BufferedReader bf = new BufferedReader(keyboard);
			String read;
			boolean op = false;
			byte[] buff = new byte[100];
			int letti=0;
			try{
				toS = new Socket(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
				System.out.println("Connected to: " + toS.getInetAddress() + " / " + toS.getPort());
				InputStream in = toS.getInputStream();
				OutputStream out = toS.getOutputStream();
				
				infinite:
				while(true){
					//Lettura e attesa del check dell'operazione da parte del server
					while(op==false){
						System.out.print("Insert operation: ");
						read = bf.readLine();
						if(read.equals(".")){
							out.write(".".getBytes(), 0, ".".length());
							break infinite;
						}
						else{
							out.write(read.getBytes(), 0, read.length());
							letti = in.read(buff);
							if(letti<0){
								System.out.println("Connection lost");
								break infinite;
							}
							if((new String(buff, 0, letti)).equals("OK")){
								System.out.println("Response for operation: OK");
								op=true;
							}
							else{
								System.out.println("Response for operation: " + new String(buff, 0, letti));
							}
						}						
					}
					op=false;
					
					//Lettura e attesa del check del primo operando
					for(int i=0; i<2; i++){
						while(op==false){
							System.out.print("Insert operator " + i + ": ");
							read = bf.readLine();
							//Send operator to server
							out.write(read.getBytes(), 0, read.length());
							//Read response of server
							letti = in.read(buff);
							if(letti<0){
								System.out.println("Connection lost");
								break infinite;
							}
							if((new String(buff, 0, letti)).equals("OK")){
								System.out.println("Response for operator " + i + ": OK");
								op=true;
							}
							else{
								System.out.println("Response for opertor" + i + ": " + new String(buff, 0, letti));
							}				
						}
						//reset boolean per il prossimo numero
						op=false;
					}
					
					//Attesa risultato dell'operazione
					letti = in.read(buff);
					if(letti<0){
						System.out.println("Connection lost");
						break infinite;
					}
					System.out.println("Result: " + new String(buff, 0, letti));
				}
				
				
			}
			catch(UnknownHostException uhe){
				System.out.println("Server non trovato");
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try{toS.close();}
				catch(Exception e){e.printStackTrace();}
			}
		}
		else
			System.out.println("java RRC localhost #port");
	}
}
