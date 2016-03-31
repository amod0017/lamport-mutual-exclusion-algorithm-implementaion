/**
 * 
 */
package edu.lamar.client;

import java.io.IOException;

/**
 * @author user
 *
 */
public class CarClient extends AbstractClient {

	public CarClient(String host, int port) {
		super(host, port);
	}

	/* (non-Javadoc)
	 * @see edu.lamar.client.AbstractClient#handleMessageFromServer(java.lang.Object)
	 */
	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println(msg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CarClient myClient = new CarClient("localhost", 5555);
			myClient.openConnection();
			myClient.sendToServer("HI");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
