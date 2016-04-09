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
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import java.awt.ScrollPane;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.HAS.model.Song;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class HASPage extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel control;

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTable table;

	// data elements Control page
	private JLabel ctlErrMsg;
	
	private Integer selectedRoom = -1;
	private Integer selectedRoomGroup = -1;
	private HashMap<Integer, RoomGroup> roomGroups;

	// room page
	private JLabel roomErrMsg;
	private JTextField txtRoomName;
	private JList roomList;
	private HashMap<Integer, String> roomsString;
	private HashMap<Integer,Room> roomMap;
	private Integer selectedRoom_RP=-1;
	private List<Room> roomSelectionlist;
	private JTextField txtRoomGroupName;
	private String error_RP = "";
	
	//playlist page
	private String error_PP="";
	private JTextField txtPlaylistName;
	private List<Song> songSelectionlist;
	private HashMap<Integer, String> songsString;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HASPage frame = new HASPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @return
	 */
	public HASPage() {

		initComponents();
		refreshData();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		control = new JPanel();
		tabbedPane.addTab("Control", null, control, null);
		GridBagLayout gbl_control = new GridBagLayout();
		gbl_control.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_control.rowHeights = new int[] { 28, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_control.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_control.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		control.setLayout(gbl_control);

		ctlErrMsg = new JLabel("");
		GridBagConstraints gbc_ctlErrorMsg = new GridBagConstraints();
		gbc_ctlErrorMsg.gridwidth = 4;
		gbc_ctlErrorMsg.insets = new Insets(0, 0, 5, 5);
		gbc_ctlErrorMsg.gridx = 0;
		gbc_ctlErrorMsg.gridy = 0;
		control.add(ctlErrMsg, gbc_ctlErrorMsg);

		JLabel lblMasterVolume = new JLabel("Master Volume");
		GridBagConstraints gbc_lblMasterVolume = new GridBagConstraints();
		gbc_lblMasterVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblMasterVolume.gridx = 0;
		gbc_lblMasterVolume.gridy = 1;
		control.add(lblMasterVolume, gbc_lblMasterVolume);

		JSlider sliderMasterVolume = new JSlider();
		GridBagConstraints gbc_sliderMasterVolume = new GridBagConstraints();
		gbc_sliderMasterVolume.gridwidth = 2;
		gbc_sliderMasterVolume.insets = new Insets(0, 0, 5, 5);
		gbc_sliderMasterVolume.gridx = 1;
		gbc_sliderMasterVolume.gridy = 1;
		control.add(sliderMasterVolume, gbc_sliderMasterVolume);

		JCheckBox chckbxMute = new JCheckBox("Mute");
		GridBagConstraints gbc_chckbxMute = new GridBagConstraints();
		gbc_chckbxMute.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxMute.gridx = 3;
		gbc_chckbxMute.gridy = 1;
		control.add(chckbxMute, gbc_chckbxMute);

		JButton btnStopAll = new JButton("Stop all music");
		GridBagConstraints gbc_btnStopAll = new GridBagConstraints();
		gbc_btnStopAll.gridwidth = 2;
		gbc_btnStopAll.insets = new Insets(0, 0, 5, 5);
		gbc_btnStopAll.gridx = 1;
		gbc_btnStopAll.gridy = 2;
		control.add(btnStopAll, gbc_btnStopAll);

		JLabel lblRoom = new JLabel("Location(s):");
		GridBagConstraints gbc_lblRoom = new GridBagConstraints();
		gbc_lblRoom.anchor = GridBagConstraints.EAST;
		gbc_lblRoom.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoom.gridx = 0;
		gbc_lblRoom.gridy = 4;
		control.add(lblRoom, gbc_lblRoom);

		JComboBox comboBox_4 = new JComboBox();
		GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
		gbc_comboBox_4.gridwidth = 2;
		gbc_comboBox_4.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_4.gridx = 1;
		gbc_comboBox_4.gridy = 4;
		control.add(comboBox_4, gbc_comboBox_4);

		JLabel lblVolume = new JLabel("Volume:");
		GridBagConstraints gbc_lblVolume = new GridBagConstraints();
		gbc_lblVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblVolume.gridx = 0;
		gbc_lblVolume.gridy = 5;
		control.add(lblVolume, gbc_lblVolume);

		JSlider sliderVolume = new JSlider();
		GridBagConstraints gbc_sliderVolume = new GridBagConstraints();
		gbc_sliderVolume.gridwidth = 2;
		gbc_sliderVolume.insets = new Insets(0, 0, 5, 5);
		gbc_sliderVolume.gridx = 1;
		gbc_sliderVolume.gridy = 5;
		control.add(sliderVolume, gbc_sliderVolume);

		JCheckBox chckbxMute_1 = new JCheckBox("Mute");
		GridBagConstraints gbc_chckbxMute_1 = new GridBagConstraints();
		gbc_chckbxMute_1.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxMute_1.gridx = 3;
		gbc_chckbxMute_1.gridy = 5;
		control.add(chckbxMute_1, gbc_chckbxMute_1);

		JLabel lblNowPlaying = new JLabel("Now Playing");
		GridBagConstraints gbc_lblNowPlaying = new GridBagConstraints();
		gbc_lblNowPlaying.insets = new Insets(0, 0, 5, 5);
		gbc_lblNowPlaying.gridx = 0;
		gbc_lblNowPlaying.gridy = 6;
		control.add(lblNowPlaying, gbc_lblNowPlaying);

		JButton btnPause = new JButton("Pause");
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.insets = new Insets(0, 0, 5, 5);
		gbc_btnPause.gridx = 1;
		gbc_btnPause.gridy = 7;
		control.add(btnPause, gbc_btnPause);

		JButton btnPlay_2 = new JButton("Play");
		btnPlay_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// playButtonActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnPlay_2 = new GridBagConstraints();
		gbc_btnPlay_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlay_2.gridx = 2;
		gbc_btnPlay_2.gridy = 7;
		control.add(btnPlay_2, gbc_btnPlay_2);

		JPanel myMusic = new JPanel();
		tabbedPane.addTab("My Music", null, myMusic, null);
		GridBagLayout gbl_myMusic = new GridBagLayout();
		gbl_myMusic.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_myMusic.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_myMusic.columnWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_myMusic.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		myMusic.setLayout(gbl_myMusic);

		String[] columnNames = { "Artist", "Album", "Song" };
		Object[][] data = { { "Adele", "21", "Someone Like You" },
				{ "The Killers", "Sam's Town", "When We Were Young" } };
		table = new JTable(data, columnNames);
		table.setRowSelectionAllowed(false);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 2;
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		myMusic.add(table, gbc_table);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.BOTH;
		myMusic.add(new JScrollPane(table), gbc);

		JLabel lblLocation = new JLabel("Location:");
		GridBagConstraints gbc_lblLocation = new GridBagConstraints();
		gbc_lblLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocation.anchor = GridBagConstraints.EAST;
		gbc_lblLocation.gridx = 0;
		gbc_lblLocation.gridy = 1;
		myMusic.add(lblLocation, gbc_lblLocation);

		JComboBox comboBoxlocation = new JComboBox();
		GridBagConstraints gbc_comboBoxlocation = new GridBagConstraints();
		gbc_comboBoxlocation.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxlocation.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxlocation.gridx = 1;
		gbc_comboBoxlocation.gridy = 1;
		myMusic.add(comboBoxlocation, gbc_comboBoxlocation);

		JButton btnPlay = new JButton("Play");
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlay.anchor = GridBagConstraints.EAST;
		gbc_btnPlay.gridx = 1;
		gbc_btnPlay.gridy = 2;
		myMusic.add(btnPlay, gbc_btnPlay);

		JPanel AddMusicPanel = new JPanel();
		tabbedPane.addTab("Add Music", null, AddMusicPanel, null);
		GridBagLayout gbl_AddMusicPanel = new GridBagLayout();
		gbl_AddMusicPanel.columnWidths = new int[] { 0, 311, 0, 0 };
		gbl_AddMusicPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_AddMusicPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_AddMusicPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		AddMusicPanel.setLayout(gbl_AddMusicPanel);

		JLabel errorMessage = new JLabel("");
		GridBagConstraints gbc_errorMessage = new GridBagConstraints();
		gbc_errorMessage.gridwidth = 2;
		gbc_errorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_errorMessage.gridx = 0;
		gbc_errorMessage.gridy = 0;
		AddMusicPanel.add(errorMessage, gbc_errorMessage);

		JLabel lblArtistName = new JLabel("Artist Name:");
		GridBagConstraints gbc_lblArtistName = new GridBagConstraints();
		gbc_lblArtistName.insets = new Insets(0, 0, 5, 5);
		gbc_lblArtistName.anchor = GridBagConstraints.WEST;
		gbc_lblArtistName.gridx = 0;
		gbc_lblArtistName.gridy = 1;
		AddMusicPanel.add(lblArtistName, gbc_lblArtistName);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		AddMusicPanel.add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnAddArtist = new JButton("Add Artist");
		GridBagConstraints gbc_btnAddArtist = new GridBagConstraints();
		gbc_btnAddArtist.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddArtist.gridx = 2;
		gbc_btnAddArtist.gridy = 1;
		AddMusicPanel.add(btnAddArtist, gbc_btnAddArtist);

		JLabel lblAlbumName = new JLabel("Album Name:");
		GridBagConstraints gbc_lblAlbumName = new GridBagConstraints();
		gbc_lblAlbumName.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumName.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumName.gridx = 0;
		gbc_lblAlbumName.gridy = 3;
		AddMusicPanel.add(lblAlbumName, gbc_lblAlbumName);

		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 3;
		AddMusicPanel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		JLabel lblAlbumGenre = new JLabel("Album Genre:");
		GridBagConstraints gbc_lblAlbumGenre = new GridBagConstraints();
		gbc_lblAlbumGenre.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumGenre.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumGenre.gridx = 0;
		gbc_lblAlbumGenre.gridy = 4;
		AddMusicPanel.add(lblAlbumGenre, gbc_lblAlbumGenre);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 4;
		AddMusicPanel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		JLabel lblReleaseDate = new JLabel("Release Date:");
		GridBagConstraints gbc_lblReleaseDate = new GridBagConstraints();
		gbc_lblReleaseDate.anchor = GridBagConstraints.WEST;
		gbc_lblReleaseDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblReleaseDate.gridx = 0;
		gbc_lblReleaseDate.gridy = 5;
		AddMusicPanel.add(lblReleaseDate, gbc_lblReleaseDate);

		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 5;
		AddMusicPanel.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);

		JLabel lblAlbumArtist = new JLabel("Album Artist");
		GridBagConstraints gbc_lblAlbumArtist = new GridBagConstraints();
		gbc_lblAlbumArtist.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumArtist.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumArtist.gridx = 0;
		gbc_lblAlbumArtist.gridy = 6;
		AddMusicPanel.add(lblAlbumArtist, gbc_lblAlbumArtist);

		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 6;
		AddMusicPanel.add(comboBox, gbc_comboBox);

		JLabel lblFeaturingArtist = new JLabel("Featuring Artist");
		GridBagConstraints gbc_lblFeaturingArtist = new GridBagConstraints();
		gbc_lblFeaturingArtist.anchor = GridBagConstraints.WEST;
		gbc_lblFeaturingArtist.insets = new Insets(0, 0, 5, 5);
		gbc_lblFeaturingArtist.gridx = 0;
		gbc_lblFeaturingArtist.gridy = 7;
		AddMusicPanel.add(lblFeaturingArtist, gbc_lblFeaturingArtist);

		JList list_1 = new JList();
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.insets = new Insets(0, 0, 5, 5);
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridx = 1;
		gbc_list_1.gridy = 7;
		AddMusicPanel.add(list_1, gbc_list_1);

		JButton btnAddAlbum = new JButton("Add Album");
		GridBagConstraints gbc_btnAddAlbum = new GridBagConstraints();
		gbc_btnAddAlbum.anchor = GridBagConstraints.SOUTH;
		gbc_btnAddAlbum.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddAlbum.gridx = 2;
		gbc_btnAddAlbum.gridy = 7;
		AddMusicPanel.add(btnAddAlbum, gbc_btnAddAlbum);

		JLabel lblSongName = new JLabel("Song Name");
		GridBagConstraints gbc_lblSongName = new GridBagConstraints();
		gbc_lblSongName.anchor = GridBagConstraints.WEST;
		gbc_lblSongName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongName.gridx = 0;
		gbc_lblSongName.gridy = 9;
		AddMusicPanel.add(lblSongName, gbc_lblSongName);

		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 9;
		AddMusicPanel.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);

		JLabel lblSongPosition = new JLabel("Song Position");
		GridBagConstraints gbc_lblSongPosition = new GridBagConstraints();
		gbc_lblSongPosition.anchor = GridBagConstraints.WEST;
		gbc_lblSongPosition.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongPosition.gridx = 0;
		gbc_lblSongPosition.gridy = 10;
		AddMusicPanel.add(lblSongPosition, gbc_lblSongPosition);

		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 10;
		AddMusicPanel.add(spinner, gbc_spinner);

		JLabel lblSongDuration = new JLabel("Song Duration");
		GridBagConstraints gbc_lblSongDuration = new GridBagConstraints();
		gbc_lblSongDuration.anchor = GridBagConstraints.WEST;
		gbc_lblSongDuration.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongDuration.gridx = 0;
		gbc_lblSongDuration.gridy = 11;
		AddMusicPanel.add(lblSongDuration, gbc_lblSongDuration);

		JSpinner spinner_1 = new JSpinner();
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = 11;
		AddMusicPanel.add(spinner_1, gbc_spinner_1);

		JLabel lblAlbum = new JLabel("Album");
		GridBagConstraints gbc_lblAlbum = new GridBagConstraints();
		gbc_lblAlbum.anchor = GridBagConstraints.WEST;
		gbc_lblAlbum.insets = new Insets(0, 0, 0, 5);
		gbc_lblAlbum.gridx = 0;
		gbc_lblAlbum.gridy = 12;
		AddMusicPanel.add(lblAlbum, gbc_lblAlbum);

		JComboBox comboBox_2 = new JComboBox();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 1;
		gbc_comboBox_2.gridy = 12;
		AddMusicPanel.add(comboBox_2, gbc_comboBox_2);

		JButton btnAddSong = new JButton("Add Song");
		GridBagConstraints gbc_btnAddSong = new GridBagConstraints();
		gbc_btnAddSong.gridx = 2;
		gbc_btnAddSong.gridy = 12;
		AddMusicPanel.add(btnAddSong, gbc_btnAddSong);

		JPanel RoomPanel = new JPanel();
		tabbedPane.addTab("Add Rooms", null, RoomPanel, null);
		GridBagLayout gbl_RoomPanel = new GridBagLayout();
		gbl_RoomPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_RoomPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_RoomPanel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_RoomPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		RoomPanel.setLayout(gbl_RoomPanel);
		
		roomErrMsg = new JLabel("");
		GridBagConstraints gbc_lblRoomerrmsg = new GridBagConstraints();
		gbc_lblRoomerrmsg.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomerrmsg.gridx = 0;
		gbc_lblRoomerrmsg.gridy = 0;
		RoomPanel.add(roomErrMsg, gbc_lblRoomerrmsg);

		txtRoomName = new JTextField();
		txtRoomName.setText("Room name");
		GridBagConstraints gbc_txtRoomName = new GridBagConstraints();
		gbc_txtRoomName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRoomName.insets = new Insets(0, 0, 5, 5);
		gbc_txtRoomName.gridx = 0;
		gbc_txtRoomName.gridy = 2;
		RoomPanel.add(txtRoomName, gbc_txtRoomName);
		txtRoomName.setColumns(10);

		JButton btnCreateRoom = new JButton("Create Room");
		btnCreateRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createRoomActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnCreateRoom = new GridBagConstraints();
		gbc_btnCreateRoom.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreateRoom.gridx = 1;
		gbc_btnCreateRoom.gridy = 2;
		RoomPanel.add(btnCreateRoom, gbc_btnCreateRoom);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 3;
		RoomPanel.add(verticalStrut, gbc_verticalStrut);

		roomList = new JList();
		roomList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				roomListSelectionListener(e);
			}
		});
		
		txtRoomGroupName = new JTextField();
		txtRoomGroupName.setText("Room Group Name");
		GridBagConstraints gbc_txtRoomGroupName = new GridBagConstraints();
		gbc_txtRoomGroupName.insets = new Insets(0, 0, 5, 5);
		gbc_txtRoomGroupName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRoomGroupName.gridx = 0;
		gbc_txtRoomGroupName.gridy = 4;
		RoomPanel.add(txtRoomGroupName, gbc_txtRoomGroupName);
		txtRoomGroupName.setColumns(10);
		GridBagConstraints gbc_roomList = new GridBagConstraints();
		gbc_roomList.insets = new Insets(0, 0, 0, 5);
		gbc_roomList.fill = GridBagConstraints.BOTH;
		gbc_roomList.gridx = 0;
		gbc_roomList.gridy = 5;
		RoomPanel.add(roomList, gbc_roomList);

		JButton btnCreateRoomGroup = new JButton("Create Room Group");
		btnCreateRoomGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createRoomGroupActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnCreateRoomGroup = new GridBagConstraints();
		gbc_btnCreateRoomGroup.anchor = GridBagConstraints.SOUTH;
		gbc_btnCreateRoomGroup.gridx = 1;
		gbc_btnCreateRoomGroup.gridy = 5;
		RoomPanel.add(btnCreateRoomGroup, gbc_btnCreateRoomGroup);

		JPanel Playlist = new JPanel();
		tabbedPane.addTab("Create Playlist", null, Playlist, null);
		GridBagLayout gbl_Playlist = new GridBagLayout();
		gbl_Playlist.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_Playlist.rowHeights = new int[] { 0, 0, 0 };
		gbl_Playlist.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_Playlist.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		Playlist.setLayout(gbl_Playlist);

		JLabel lblPlaylistName = new JLabel("Playlist Name:");
		GridBagConstraints gbc_lblPlaylistName = new GridBagConstraints();
		gbc_lblPlaylistName.anchor = GridBagConstraints.EAST;
		gbc_lblPlaylistName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlaylistName.gridx = 0;
		gbc_lblPlaylistName.gridy = 0;
		Playlist.add(lblPlaylistName, gbc_lblPlaylistName);
		
		txtPlaylistName = new JTextField();
		txtPlaylistName.setText("Playlist Name");
		GridBagConstraints gbc_txtPlaylistName = new GridBagConstraints();
		gbc_txtPlaylistName.insets = new Insets(0, 0, 5, 5);
		gbc_txtPlaylistName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPlaylistName.gridx = 1;
		gbc_txtPlaylistName.gridy = 0;
		Playlist.add(txtPlaylistName, gbc_txtPlaylistName);
		txtPlaylistName.setColumns(10);

		JLabel lblSelectSongs = new JLabel("Select Songs:");
		GridBagConstraints gbc_lblSelectSongs = new GridBagConstraints();
		gbc_lblSelectSongs.insets = new Insets(0, 0, 0, 5);
		gbc_lblSelectSongs.gridx = 0;
		gbc_lblSelectSongs.gridy = 1;
		Playlist.add(lblSelectSongs, gbc_lblSelectSongs);

		JList songsList = new JList();
		songsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				songListSelectionListener(e);
			}
		});
		GridBagConstraints gbc_songsList = new GridBagConstraints();
		gbc_songsList.insets = new Insets(0, 0, 0, 5);
		gbc_songsList.fill = GridBagConstraints.BOTH;
		gbc_songsList.gridx = 1;
		gbc_songsList.gridy = 1;
		Playlist.add(songsList, gbc_songsList);

		JButton btnCreatePlaylist = new JButton("Create\nPlaylist");
		btnCreatePlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPlaylistActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnCreatePlaylist = new GridBagConstraints();
		gbc_btnCreatePlaylist.anchor = GridBagConstraints.SOUTH;
		gbc_btnCreatePlaylist.gridx = 2;
		gbc_btnCreatePlaylist.gridy = 1;
		Playlist.add(btnCreatePlaylist, gbc_btnCreatePlaylist);
	}

	private void refreshData()
	{
		HAS h = HAS.getInstance();

		// error
		//ctlErrMsg.setText(error);
		
		roomErrMsg.setText(error_RP);
	

	if(error_RP==null||error_RP.length()==0)

	{
		roomsString = new HashMap<Integer, String>();
		Iterator<Room> roomIt = h.getRooms().iterator();
		Integer index = 0;
		while (roomIt.hasNext()) {
			Room r = roomIt.next();		
			roomsString.put(index, r.getName());
			index++;
		}
		
		txtRoomName.setText("");
		
		if(!roomsString.isEmpty()){
		roomList.setListData(roomsString.values().toArray());
		roomList.clearSelection();
		txtRoomGroupName.setText("");
		
		}
	}
	
	if(error_PP==null||error_PP.length()==0)

	{
		songsString = new HashMap<Integer, String>();
		Iterator<Song> songIt = h.getSongs().iterator();
		Integer index = 0;
		while ( songIt.hasNext()) {
			Song s = songIt.next();		
			songsString.put(index, s.getName());
			index++;
		}
		
		txtRoomName.setText("");
		
		if(!roomsString.isEmpty()){
		roomList.setListData(roomsString.values().toArray());
		roomList.clearSelection();
		txtRoomGroupName.setText("");
		
		}
	}

	}

	private void createRoomActionPerformed(java.awt.event.ActionEvent evt){
		
		HASController hc = new HASController();
		
			try{
				hc.createRoom(txtRoomName.getText());
			}
			catch (InvalidInputException e){
				error_RP = e.getMessage();
			}
		
	refreshData();	
	}
	
	private void roomListSelectionListener(ListSelectionEvent e){
		HAS h = HAS.getInstance();
		
		boolean adjust = e.getValueIsAdjusting();
		if (!adjust) {
	          JList list = (JList) e.getSource();
	          int roomSelections[] = list.getSelectedIndices();
	        //  Object selectionValues[] = list.getSelectedValues();
	          roomSelectionlist = new ArrayList<Room>();
	          for (int i=0; i<roomSelections.length;i++){
	        	  roomSelectionlist.add(i, h.getRoom(roomSelections[i]));
	        
	          }
		}
	}
	
	private void songListSelectionListener(ListSelectionEvent e){
		HAS h = HAS.getInstance();
		
		boolean adjust = e.getValueIsAdjusting();
		if (!adjust) {
	          JList list = (JList) e.getSource();
	          int songSelections[] = list.getSelectedIndices();
	        //  Object selectionValues[] = list.getSelectedValues();
	          songSelectionlist = new ArrayList<Song>();
	          for (int i=0; i<songSelections.length;i++){
	        	  songSelectionlist.add(i, h.getSong(songSelections[i]));
	        
	          }
		}
	}
	

	
	private void createRoomGroupActionPerformed(java.awt.event.ActionEvent evt){
		HASController hc = new HASController();
		try{
			hc.createRoomGroup(txtRoomGroupName.getText(), roomSelectionlist);
		}
	
		catch (InvalidInputException e){
			error_PP=e.getMessage();
		}
		refreshData();
	}
	
	private void createPlaylistActionPerformed(java.awt.event.ActionEvent evt){
		HASController hc = new HASController();
		try{
			hc.createPlaylist(txtPlaylistName.getText(), songSelectionlist);
		}
		catch (InvalidInputException e){
			error_PP=e.getMessage();
		}
		refreshData(); 
	}
	

	/*
	 * private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
	 * error = ""; if (selectedRoom < 0) error = error + "Must Select a room! ";
	 * 
	 * // TODO change the ranges of values for songDurationSpinner and //
	 * songPositionSpinner
	 * 
	 * error = error.trim(); if (error.length() == 0) { HASController hc = new
	 * HASController(); try { /* hc.addSongtoAlbum(albums.get(selectedAlbum),
	 * songNameTextField.getText(), (Integer)
	 * (songDurationSpinner.getModel().getValue()), (Integer)
	 * (songPositionSpinner.getModel().getValue()), null); } catch
	 * (InvalidInputException e) { error = e.getMessage(); } }
	 * 
	 * refreshData(); }
	 * 
	 */
}
