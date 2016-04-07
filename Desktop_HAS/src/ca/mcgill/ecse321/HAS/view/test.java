package ca.mcgill.ecse321.HAS.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import java.awt.ScrollPane;
import javax.swing.JScrollBar;

public class test extends JFrame {

	private JPanel contentPane;
	private JTextField txtRoomName;
	private JTextField txtRoomGroupName;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;

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
		setBounds(100, 100, 520, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Control", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{28, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblMasterVolume = new JLabel("Master Volume");
		GridBagConstraints gbc_lblMasterVolume = new GridBagConstraints();
		gbc_lblMasterVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblMasterVolume.gridx = 0;
		gbc_lblMasterVolume.gridy = 1;
		panel_1.add(lblMasterVolume, gbc_lblMasterVolume);
		
		JSlider slider = new JSlider();
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.gridwidth = 2;
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 1;
		panel_1.add(slider, gbc_slider);
		
		JCheckBox chckbxMute = new JCheckBox("Mute");
		GridBagConstraints gbc_chckbxMute = new GridBagConstraints();
		gbc_chckbxMute.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxMute.gridx = 3;
		gbc_chckbxMute.gridy = 1;
		panel_1.add(chckbxMute, gbc_chckbxMute);
		
		JButton btnNewButton = new JButton("Stop all music");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblRoom = new JLabel("Location(s):");
		GridBagConstraints gbc_lblRoom = new GridBagConstraints();
		gbc_lblRoom.anchor = GridBagConstraints.EAST;
		gbc_lblRoom.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoom.gridx = 0;
		gbc_lblRoom.gridy = 4;
		panel_1.add(lblRoom, gbc_lblRoom);
		
		JComboBox comboBox_4 = new JComboBox();
		GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
		gbc_comboBox_4.gridwidth = 2;
		gbc_comboBox_4.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_4.gridx = 1;
		gbc_comboBox_4.gridy = 4;
		panel_1.add(comboBox_4, gbc_comboBox_4);
		
		JLabel lblVolume = new JLabel("Volume:");
		GridBagConstraints gbc_lblVolume = new GridBagConstraints();
		gbc_lblVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblVolume.gridx = 0;
		gbc_lblVolume.gridy = 5;
		panel_1.add(lblVolume, gbc_lblVolume);
		
		JSlider slider_1 = new JSlider();
		GridBagConstraints gbc_slider_1 = new GridBagConstraints();
		gbc_slider_1.gridwidth = 2;
		gbc_slider_1.insets = new Insets(0, 0, 5, 5);
		gbc_slider_1.gridx = 1;
		gbc_slider_1.gridy = 5;
		panel_1.add(slider_1, gbc_slider_1);
		
		JCheckBox chckbxMute_1 = new JCheckBox("Mute");
		GridBagConstraints gbc_chckbxMute_1 = new GridBagConstraints();
		gbc_chckbxMute_1.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxMute_1.gridx = 3;
		gbc_chckbxMute_1.gridy = 5;
		panel_1.add(chckbxMute_1, gbc_chckbxMute_1);
		
		JLabel lblNowPlaying = new JLabel("Now Playing");
		GridBagConstraints gbc_lblNowPlaying = new GridBagConstraints();
		gbc_lblNowPlaying.insets = new Insets(0, 0, 5, 5);
		gbc_lblNowPlaying.gridx = 0;
		gbc_lblNowPlaying.gridy = 6;
		panel_1.add(lblNowPlaying, gbc_lblNowPlaying);
		
		JButton btnPause_1 = new JButton("Pause");
		GridBagConstraints gbc_btnPause_1 = new GridBagConstraints();
		gbc_btnPause_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnPause_1.gridx = 1;
		gbc_btnPause_1.gridy = 7;
		panel_1.add(btnPause_1, gbc_btnPause_1);
		
		JButton btnPlay_2 = new JButton("Play");
		GridBagConstraints gbc_btnPlay_2 = new GridBagConstraints();
		gbc_btnPlay_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlay_2.gridx = 2;
		gbc_btnPlay_2.gridy = 7;
		panel_1.add(btnPlay_2, gbc_btnPlay_2);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("My Music", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		String [] columnNames= {"Artist", "Album", "Song"};
		Object[][] data = {{"Adele","21","Someone Like You"},{"The Killers", "Sam's Town","When We Were Young"}};
		table = new JTable( data, columnNames);
		table.setRowSelectionAllowed(false);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 2;
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		panel.add(table, gbc_table);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(new JScrollPane(table), gbc);
		
		
		
		JLabel lblLocation = new JLabel("Location:");
		GridBagConstraints gbc_lblLocation = new GridBagConstraints();
		gbc_lblLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocation.anchor = GridBagConstraints.EAST;
		gbc_lblLocation.gridx = 0;
		gbc_lblLocation.gridy = 1;
		panel.add(lblLocation, gbc_lblLocation);
		
		JComboBox comboBox_3 = new JComboBox();
		GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
		gbc_comboBox_3.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3.gridx = 1;
		gbc_comboBox_3.gridy = 1;
		panel.add(comboBox_3, gbc_comboBox_3);
		
		JButton btnPlay = new JButton("Play");
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlay.anchor = GridBagConstraints.EAST;
		gbc_btnPlay.gridx = 1;
		gbc_btnPlay.gridy = 2;
		panel.add(btnPlay, gbc_btnPlay);
		
		JPanel AddMusicPanel = new JPanel();
		tabbedPane.addTab("Add Music", null, AddMusicPanel, null);
		GridBagLayout gbl_AddMusicPanel = new GridBagLayout();
		gbl_AddMusicPanel.columnWidths = new int[]{0, 311, 0, 0};
		gbl_AddMusicPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_AddMusicPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_AddMusicPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		AddMusicPanel.setLayout(gbl_AddMusicPanel);
		
		JLabel lblArtistName = new JLabel("Artist Name:");
		GridBagConstraints gbc_lblArtistName = new GridBagConstraints();
		gbc_lblArtistName.insets = new Insets(0, 0, 5, 5);
		gbc_lblArtistName.anchor = GridBagConstraints.WEST;
		gbc_lblArtistName.gridx = 0;
		gbc_lblArtistName.gridy = 0;
		AddMusicPanel.add(lblArtistName, gbc_lblArtistName);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		AddMusicPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnAddArtist = new JButton("Add Artist");
		GridBagConstraints gbc_btnAddArtist = new GridBagConstraints();
		gbc_btnAddArtist.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddArtist.gridx = 2;
		gbc_btnAddArtist.gridy = 0;
		AddMusicPanel.add(btnAddArtist, gbc_btnAddArtist);
		
		JLabel lblAlbumName = new JLabel("Album name:");
		GridBagConstraints gbc_lblAlbumName = new GridBagConstraints();
		gbc_lblAlbumName.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumName.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumName.gridx = 0;
		gbc_lblAlbumName.gridy = 2;
		AddMusicPanel.add(lblAlbumName, gbc_lblAlbumName);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		AddMusicPanel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblAlbumGenre = new JLabel("Album Genre:");
		GridBagConstraints gbc_lblAlbumGenre = new GridBagConstraints();
		gbc_lblAlbumGenre.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumGenre.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumGenre.gridx = 0;
		gbc_lblAlbumGenre.gridy = 3;
		AddMusicPanel.add(lblAlbumGenre, gbc_lblAlbumGenre);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 3;
		AddMusicPanel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblReleaseDate = new JLabel("Release Date:");
		GridBagConstraints gbc_lblReleaseDate = new GridBagConstraints();
		gbc_lblReleaseDate.anchor = GridBagConstraints.WEST;
		gbc_lblReleaseDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblReleaseDate.gridx = 0;
		gbc_lblReleaseDate.gridy = 4;
		AddMusicPanel.add(lblReleaseDate, gbc_lblReleaseDate);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 4;
		AddMusicPanel.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JLabel lblAlbumArtist = new JLabel("Album Artist");
		GridBagConstraints gbc_lblAlbumArtist = new GridBagConstraints();
		gbc_lblAlbumArtist.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumArtist.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumArtist.gridx = 0;
		gbc_lblAlbumArtist.gridy = 5;
		AddMusicPanel.add(lblAlbumArtist, gbc_lblAlbumArtist);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 5;
		AddMusicPanel.add(comboBox, gbc_comboBox);
		
		JLabel lblFeaturingArtist = new JLabel("Featuring Artist");
		GridBagConstraints gbc_lblFeaturingArtist = new GridBagConstraints();
		gbc_lblFeaturingArtist.anchor = GridBagConstraints.WEST;
		gbc_lblFeaturingArtist.insets = new Insets(0, 0, 5, 5);
		gbc_lblFeaturingArtist.gridx = 0;
		gbc_lblFeaturingArtist.gridy = 6;
		AddMusicPanel.add(lblFeaturingArtist, gbc_lblFeaturingArtist);
		
		JComboBox comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 6;
		AddMusicPanel.add(comboBox_1, gbc_comboBox_1);
		
		JButton btnAddAlbum = new JButton("Add Album");
		GridBagConstraints gbc_btnAddAlbum = new GridBagConstraints();
		gbc_btnAddAlbum.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddAlbum.gridx = 2;
		gbc_btnAddAlbum.gridy = 6;
		AddMusicPanel.add(btnAddAlbum, gbc_btnAddAlbum);
		
		JLabel lblSongName = new JLabel("Song Name");
		GridBagConstraints gbc_lblSongName = new GridBagConstraints();
		gbc_lblSongName.anchor = GridBagConstraints.WEST;
		gbc_lblSongName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongName.gridx = 0;
		gbc_lblSongName.gridy = 8;
		AddMusicPanel.add(lblSongName, gbc_lblSongName);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 8;
		AddMusicPanel.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblSongPosition = new JLabel("Song Position");
		GridBagConstraints gbc_lblSongPosition = new GridBagConstraints();
		gbc_lblSongPosition.anchor = GridBagConstraints.WEST;
		gbc_lblSongPosition.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongPosition.gridx = 0;
		gbc_lblSongPosition.gridy = 9;
		AddMusicPanel.add(lblSongPosition, gbc_lblSongPosition);
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 9;
		AddMusicPanel.add(spinner, gbc_spinner);
		
		JLabel lblSongDuration = new JLabel("Song Duration");
		GridBagConstraints gbc_lblSongDuration = new GridBagConstraints();
		gbc_lblSongDuration.anchor = GridBagConstraints.WEST;
		gbc_lblSongDuration.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongDuration.gridx = 0;
		gbc_lblSongDuration.gridy = 10;
		AddMusicPanel.add(lblSongDuration, gbc_lblSongDuration);
		
		JSpinner spinner_1 = new JSpinner();
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = 10;
		AddMusicPanel.add(spinner_1, gbc_spinner_1);
		
		JLabel lblAlbum = new JLabel("Album");
		GridBagConstraints gbc_lblAlbum = new GridBagConstraints();
		gbc_lblAlbum.anchor = GridBagConstraints.WEST;
		gbc_lblAlbum.insets = new Insets(0, 0, 0, 5);
		gbc_lblAlbum.gridx = 0;
		gbc_lblAlbum.gridy = 11;
		AddMusicPanel.add(lblAlbum, gbc_lblAlbum);
		
		JComboBox comboBox_2 = new JComboBox();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 1;
		gbc_comboBox_2.gridy = 11;
		AddMusicPanel.add(comboBox_2, gbc_comboBox_2);
		
		JButton btnAddSong= new JButton("Add Song");
		GridBagConstraints gbc_btnAddSong = new GridBagConstraints();
		gbc_btnAddSong.gridx = 2;
		gbc_btnAddSong.gridy = 11;
		AddMusicPanel.add(btnAddSong, gbc_btnAddSong);
		
		JPanel RoomPanel = new JPanel();
		tabbedPane.addTab("Add Rooms", null, RoomPanel, null);
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
		
		JPanel Playlist = new JPanel();
		tabbedPane.addTab("Create Playlist", null, Playlist, null);
		GridBagLayout gbl_Playlist = new GridBagLayout();
		gbl_Playlist.columnWidths = new int[]{0, 0, 0, 0};
		gbl_Playlist.rowHeights = new int[]{0, 0, 0};
		gbl_Playlist.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_Playlist.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		Playlist.setLayout(gbl_Playlist);
		
		JLabel lblPlaylistName = new JLabel("Playlist Name:");
		GridBagConstraints gbc_lblPlaylistName = new GridBagConstraints();
		gbc_lblPlaylistName.anchor = GridBagConstraints.EAST;
		gbc_lblPlaylistName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlaylistName.gridx = 0;
		gbc_lblPlaylistName.gridy = 0;
		Playlist.add(lblPlaylistName, gbc_lblPlaylistName);
		
		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 0;
		Playlist.add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
		JLabel lblSelectSongs = new JLabel("Select Songs:");
		GridBagConstraints gbc_lblSelectSongs = new GridBagConstraints();
		gbc_lblSelectSongs.insets = new Insets(0, 0, 0, 5);
		gbc_lblSelectSongs.gridx = 0;
		gbc_lblSelectSongs.gridy = 1;
		Playlist.add(lblSelectSongs, gbc_lblSelectSongs);
		
		JList list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 1;
		Playlist.add(list, gbc_list);
		
		JButton btnCreate = new JButton("Create\nPlaylist");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.anchor = GridBagConstraints.SOUTH;
		gbc_btnCreate.gridx = 2;
		gbc_btnCreate.gridy = 1;
		Playlist.add(btnCreate, gbc_btnCreate);
	}
}
