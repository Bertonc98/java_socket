import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.io.*;

class Client{
	public static void main(String[] args){
		InputStreamReader t = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader(t);
		double a;
		double b;
		String cur;
		byte[] buff = new byte[100];
		try{
			Socket toS = new Socket(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
			do{
				System.out.print("Insert first double: ");
				a=Double.parseDouble(bf.readLine());
				
				System.out.print("Insert second double: ");
				b=Double.parseDouble(bf.readLine());
				
				InputStream in = toS.getInputStream();
				OutputStream out = toS.getOutputStream();
				cur = String.valueOf(a).concat("+".concat(String.valueOf(b)));
				out.write(cur.getBytes(), 0, cur.length());
				
				int letti = in.read(buff);
				if(letti<0){
					System.out.println("Server expired");
					break;
				}
				System.out.println("Result: " + new String(buff, 0, letti));
			}while(true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
