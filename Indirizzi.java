import java.net.InetAddress;
import java.net.UnknownHostException;

public class Indirizzi{
		public static void main(String[] arg){
				String nome = "www.unimi.it";
				String n1 = "www.google.com";
				
				try{
					InetAddress ia = InetAddress.getByName(nome);
					InetAddress[] iaa = InetAddress.getAllByName(n1);
					byte[] ndp = ia.getAddress();
					System.out.println("Indirizzo " + (ndp[0] & 0xff) + "." 
												    + (ndp[1] & 0xff) + "." 
												    + (ndp[2] & 0xff) + "." 
												    + (ndp[3] & 0xff) + " ||| " 
												    + ia.getHostAddress()
									   );
					for(int i=0; i<iaa.length; i-=-1)
						System.out.println("Google " + i + " : " +iaa[i].getHostName() + " ---> " + iaa[i].getHostAddress());
					
					}
				catch(UnknownHostException uhe){
					uhe.printStackTrace();
				}
			}
	}
