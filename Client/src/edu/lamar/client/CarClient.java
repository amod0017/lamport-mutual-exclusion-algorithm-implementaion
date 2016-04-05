/**
 * 
 */
package edu.lamar.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;
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
	int myCarId;
	Map<Integer, String> carAcknowledgementStatusMap = new HashMap<>();

	public CarClient(int carId, String host, int port) {
		super(host, port);
		myCarId = carId;
		carAcknowledgementStatusMap.put(1, "NCK");
		carAcknowledgementStatusMap.put(2, "NCK");
		carAcknowledgementStatusMap.put(3, "NCK");
		carAcknowledgementStatusMap.put(4, "NCK");
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		Message myMessage = ((Message) msg);
		if (myMessage.getMessageType().equals(MessageTypes.BridgeRequest)) {
			if (hadIrequestedTheBridge) {
				if (timeStamp > myMessage.getTimeStamp()) {
					timeStamp = myMessage.getTimeStamp() + 1;
					try {
						queue.add(myMessage.getCarId());
						sendToServer(new MessageImpl(myCarId, timeStamp, MessageTypes.Acknowledge));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				timeStamp = myMessage.getTimeStamp() + 1;
				queue.add(myMessage.getCarId());
				try {
					sendToServer(new MessageImpl(myCarId, timeStamp, MessageTypes.Acknowledge));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (myMessage.getMessageType().equals(MessageTypes.BridgRelease)) {
			// remove from queue
			// should be the top car
			queue.remove();
		} else {
			// Acknowledge
			if (hadIrequestedTheBridge) {
				carAcknowledgementStatusMap.put(myMessage.getCarId(), "ACK");
			}
			if (getUpdatedAckStatus(carAcknowledgementStatusMap)) {
				try {
					System.out.println("I am on bridge: " + myCarId);
					sendToServer(new MessageImpl(myCarId, myMessage.getTimeStamp() + 1, MessageTypes.OnBridge));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean getUpdatedAckStatus(Map<Integer, String> carAcknowledgementStatusMap2) {
		for (Entry<Integer, String> entry : carAcknowledgementStatusMap2.entrySet()) {
			if (entry.getValue().equals("NCK")) {
				return false;
			}
		}
		return true;
	}

	private int getCurrentTimeStamp() {
		timeStamp = timeStamp + 1;
		return timeStamp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(new InputStreamReader(System.in));
			System.out.println("Enter Car Id:");
			int carId = scanner.nextInt();
			CarClient myClient = new CarClient(carId, "localhost", 5555);
			myClient.openConnection();
			System.out.println("Press 1 for bridge request & 2 for bridge release");
			int option = scanner.nextInt();
			if (option == 2) {
				if (!myClient.hadIrequestedTheBridge)
					System.out.println("Please request the bridge first");
				else
					myClient.sendToServer(
							new MessageImpl(carId, myClient.getCurrentTimeStamp(), MessageTypes.BridgRelease));
			} else if(option == 1){
				myClient.sendToServer(new MessageImpl(carId, myClient.getCurrentTimeStamp(), MessageTypes.BridgeRequest));
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
