import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.net.*;
import java.io.*;
import java.util.*;

class RRS{
	private static final int MAX = 4;
	private static final int timeout = 3000; //3 secondi
	public static void main(String[] args){
		ArrayList<Socket> client = new ArrayList<Socket>();
		boolean created = false, op = false;
		int connected =0, i = 0, nums = 0, letti;
		byte[] buff = new byte[100];
		String operation = null;
		double op1, op2, res;
		InputStream in;
		OutputStream out;
		Socket curr;
		//Creazione ServerSocket
		ServerSocket passive = null;
		try{passive = new ServerSocket(0); created=true;}
		catch(Exception e){e.printStackTrace(); System.out.println("Error in ServerSocket creation");}
		
		if(created==true){
			System.out.println("Server: " + passive.getInetAddress() + " / " + passive.getLocalPort());
			while(true){
				//Gestione accettazione dei client
				try{
					passive.setSoTimeout(timeout);
					while(connected<MAX){
						client.add(passive.accept());
						System.out.println("Connected to: " +
										   client.get(connected).getInetAddress() + " / " +
										   client.get(connected).getPort());
						connected++;
					}
				}
				catch(SocketTimeoutException ste){System.out.println("Timeout expired, start handling request of conencted clients");}
				catch(Exception e){e.printStackTrace();}
				
				//Gestione richieste client
				client:
				while(connected>0){
					try{
						//Reset variabili di controllo
						op=false;
						nums=0;
						op1=1.0;
						op2=1.0;
						//Istanzio le variabili di dialogo con al socket corrente
						curr=client.get(i);
						System.out.println("Handling: " + curr.getInetAddress() + " / " + curr.getPort());
						in = curr.getInputStream();
						out= curr.getOutputStream();
						 
						curr.setSoTimeout(timeout);
						
						//finchè non narriva una operazione riconosciuta
						do{
							letti = in.read(buff);
							if(letti<0 || (new String(buff, 0, letti)).equals(".")){
								//Chiudo la connessione, setto il nuovo indice e passo al ciclo dopo, 
								//se non si è arrivato all'ultimo elemento della lista dei client
								curr.close();
								client.remove(i);
								connected--;	
								System.out.println("--Connection closed");
								i = connected==0 ? 0 : (i+1)%connected;
								if(letti>0)
									System.out.println("---Received '.'");
								if(i==(client.size()-1))
									break client;	
								continue client;				
							}							
							String read = new String(buff, 0, letti);
							System.out.println("Received: " + read);
							//Se riconosce l'operazione esce dal ciclo e chiede gli operandi
							if(checkop(read)){
								out.write("OK".getBytes(), 0, "OK".length());
								operation = read;
								op=true;
							}
							else
								out.write("WRONG".getBytes(), 0, "WRONG".length());
						}while(op == false);
						
						//Ciclo di richiesta operandi
						System.out.println("Operator");
						curr.setSoTimeout(0);
						do{
							letti = in.read(buff);
							if(letti<0){
								//Chiudo la connessione, setto il nuovo indice e passo al ciclo dopo, 
								//se non si è arrivato all'ultimo elemento della lista dei client
								curr.close();
								client.remove(i);
								connected--;	
								i = connected==0 ? 0 : (i+1)%connected;
								if(i==(client.size()-1))
									break client;	
								continue client;				
							}	
							try{
								if(nums==0)
									op1=Double.parseDouble(new String(buff, 0, letti));
								else
									op2=Double.parseDouble(new String(buff, 0, letti));
								//richiedo se cerca di dividere per 0
								if(operation.equals("/") && nums==1 && op2==0.0)
									op2=Double.parseDouble("error");
								nums++;
								out.write("OK".getBytes(), 0, "OK".length());
							}
							catch(NumberFormatException nfe){
								out.write("WRONG".getBytes(), 0, "WRONG".length());
							}
						}while(nums!=2);
						
						//calcolo del risultato ed invio
						res = operation(operation, op1, op2);
						out.write(String.valueOf(res).getBytes(), 0, String.valueOf(res).length());
						
					}
					catch(SocketTimeoutException ste){System.out.println("Time expired");}
					catch(Exception e){
						e.printStackTrace();
						try{
							client.get(i).close();
							client.remove(i);
							connected--;
						}
						catch(IOException ioe){ioe.printStackTrace();}
					}
					//se è sull'ultimo elemento esce dal ciclo di handling dei client e vede se accettare connesioni
					//altrimenti, se la coda non è vuota continua
					i= connected==0 ? 0 : (i+1)%connected;
					if(i==(client.size()-1))
						break client;
				}
				
			}
		}
	}
	
	private static boolean checkop(String s){
		return (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"));
	}
	
	private static double operation(String op, double op1, double op2){
		double res = 1.0;
		switch(op){
			case "+": res=op1+op2; break;
			case "-": res=op1-op2; break;
			case "*": res=op1*op2; break;
			case "/": res=op1/op2; break;
		}
		return res;
	}
}
