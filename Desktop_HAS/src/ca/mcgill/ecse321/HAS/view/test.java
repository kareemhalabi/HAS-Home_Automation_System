package ca.mcgill.ecse321.HAS.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.Component;

public class test extends JFrame {

	private JPanel contentPane;
	private JTextField txtRoomName;
	private JTextField txtRoomGroupName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("My Music", null, panel, null);
		
		JPanel RoomPanel = new JPanel();
		tabbedPane.addTab("Rooms", null, RoomPanel, null);
		GridBagLayout gbl_RoomPanel = new GridBagLayout();
		gbl_RoomPanel.columnWidths = new int[] {0, 0, 0};
		gbl_RoomPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_RoomPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_RoomPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		RoomPanel.setLayout(gbl_RoomPanel);
		
		txtRoomName = new JTextField();
		txtRoomName.setText("Room name");
		GridBagConstraints gbc_txtRoomName = new GridBagConstraints();
		gbc_txtRoomName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRoomName.insets = new Insets(0, 0, 5, 5);
		gbc_txtRoomName.gridx = 0;
		gbc_txtRoomName.gridy = 0;
		RoomPanel.add(txtRoomName, gbc_txtRoomName);
		txtRoomName.setColumns(10);
		
		JButton btnCreateRoom = new JButton("Create Room");
		GridBagConstraints gbc_btnCreateRoom = new GridBagConstraints();
		gbc_btnCreateRoom.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreateRoom.gridx = 1;
		gbc_btnCreateRoom.gridy = 0;
		RoomPanel.add(btnCreateRoom, gbc_btnCreateRoom);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		RoomPanel.add(verticalStrut, gbc_verticalStrut);
		
		txtRoomGroupName = new JTextField();
		txtRoomGroupName.setText("Room Group Name");
		GridBagConstraints gbc_txtRoomGroupName = new GridBagConstraints();
		gbc_txtRoomGroupName.insets = new Insets(0, 0, 5, 5);
		gbc_txtRoomGroupName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRoomGroupName.gridx = 0;
		gbc_txtRoomGroupName.gridy = 2;
		RoomPanel.add(txtRoomGroupName, gbc_txtRoomGroupName);
		txtRoomGroupName.setColumns(10);
		
		JList RoomList = new JList();
		GridBagConstraints gbc_RoomList = new GridBagConstraints();
		gbc_RoomList.insets = new Insets(0, 0, 0, 5);
		gbc_RoomList.fill = GridBagConstraints.BOTH;
		gbc_RoomList.gridx = 0;
		gbc_RoomList.gridy = 3;
		RoomPanel.add(RoomList, gbc_RoomList);
		
		
		JButton btnCreateRoomGroup = new JButton("Create Room Group");
		GridBagConstraints gbc_btnCreateRoomGroup = new GridBagConstraints();
		gbc_btnCreateRoomGroup.anchor = GridBagConstraints.SOUTH;
		gbc_btnCreateRoomGroup.gridx = 1;
		gbc_btnCreateRoomGroup.gridy = 3;
		RoomPanel.add(btnCreateRoomGroup, gbc_btnCreateRoomGroup);
		
		JPanel AddMusicPanel = new JPanel();
		tabbedPane.addTab("Add Music", null, AddMusicPanel, null);
		GridBagLayout gbl_AddMusicPanel = new GridBagLayout();
		gbl_AddMusicPanel.columnWidths = new int[]{0};
		gbl_AddMusicPanel.rowHeights = new int[]{0};
		gbl_AddMusicPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_AddMusicPanel.rowWeights = new double[]{Double.MIN_VALUE};
		AddMusicPanel.setLayout(gbl_AddMusicPanel);
	}
}
