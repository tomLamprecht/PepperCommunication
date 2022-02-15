import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpServer;

public class Server {
	private final int PORT = 5001;

	private ServerSocket ss;
	private Map<String, Socket> sockets;

	public boolean startServer() {
		try {
			ss = new ServerSocket(PORT);
			sockets = new HashMap<>();
			connect();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private void connect() {
		Thread connectionStarter = new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						// System.out.println("Waiting for Connection...");
						Socket socket;
						socket = ss.accept();
						// System.out.println("Prepare Connection to " + socket);
						prepareConnection(socket);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		connectionStarter.start();
	}

	private void prepareConnection(Socket socket) {
		Thread prep = new Thread() {
			@Override
			public void run() {
				String username;
				try (InputStream is = socket.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
					username = br.readLine();
					addSocketToMap(socket, username);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};
		prep.start();
	}

	private synchronized void addSocketToMap(Socket socket, String username) throws IOException {
		if (sockets.get(username) == null) {
			sockets.put(username, socket);
			System.out.println("Sucessfull Connection to " + username);
		} else {
			System.err.println("Client tried to connect with a already existing username... (" + username + ")");
			System.err.println("Shutting down the connection");
			socket.close();
		}
	}

	public Socket getSocket(String username) {
		return sockets.get(username);
	}

	public int getPORT() {
		return PORT;
	}

}
