/**
 * 
 */
package edu.lamar.client;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.lamar.common.MessageImpl;
import edu.lamar.common.irp.Message;
import edu.lamar.common.irp.MessageTypes;

/**
 * @author user
 *
 */
public class CarClient extends AbstractClient {
	boolean hadIrequestedTheBridge = false;
	int timeStamp = 0;
	Queue<Integer> queue = new LinkedBlockingQueue<Integer>();
	public CarClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		Message myMessage = ((Message)msg);
		if(myMessage.getMessageType().equals(MessageTypes.BridgeRequest)){
			if(hadIrequestedTheBridge){
				if(timeStamp > myMessage.getTimeStamp()){
					timeStamp = myMessage.getTimeStamp()+1;
					try {
						sendToServer(new MessageImpl(0, timeStamp, MessageTypes.Acknowledge));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else{
				timeStamp = myMessage.getTimeStamp()+1;
				try {
					sendToServer(new MessageImpl(0, timeStamp, MessageTypes.Acknowledge));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else if(myMessage.getMessageType().equals(MessageTypes.BridgRelease)){
			
		}else{
			// Acknowledge
		}
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
