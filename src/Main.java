import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sun.net.httpserver.HttpServer;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Server server = new Server();
		server.startServer();
		
		connect("tom1");
		connect("2");
		connect("tom1");
		
	}

	/**
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void connect(String name) throws UnknownHostException, IOException {
		Socket testConnection = new Socket("localhost", 5001);
		OutputStream os = testConnection.getOutputStream();
		InputStream is = testConnection.getInputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
		bw.write(name+'\n');
		bw.flush();
		System.out.println("Send username");
	}
	
	
	
}
