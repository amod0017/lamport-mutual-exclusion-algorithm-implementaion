package edu.lamar.client;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyFrame2 extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final JTextField carId;
	private final JLabel bridgeUpperEdge;
	private final JLabel onBridgeCar;
	private final JLabel bridgeLowerEdge;
	private final JButton btnBridgeRequest;
	private final JButton btnBridgeRelease;
	private final JButton btnConnect;
	private String myCarId;
	private CarClient3 myCarClient;
	private final JLabel lblEnterDirection;
	private final JTextField carDirection;
	private String myCarDirection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final MyFrame2 frame = new MyFrame2();
					frame.setVisible(true);
					frame.btnConnect.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// frame.btnBridgeRelease.setEnabled(true);
							frame.btnBridgeRequest.setEnabled(true);
							frame.myCarId = frame.carId.getText();
							frame.myCarDirection = frame.carDirection.getText();
							frame.myCarClient = new CarClient3(frame, Integer.parseInt(frame.myCarId),
									frame.myCarDirection, "localhost", 5555);
							try {
								frame.myCarClient.openConnection();
							} catch (final IOException e1) {
								e1.printStackTrace();
							}
						}
					});
					// create car client and connect with server

					frame.btnBridgeRequest.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							frame.btnBridgeRequest.setEnabled(false);
							frame.btnBridgeRelease.setEnabled(true);
							frame.myCarClient.callBridgeRequest(frame.myCarClient);
						}
					});
					frame.btnBridgeRelease.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							frame.btnBridgeRelease.setEnabled(false);
							frame.btnBridgeRequest.setEnabled(true);
							frame.myCarClient.callBridgeRelease(frame.myCarClient);
						}
					});
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		final GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		final JLabel lblEnterCarId = new JLabel("Enter Car Id");
		final GridBagConstraints gbc_lblEnterCarId = new GridBagConstraints();
		gbc_lblEnterCarId.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterCarId.gridx = 1;
		gbc_lblEnterCarId.gridy = 1;
		contentPane.add(lblEnterCarId, gbc_lblEnterCarId);

		carId = new JTextField();
		final GridBagConstraints gbc_carId = new GridBagConstraints();
		gbc_carId.anchor = GridBagConstraints.WEST;
		gbc_carId.insets = new Insets(0, 0, 5, 5);
		gbc_carId.gridx = 2;
		gbc_carId.gridy = 1;
		contentPane.add(carId, gbc_carId);
		carId.setColumns(10);

		btnConnect = new JButton("connect");
		final GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 5, 0);
		gbc_btnConnect.gridx = 3;
		gbc_btnConnect.gridy = 1;
		contentPane.add(btnConnect, gbc_btnConnect);

		lblEnterDirection = new JLabel("Enter Direction");
		final GridBagConstraints gbc_lblEnterDirection = new GridBagConstraints();
		gbc_lblEnterDirection.anchor = GridBagConstraints.EAST;
		gbc_lblEnterDirection.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterDirection.gridx = 1;
		gbc_lblEnterDirection.gridy = 2;
		contentPane.add(lblEnterDirection, gbc_lblEnterDirection);

		carDirection = new JTextField();
		final GridBagConstraints gbc_carDirection = new GridBagConstraints();
		gbc_carDirection.insets = new Insets(0, 0, 5, 5);
		gbc_carDirection.fill = GridBagConstraints.HORIZONTAL;
		gbc_carDirection.gridx = 2;
		gbc_carDirection.gridy = 2;
		contentPane.add(carDirection, gbc_carDirection);
		carDirection.setColumns(10);

		bridgeUpperEdge = new JLabel("--------------------------------");
		final GridBagConstraints gbc_bridgeUpperEdge = new GridBagConstraints();
		gbc_bridgeUpperEdge.insets = new Insets(0, 0, 5, 5);
		gbc_bridgeUpperEdge.gridx = 2;
		gbc_bridgeUpperEdge.gridy = 3;
		contentPane.add(bridgeUpperEdge, gbc_bridgeUpperEdge);

		onBridgeCar = new JLabel("C");
		final GridBagConstraints gbc_onBridgeCar = new GridBagConstraints();
		gbc_onBridgeCar.insets = new Insets(0, 0, 5, 5);
		gbc_onBridgeCar.gridx = 2;
		gbc_onBridgeCar.gridy = 4;
		contentPane.add(onBridgeCar, gbc_onBridgeCar);

		bridgeLowerEdge = new JLabel("--------------------------------");
		final GridBagConstraints gbc_bridgeLowerEdge = new GridBagConstraints();
		gbc_bridgeLowerEdge.insets = new Insets(0, 0, 5, 5);
		gbc_bridgeLowerEdge.gridx = 2;
		gbc_bridgeLowerEdge.gridy = 5;
		contentPane.add(bridgeLowerEdge, gbc_bridgeLowerEdge);

		btnBridgeRequest = new JButton("Bridge Request");
		btnBridgeRequest.setEnabled(false);
		final GridBagConstraints gbc_btnBridgeRequest = new GridBagConstraints();
		gbc_btnBridgeRequest.insets = new Insets(0, 0, 0, 5);
		gbc_btnBridgeRequest.gridx = 1;
		gbc_btnBridgeRequest.gridy = 9;
		contentPane.add(btnBridgeRequest, gbc_btnBridgeRequest);

		btnBridgeRelease = new JButton("Bridge Release");
		btnBridgeRelease.setEnabled(false);
		final GridBagConstraints gbc_btnBridgeRelease = new GridBagConstraints();
		gbc_btnBridgeRelease.gridx = 3;
		gbc_btnBridgeRelease.gridy = 9;
		contentPane.add(btnBridgeRelease, gbc_btnBridgeRelease);

	}

	public void updateOnBridgeLabel(String onBridge) {
		onBridgeCar.setText(onBridge);
		this.repaint();
	}

}
