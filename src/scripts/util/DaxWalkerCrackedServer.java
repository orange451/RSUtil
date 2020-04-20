package scripts.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import javax.net.ssl.HttpsURLConnection;

import scripts.dax_api.api_lib.utils.IOHelper;

public class DaxWalkerCrackedServer {

	public static final int PORT = 35567;
	private static ServerSocket server;

	public static void main(String[] args) {
		try {
			server = new ServerSocket(PORT);
			server.setSoTimeout(1000);
			System.out.println("Server started on: " + server.getLocalPort());
			
			while(true) {

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				final Socket incoming;
				try {
					incoming = server.accept();
					incoming.setKeepAlive(true);

					new Thread(new Runnable() {
						public void run() {
							try {
								
								while(true) {
									if ( incoming.isClosed() )
										break;

									// Read the message from tribot
							        String contents = IOHelper.readInputStreamWithoutClosing(incoming.getInputStream());
									System.out.println("["+new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()) + "] Incoming request: " + contents);
							        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

							        // Setup connection to expltv server
							        URL myurl = new URL("https://osrs-map.herokuapp.com/getPath");
							        HttpsURLConnection expltvServerCon = (HttpsURLConnection) myurl.openConnection();
							        expltvServerCon.setDoOutput(true);
							        expltvServerCon.setDoInput(true);
							        expltvServerCon.setRequestProperty("Method", "POST");
							        expltvServerCon.setRequestProperty("Content-Type", "application/json");
							        expltvServerCon.setRequestProperty("Origin", "https://explv.github.io");
							        
							        // Forward data from tribot to expltv
							        IOHelper.writeOutputStream(expltvServerCon.getOutputStream(), contents);
							        
							        // Read expltv server path data back
							        String returnContents = IOHelper.readInputStream(expltvServerCon.getInputStream());
							        
							        // Send expltv data back to tribot
							        IOHelper.writeOutputStream(incoming.getOutputStream(), returnContents);

						        	// Dont burn CPU
									Thread.sleep(50);
								}
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			System.out.println("Error making server... " + e1);
			e1.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
