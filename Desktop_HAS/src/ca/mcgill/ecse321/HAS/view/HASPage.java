package ca.mcgill.ecse321.HAS.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

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
import ca.mcgill.ecse321.HAS.persistence.PersistenceHAS;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class HASPage extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel control;

	private JTable table;

	// data elements Control page
	private String error_CP = "";
	private JLabel ctlErrMsg;
	private Integer selectedRoom = -1;
	private Integer selectedRoomGroup = -1;
	private HashMap<Integer, RoomGroup> roomGroups;
	private Integer mVolume = 50;
	private Integer rVolume = 50;
	private JComboBox location2CB;
	private Integer selected2location = -1;
	private HashMap<Integer, String> location2Map;

	// room page
	private JLabel roomErrMsg;
	private JTextField txtRoomName;
	private JList roomList;
	private HashMap<Integer, String> roomsString;
	private HashMap<Integer, String> roomGroupString;
	private HashMap<Integer, String> locationMap;
	private Integer selectedRoom_RP = -1;
	private List<Room> roomSelectionlist;
	private JTextField txtRoomGroupName;
	private String error_RP = "";

	// playlist page
	private String error_PP = "";
	private JTextField txtPlaylistName;
	private List<Song> songSelectionlist;
	private HashMap<Integer, String> songsString;
	private JLabel playlistErrMsg;
	private JList songList;

	// add music page
	private String error_AP = "";
	private JLabel addMusicErrMsg;
	private JTextField txtArtistName;
	private JTextField txtAlbumName;
	private JTextField txtAlbumGenre;
	private JTextField txtSongName;

	private Integer selectedArtist = -1;
	private HashMap<Integer, Artist> artists;
	private Integer selectedAlbum = -1;
	private HashMap<Integer, Album> albums;

	NumberFormat amountFormat = NumberFormat.getNumberInstance();
	private JSpinner songDurSpin;
	private JSpinner songPosSpin;

	private JDatePickerImpl albumReleaseDatePicker;

	private HashMap<Integer, String> fArtistString;
	private List<Artist> fArtistSelectionList;
	private JList featuringArtistList;

	private JComboBox albumArtistCB;
	private JComboBox albumCB;

	// my music
	private Integer row = -1;
	private Integer column = -1;
	private String error_MP = "";
	private JComboBox locationCB;
	private Integer selectedLocation = -1;
	private Vector data = new Vector();
	private Vector columnNames = new Vector();
	private DefaultTableModel tableModel;

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

		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

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
		sliderMasterVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mVolume = sliderMasterVolume.getValue();
				// mVolumenChanged(e);
			}
		});

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

		location2CB = new JComboBox();
		location2CB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				selected2location = cb.getSelectedIndex();
			}
		});

		GridBagConstraints gbc_location2CB = new GridBagConstraints();
		gbc_location2CB.gridwidth = 2;
		gbc_location2CB.insets = new Insets(0, 0, 5, 5);
		gbc_location2CB.fill = GridBagConstraints.HORIZONTAL;
		gbc_location2CB.gridx = 1;
		gbc_location2CB.gridy = 4;
		control.add(location2CB, gbc_location2CB);

		JLabel lblVolume = new JLabel("Volume:");
		GridBagConstraints gbc_lblVolume = new GridBagConstraints();
		gbc_lblVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblVolume.gridx = 0;
		gbc_lblVolume.gridy = 5;
		control.add(lblVolume, gbc_lblVolume);

		JSlider sliderVolume = new JSlider();
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (selected2location > -1) {

				}
			}
		});
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

		JPanel AddMusicPanel = new JPanel();
		tabbedPane.addTab("Add Music", null, AddMusicPanel, null);
		GridBagLayout gbl_AddMusicPanel = new GridBagLayout();
		gbl_AddMusicPanel.columnWidths = new int[] { 0, 311, 0, 0 };
		gbl_AddMusicPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_AddMusicPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_AddMusicPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		AddMusicPanel.setLayout(gbl_AddMusicPanel);

		JLabel errorMessage = new JLabel("");
		GridBagConstraints gbc_errorMessage = new GridBagConstraints();
		gbc_errorMessage.gridwidth = 2;
		gbc_errorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_errorMessage.gridx = 0;
		gbc_errorMessage.gridy = 0;
		AddMusicPanel.add(errorMessage, gbc_errorMessage);

		addMusicErrMsg = new JLabel("New label");
		GridBagConstraints gbc_addMusicErrMsg = new GridBagConstraints();
		gbc_addMusicErrMsg.gridwidth = 3;
		gbc_addMusicErrMsg.insets = new Insets(0, 0, 5, 0);
		gbc_addMusicErrMsg.gridx = 0;
		gbc_addMusicErrMsg.gridy = 1;
		AddMusicPanel.add(addMusicErrMsg, gbc_addMusicErrMsg);

		JLabel lblArtistName = new JLabel("Artist Name:");
		GridBagConstraints gbc_lblArtistName = new GridBagConstraints();
		gbc_lblArtistName.insets = new Insets(0, 0, 5, 5);
		gbc_lblArtistName.anchor = GridBagConstraints.WEST;
		gbc_lblArtistName.gridx = 0;
		gbc_lblArtistName.gridy = 2;
		AddMusicPanel.add(lblArtistName, gbc_lblArtistName);

		txtArtistName = new JTextField();
		GridBagConstraints gbc_txtArtistName = new GridBagConstraints();
		gbc_txtArtistName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtArtistName.insets = new Insets(0, 0, 5, 5);
		gbc_txtArtistName.gridx = 1;
		gbc_txtArtistName.gridy = 2;
		AddMusicPanel.add(txtArtistName, gbc_txtArtistName);
		txtArtistName.setColumns(10);

		JButton btnAddArtist = new JButton("Add Artist");
		btnAddArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addArtistActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnAddArtist = new GridBagConstraints();
		gbc_btnAddArtist.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddArtist.gridx = 2;
		gbc_btnAddArtist.gridy = 2;
		AddMusicPanel.add(btnAddArtist, gbc_btnAddArtist);

		JLabel lblAlbumName = new JLabel("Album Name:");
		GridBagConstraints gbc_lblAlbumName = new GridBagConstraints();
		gbc_lblAlbumName.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumName.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumName.gridx = 0;
		gbc_lblAlbumName.gridy = 4;
		AddMusicPanel.add(lblAlbumName, gbc_lblAlbumName);

		txtAlbumName = new JTextField();
		GridBagConstraints gbc_txtAlbumName = new GridBagConstraints();
		gbc_txtAlbumName.insets = new Insets(0, 0, 5, 5);
		gbc_txtAlbumName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAlbumName.gridx = 1;
		gbc_txtAlbumName.gridy = 4;
		AddMusicPanel.add(txtAlbumName, gbc_txtAlbumName);
		txtAlbumName.setColumns(10);

		JLabel lblAlbumGenre = new JLabel("Album Genre:");
		GridBagConstraints gbc_lblAlbumGenre = new GridBagConstraints();
		gbc_lblAlbumGenre.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumGenre.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumGenre.gridx = 0;
		gbc_lblAlbumGenre.gridy = 5;
		AddMusicPanel.add(lblAlbumGenre, gbc_lblAlbumGenre);

		txtAlbumGenre = new JTextField();
		GridBagConstraints gbc_txtAlbumGenre = new GridBagConstraints();
		gbc_txtAlbumGenre.insets = new Insets(0, 0, 5, 5);
		gbc_txtAlbumGenre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAlbumGenre.gridx = 1;
		gbc_txtAlbumGenre.gridy = 5;
		AddMusicPanel.add(txtAlbumGenre, gbc_txtAlbumGenre);
		txtAlbumGenre.setColumns(10);

		JLabel lblReleaseDate = new JLabel("Release Date:");
		GridBagConstraints gbc_lblReleaseDate = new GridBagConstraints();
		gbc_lblReleaseDate.anchor = GridBagConstraints.WEST;
		gbc_lblReleaseDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblReleaseDate.gridx = 0;
		gbc_lblReleaseDate.gridy = 6;
		AddMusicPanel.add(lblReleaseDate, gbc_lblReleaseDate);

		albumReleaseDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		GridBagConstraints gbc_albumReleaseDatePicker = new GridBagConstraints();
		gbc_albumReleaseDatePicker.insets = new Insets(0, 0, 5, 5);
		gbc_albumReleaseDatePicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_albumReleaseDatePicker.gridx = 1;
		gbc_albumReleaseDatePicker.gridy = 6;
		AddMusicPanel.add(albumReleaseDatePicker, gbc_albumReleaseDatePicker);

		JLabel lblAlbumArtist = new JLabel("Album Artist");
		GridBagConstraints gbc_lblAlbumArtist = new GridBagConstraints();
		gbc_lblAlbumArtist.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumArtist.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumArtist.gridx = 0;
		gbc_lblAlbumArtist.gridy = 7;
		AddMusicPanel.add(lblAlbumArtist, gbc_lblAlbumArtist);

		albumArtistCB = new JComboBox();
		albumArtistCB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedArtist = cb.getSelectedIndex();
			}
		});
		GridBagConstraints gbc_albumArtistCB = new GridBagConstraints();
		gbc_albumArtistCB.insets = new Insets(0, 0, 5, 5);
		gbc_albumArtistCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_albumArtistCB.gridx = 1;
		gbc_albumArtistCB.gridy = 7;
		AddMusicPanel.add(albumArtistCB, gbc_albumArtistCB);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 8;
		AddMusicPanel.add(verticalStrut_1, gbc_verticalStrut_1);

		JLabel lblSongName = new JLabel("Song Name:");
		GridBagConstraints gbc_lblSongName = new GridBagConstraints();
		gbc_lblSongName.anchor = GridBagConstraints.WEST;
		gbc_lblSongName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongName.gridx = 0;
		gbc_lblSongName.gridy = 9;
		AddMusicPanel.add(lblSongName, gbc_lblSongName);

		featuringArtistList = new JList();
		featuringArtistList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				featuringArtistListSelectionListener(e);
			}
		});
		GridBagConstraints gbc_featuringArtistList = new GridBagConstraints();
		gbc_featuringArtistList.insets = new Insets(0, 0, 5, 5);
		gbc_featuringArtistList.fill = GridBagConstraints.BOTH;
		gbc_featuringArtistList.gridx = 1;
		gbc_featuringArtistList.gridy = 10;
		AddMusicPanel.add(featuringArtistList, gbc_featuringArtistList);

		JButton btnAddAlbum = new JButton("Add Album");
		btnAddAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAlbumActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnAddAlbum = new GridBagConstraints();
		gbc_btnAddAlbum.anchor = GridBagConstraints.SOUTH;
		gbc_btnAddAlbum.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddAlbum.gridx = 2;
		gbc_btnAddAlbum.gridy = 7;
		AddMusicPanel.add(btnAddAlbum, gbc_btnAddAlbum);

		JLabel lblFeaturingArtist = new JLabel("Featuring Artist:");
		GridBagConstraints gbc_lblFeaturingArtist = new GridBagConstraints();
		gbc_lblFeaturingArtist.anchor = GridBagConstraints.WEST;
		gbc_lblFeaturingArtist.insets = new Insets(0, 0, 5, 5);
		gbc_lblFeaturingArtist.gridx = 0;
		gbc_lblFeaturingArtist.gridy = 10;
		AddMusicPanel.add(lblFeaturingArtist, gbc_lblFeaturingArtist);

		txtSongName = new JTextField();
		GridBagConstraints gbc_txtSongName = new GridBagConstraints();
		gbc_txtSongName.insets = new Insets(0, 0, 5, 5);
		gbc_txtSongName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSongName.gridx = 1;
		gbc_txtSongName.gridy = 9;
		AddMusicPanel.add(txtSongName, gbc_txtSongName);
		txtSongName.setColumns(10);

		JLabel lblSongPosition = new JLabel("Song Position");
		GridBagConstraints gbc_lblSongPosition = new GridBagConstraints();
		gbc_lblSongPosition.anchor = GridBagConstraints.WEST;
		gbc_lblSongPosition.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongPosition.gridx = 0;
		gbc_lblSongPosition.gridy = 11;
		AddMusicPanel.add(lblSongPosition, gbc_lblSongPosition);

		songPosSpin = new JSpinner();
		GridBagConstraints gbc_songPosSpin = new GridBagConstraints();
		gbc_songPosSpin.fill = GridBagConstraints.HORIZONTAL;
		gbc_songPosSpin.insets = new Insets(0, 0, 5, 5);
		gbc_songPosSpin.gridx = 1;
		gbc_songPosSpin.gridy = 11;
		AddMusicPanel.add(songPosSpin, gbc_songPosSpin);

		JLabel lblSongDuration = new JLabel("Song Duration");
		GridBagConstraints gbc_lblSongDuration = new GridBagConstraints();
		gbc_lblSongDuration.anchor = GridBagConstraints.WEST;
		gbc_lblSongDuration.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongDuration.gridx = 0;
		gbc_lblSongDuration.gridy = 12;
		AddMusicPanel.add(lblSongDuration, gbc_lblSongDuration);

		songDurSpin = new JSpinner();
		GridBagConstraints gbc_songDurSpin = new GridBagConstraints();
		gbc_songDurSpin.fill = GridBagConstraints.HORIZONTAL;
		gbc_songDurSpin.insets = new Insets(0, 0, 5, 5);
		gbc_songDurSpin.gridx = 1;
		gbc_songDurSpin.gridy = 12;
		AddMusicPanel.add(songDurSpin, gbc_songDurSpin);

		JLabel lblAlbum = new JLabel("Album");
		GridBagConstraints gbc_lblAlbum = new GridBagConstraints();
		gbc_lblAlbum.anchor = GridBagConstraints.WEST;
		gbc_lblAlbum.insets = new Insets(0, 0, 0, 5);
		gbc_lblAlbum.gridx = 0;
		gbc_lblAlbum.gridy = 13;
		AddMusicPanel.add(lblAlbum, gbc_lblAlbum);

		albumCB = new JComboBox();
		albumCB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedAlbum = cb.getSelectedIndex();
			}
		});
		GridBagConstraints gbc_albumCB = new GridBagConstraints();
		gbc_albumCB.insets = new Insets(0, 0, 0, 5);
		gbc_albumCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_albumCB.gridx = 1;
		gbc_albumCB.gridy = 13;
		AddMusicPanel.add(albumCB, gbc_albumCB);

		JButton btnAddSong = new JButton("Add Song");
		btnAddSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSongActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnAddSong = new GridBagConstraints();
		gbc_btnAddSong.gridx = 2;
		gbc_btnAddSong.gridy = 13;
		AddMusicPanel.add(btnAddSong, gbc_btnAddSong);

		JPanel myMusic = new JPanel();
		tabbedPane.addTab("My Music", null, myMusic, null);
		GridBagLayout gbl_myMusic = new GridBagLayout();
		gbl_myMusic.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_myMusic.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_myMusic.columnWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_myMusic.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		myMusic.setLayout(gbl_myMusic);
		columnNames.addElement("Artist");
		columnNames.addElement("Album");
		columnNames.addElement("Song");

		tableModel = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		table = new JTable();
		table.setModel(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener(table);
		table.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				tableListSelectionListener(e);
			}
		});

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

		locationCB = new JComboBox();
		locationCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				selectedLocation = cb.getSelectedIndex();

			}
		});
		GridBagConstraints gbc_locationCB = new GridBagConstraints();
		gbc_locationCB.insets = new Insets(0, 0, 5, 5);
		gbc_locationCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_locationCB.gridx = 1;
		gbc_locationCB.gridy = 1;
		myMusic.add(locationCB, gbc_locationCB);

		JButton btnPlaySltSong = new JButton("Play");
		btnPlaySltSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSltSongActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnPlaySltSong = new GridBagConstraints();
		gbc_btnPlaySltSong.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlaySltSong.anchor = GridBagConstraints.EAST;
		gbc_btnPlaySltSong.gridx = 1;
		gbc_btnPlaySltSong.gridy = 2;
		myMusic.add(btnPlaySltSong, gbc_btnPlaySltSong);

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
		gbl_Playlist.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_Playlist.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_Playlist.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		Playlist.setLayout(gbl_Playlist);

		playlistErrMsg = new JLabel("");
		GridBagConstraints gbc_playlistErrMsg = new GridBagConstraints();
		gbc_playlistErrMsg.gridwidth = 3;
		gbc_playlistErrMsg.insets = new Insets(0, 0, 5, 5);
		gbc_playlistErrMsg.gridx = 0;
		gbc_playlistErrMsg.gridy = 0;
		Playlist.add(playlistErrMsg, gbc_playlistErrMsg);

		JLabel lblPlaylistName = new JLabel("Playlist Name:");
		GridBagConstraints gbc_lblPlaylistName = new GridBagConstraints();
		gbc_lblPlaylistName.anchor = GridBagConstraints.EAST;
		gbc_lblPlaylistName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlaylistName.gridx = 0;
		gbc_lblPlaylistName.gridy = 1;
		Playlist.add(lblPlaylistName, gbc_lblPlaylistName);

		txtPlaylistName = new JTextField();
		txtPlaylistName.setText("Playlist Name");
		GridBagConstraints gbc_txtPlaylistName = new GridBagConstraints();
		gbc_txtPlaylistName.insets = new Insets(0, 0, 5, 5);
		gbc_txtPlaylistName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPlaylistName.gridx = 1;
		gbc_txtPlaylistName.gridy = 1;
		Playlist.add(txtPlaylistName, gbc_txtPlaylistName);
		txtPlaylistName.setColumns(10);

		JLabel lblSelectSongs = new JLabel("Select Songs:");
		GridBagConstraints gbc_lblSelectSongs = new GridBagConstraints();
		gbc_lblSelectSongs.insets = new Insets(0, 0, 0, 5);
		gbc_lblSelectSongs.gridx = 0;
		gbc_lblSelectSongs.gridy = 2;
		Playlist.add(lblSelectSongs, gbc_lblSelectSongs);

		songList = new JList();
		songList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				songListSelectionListener(e);
			}
		});
		GridBagConstraints gbc_songsList = new GridBagConstraints();
		gbc_songsList.insets = new Insets(0, 0, 0, 5);
		gbc_songsList.fill = GridBagConstraints.BOTH;
		gbc_songsList.gridx = 1;
		gbc_songsList.gridy = 2;
		Playlist.add(songList, gbc_songsList);

		JButton btnCreatePlaylist = new JButton("Create\nPlaylist");
		btnCreatePlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPlaylistActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnCreatePlaylist = new GridBagConstraints();
		gbc_btnCreatePlaylist.anchor = GridBagConstraints.SOUTH;
		gbc_btnCreatePlaylist.gridx = 2;
		gbc_btnCreatePlaylist.gridy = 2;
		Playlist.add(btnCreatePlaylist, gbc_btnCreatePlaylist);
	}

	private void refreshData() {
		
		HAS h = HAS.getInstance();

		resetDataArtist();

		// error
		roomErrMsg.setText(error_RP);
		playlistErrMsg.setText(error_PP);
		addMusicErrMsg.setText(error_AP);

		roomsString = new HashMap<Integer, String>();
		Iterator<Room> roomIt = h.getRooms().iterator();
		Integer index = 0;
		while (roomIt.hasNext()) {
			Room r = roomIt.next();
			roomsString.put(index, r.getName());
			index++;
		}

		roomGroupString = new HashMap<Integer, String>();
		Iterator<RoomGroup> roomGIt = h.getRoomGroups().iterator();
		Integer index2 = 0;
		while (roomGIt.hasNext()) {
			RoomGroup rg = roomGIt.next();
			roomGroupString.put(index, rg.getName());
			index2++;
		}

		// ctl page
		if (error_CP == null || error_CP.length() == 0) {
			if (roomsString.size() > 0) {
				location2Map = new HashMap<Integer, String>();
				for (int i = 0; i < (roomsString.size() + roomGroupString.size()); i++) {
					if (i < roomsString.size()) {
						location2Map.put(i, roomsString.get(i));
					} else {
						location2Map.put(i, roomGroupString.get((i - roomsString.size())));
					}
				}
				location2CB.addItem(location2Map.values());
			}

		}

		if (error_AP == null || error_AP.length() == 0)

		{
			fArtistString = new HashMap<Integer, String>();
			Iterator<Artist> artIt = h.getArtists().iterator();
			Integer index3 = 0;
			while (artIt.hasNext()) {
				Artist a = artIt.next();
				fArtistString.put(index3, a.getName());
				index3++;
			}

			if (!fArtistString.isEmpty()) {
				featuringArtistList.setListData(fArtistString.values().toArray());
				featuringArtistList.clearSelection();
			}

			artists = new HashMap<Integer, Artist>();
			albumArtistCB.removeAllItems();
			Iterator<Artist> alarIt = h.getArtists().iterator();
			Integer index4 = 0;
			while (alarIt.hasNext()) {
				Artist alar = alarIt.next();
				artists.put(index4, alar);
				albumArtistCB.addItem(alar.getName());
				index4++;
			}
			selectedArtist = -1;
			albumArtistCB.setSelectedIndex(selectedArtist);

			albums = new HashMap<Integer, Album>();
			albumCB.removeAllItems();
			Iterator<Album> alIt = h.getAlbums().iterator();
			index = 0;
			while (alIt.hasNext()) {
				Album al = alIt.next();
				albums.put(index, al);
				albumCB.addItem(al.getName());
				index++;
			}
			selectedAlbum = -1;
			albumCB.setSelectedIndex(selectedArtist);

		}

		// room page
		if (error_RP == null || error_RP.length() == 0)

		{

			txtRoomName.setText("");

			if (!roomsString.isEmpty()) {
				roomList.setListData(roomsString.values().toArray());
				roomList.clearSelection();
				txtRoomGroupName.setText("");

			}

			artists = new HashMap<Integer, Artist>();
			albumArtistCB.removeAllItems();
			Iterator<Artist> arIt = h.getArtists().iterator();
			index = 0;
			while (arIt.hasNext()) {
				Artist ar = arIt.next();
				artists.put(index, ar);
				albumArtistCB.addItem(ar.getName());
				index++;
			}
			selectedArtist = -1;
			albumArtistCB.setSelectedIndex(selectedArtist);
		}

		if (error_PP == null || error_PP.length() == 0)

		{
			songsString = new HashMap<Integer, String>();
			Iterator<Song> songIt = h.getSongs().iterator();
			Integer index5 = 0;
			while (songIt.hasNext()) {
				Song s = songIt.next();
				songsString.put(index5, s.getName());
				index5++;
			}

			txtRoomName.setText("");

			if (!songsString.isEmpty()) {
				songList.setListData(songsString.values().toArray());
				songList.clearSelection();
				txtRoomGroupName.setText("");

			}
		}

		if (error_MP == null || error_MP.length() == 0) {

			locationMap = new HashMap<Integer, String>();
			for (int i = 0; i < (roomsString.size() + roomGroupString.size()); i++) {
				if (i < roomsString.size()) {
					locationMap.put(i, roomsString.get(i));
				} else {
					locationMap.put(i, roomGroupString.get((i - roomsString.size())));
				}
			}
			locationCB.addItem(locationMap.values());
		}

	}

	// room page

	private void createRoomActionPerformed(java.awt.event.ActionEvent evt) {

		HASController hc = new HASController();

		try {
			hc.createRoom(txtRoomName.getText());
		} catch (InvalidInputException e) {
			error_RP = e.getMessage();
		}

		refreshData();
	}

	private void roomListSelectionListener(ListSelectionEvent e) {
		HAS h = HAS.getInstance();

		boolean adjust = e.getValueIsAdjusting();
		if (!adjust) {
			JList list = (JList) e.getSource();
			int roomSelections[] = list.getSelectedIndices();
			// Object selectionValues[] = list.getSelectedValues();
			roomSelectionlist = new ArrayList<Room>();
			for (int i = 0; i < roomSelections.length; i++) {
				roomSelectionlist.add(i, h.getRoom(roomSelections[i]));

			}
		}
	}

	private void createRoomGroupActionPerformed(java.awt.event.ActionEvent evt) {
		HASController hc = new HASController();
		try {
			hc.createRoomGroup(txtRoomGroupName.getText(), roomSelectionlist);
		}

		catch (InvalidInputException e) {
			error_PP = e.getMessage();
		}
		refreshData();
	}

	// playlist page
	private void songListSelectionListener(ListSelectionEvent e) {
		HAS h = HAS.getInstance();

		boolean adjust = e.getValueIsAdjusting();
		if (!adjust) {
			JList list = (JList) e.getSource();
			int songSelections[] = list.getSelectedIndices();
			// Object selectionValues[] = list.getSelectedValues();
			songSelectionlist = new ArrayList<Song>();
			for (int i = 0; i < songSelections.length; i++) {
				songSelectionlist.add(i, h.getSong(songSelections[i]));

			}
		}
	}

	private void createPlaylistActionPerformed(java.awt.event.ActionEvent evt) {
		HASController hc = new HASController();
		try {
			hc.createPlaylist(txtPlaylistName.getText(), songSelectionlist);
		} catch (InvalidInputException e) {
			error_PP = e.getMessage();
		}
		refreshData();
	}

	private void addArtistActionPerformed(java.awt.event.ActionEvent evt) {
		HASController hc = new HASController();
		error_AP = null;
		try {
			hc.createArtist(txtArtistName.getText());
		} catch (InvalidInputException e) {
			error_AP = e.getMessage();
		}
		refreshData();
	}

	private void addAlbumActionPerformed(java.awt.event.ActionEvent evt) {
		error_AP = "";
		if (selectedArtist < 0)
			error_AP = error_AP + "Album must have an artist!";
		if (albumReleaseDatePicker.getModel().getValue() == null) {
			error_AP = error_AP + "Album must have a release date! ";
		}
		error_AP = error_AP.trim();
		if (error_AP.length() == 0) {
			// call the method to create an album
			HASController hc = new HASController();
			Date selectedDate = (java.sql.Date) albumReleaseDatePicker.getModel().getValue();

			try {
				hc.createAlbum(txtAlbumName.getText(), txtAlbumGenre.getText(), selectedDate,
						artists.get(selectedArtist));
			} catch (InvalidInputException e) {
				error_AP = e.getMessage();
			}

		}
		refreshData();

	}

	private void featuringArtistListSelectionListener(ListSelectionEvent e) {
		HAS h = HAS.getInstance();

		boolean adjust = e.getValueIsAdjusting();
		if (!adjust) {
			JList list = (JList) e.getSource();
			int artistSelections[] = list.getSelectedIndices();
			// Object selectionValues[] = list.getSelectedValues();
			fArtistSelectionList = new ArrayList<Artist>();
			for (int i = 0; i < artistSelections.length; i++) {
				fArtistSelectionList.add(i, h.getArtist(artistSelections[i]));
			}
		}
	}

	private void addSongActionPerformed(java.awt.event.ActionEvent evt) {
		error_AP = "";
		if (selectedAlbum < 0)
			error_AP = error_AP + "Song must have an album! ";

		// TODO change the ranges of values for songDurationSpinner and
		// songPositionSpinner

		error_AP = error_AP.trim();
		if (error_AP.length() == 0) {

			HASController hc = new HASController();
			try { // TODO:need to fix
				hc.addSongtoAlbum(albums.get(selectedAlbum), txtSongName.getText(),
						(Integer) (songDurSpin.getModel().getValue()), (Integer) (songPosSpin.getModel().getValue()),
						fArtistSelectionList);
			} catch (InvalidInputException e) {
				error_AP = e.getMessage();
			}

		}

		refreshData();
	}

	private void tableListSelectionListener(ListSelectionEvent e) {
		HAS h = HAS.getInstance();

		boolean adjust = e.getValueIsAdjusting();
		if (!adjust) {
			row = table.getSelectedRow();
			column = table.getSelectedColumn();
			System.out.println(row);
			System.out.println(column);

		}

	}

	private void resetData() {
		HAS h = HAS.getInstance();
		data.clear();
		if (h.getAlbums().size() > 0) {
			for (int in = 0; in < h.getAlbums().size(); in++) {
				if (h.getAlbum(in).getSongs().size() > 0) {
					for (int jn = 0; jn < h.getAlbum(in).getSongs().size(); jn++) {

						Vector tablerow = new Vector();

						tablerow.addElement(h.getAlbum(in).getMainArtist().getName());
						tablerow.addElement(h.getAlbum(in).getName());
						tablerow.addElement(h.getAlbum(in).getSong(jn).getName());

						data.addElement(tablerow);

					}
				}
			}
		}
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();

	}
	
	private void resetDataArtist() {
		HAS h = HAS.getInstance();
		data.clear();
		if (h.getArtists().size() > 0) {
			for (int in = 0; in < h.getArtists().size(); in++) {
				if (h.getArtist(in).getAlbums().size() > 0) {
					for (int jn = 0; jn < h.getArtist(in).getAlbums().size(); jn++) {
						if(h.getArtist(in).getAlbum(jn).getSongs().size()>0)
							for(int kn=0; kn<h.getArtist(in).getAlbum(jn).getSongs().size();kn++){

						Vector tablerowA = new Vector();

						tablerowA.addElement(h.getArtist(in).getName());
						tablerowA.addElement(h.getArtist(in).getAlbum(jn).getName());
						tablerowA.addElement(h.getArtist(in).getAlbum(jn).getSong(kn).getName());

						data.addElement(tablerowA);
							}

					}
				}
			}
		}
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();

	}


	
	
	// TODO maybe use this?
	// public static DefaultTableModel buildTableModel(ResultSet rs) throws
	// SQLException {
	//
	// ResultSetMetaData metaData = rs.getMetaData();
	//
	// // names of columns
	// Vector<String> columnNames = new Vector<String>();
	// int columnCount = metaData.getColumnCount();
	// for (int column = 1; column <= columnCount; column++) {
	// columnNames.add(metaData.getColumnName(column));
	// }
	//
	// // data of the table
	// Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	// while (rs.next()) {
	// Vector<Object> vector = new Vector<Object>();
	// for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	// vector.add(rs.getObject(columnIndex));
	// }
	// data.add(vector);
	// }
	//
	// return new DefaultTableModel(data, columnNames);
	//
	// }

	private void playSltSongActionPerformed(java.awt.event.ActionEvent evt) {
		HASController hc = new HASController();
		if (selectedLocation > roomsString.size()) {
			try {
				hc.playRoomGroup(null, roomGroups.get(selectedLocation));
			} catch (InvalidInputException e) {
				error_AP = e.getMessage();
			}
	}
	}

//	private void mVolumeChanged(java.awt.event)){
//		 HASController hc = new HASController();
//		 for(i=0;i<roomString.size();i++){
//		 try{
//		 hc.setRoomVolumeLevel(room, volumeLevel);
//		 }
//		 }
//		
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

