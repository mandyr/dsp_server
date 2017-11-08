package dsp_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainApplication {

	public static int CHECK_EOF_REACHED = 1;
	public static int CHECK_FOLDER_CONTENT_CHANGED = 2;
	private static int port = 8000;
	
	private ArrayList<Client> clients = null;
	
	private static Socket socket;
	private static DataInputStream in;
	private static DataOutputStream out;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket mediaServer = new ServerSocket(port);
			socket = mediaServer.accept();
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			System.out.println(in.readInt());
			out.writeInt(200);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}
