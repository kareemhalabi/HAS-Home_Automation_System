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
import javax.swing.SpinnerNumberModel;

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
import ca.mcgill.ecse321.HAS.model.Playlist;
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

	// Pages
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel control;
	private JPanel playPlaylist;
	private JTable table;

	// Control page
	private String error_CP = "";
	private JLabel cpErrMsg;
	private String nowPlaying = "";
	private Song playSong;
	private JLabel ctlErrMsg;
	private Integer selectedRoom = -1;
	private Integer selectedRoomGroup = -1;
	private HashMap<Integer, RoomGroup> roomGroups;
	private HashMap<Integer, Room> rooms;
	private Integer mVolume = 50;
	private Integer rVolume = 50;
	private JComboBox locationctrCB;
	private Integer selectedctrlocation = -1;
	private HashMap<Integer, String> locationctrMap;
	private Integer sliderVol;
	private Integer mSliderVol;
	private boolean mMuteBox;
	private boolean muteBox;
	private JSlider sliderVolume;
	private JSlider sliderMasterVolume;
	private JCheckBox mChckbxMute;
	private JCheckBox chckbxMute;
	private JLabel lblPlaying;

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
	private String error_PPP = "";
	private JLabel pppErrMsg;
	private JLabel playlistErrMsg;
	private JTextField txtPlaylistName;
	private List<Song> songSelectionlist;
	private HashMap<Integer, String> songsString;
	private JList songList;
	private Integer selected3Location = -1;
	private HashMap<Integer, Playlist> playlistSelectionList;
	private Integer selectedPlaylist = -1;
	private JComboBox playlistCB;
	private JComboBox locations3CB;
	private HashMap<Integer, String> location3Map;

	// add music page
	private String error_AP = "";
	private JLabel addMusicErrMsg;
	boolean sortedByArtist = true;
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
	private JLabel mpErrMsg;
	private String error_MP = "";
	private Integer row = -1;
	private Integer column = -1;
	private JComboBox locationCB;
	private Integer selectedLocation = -1;
	private Vector data = new Vector();
	private Vector columnNames = new Vector();
	private DefaultTableModel tableModel;
	private Album playAlbum;

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

		setBounds(100, 100, 679, 483);
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
		ctlErrMsg.setForeground(Color.RED);
		GridBagConstraints gbc_ctlErrorMsg = new GridBagConstraints();
		gbc_ctlErrorMsg.gridwidth = 4;
		gbc_ctlErrorMsg.insets = new Insets(0, 0, 5, 0);
		gbc_ctlErrorMsg.gridx = 0;
		gbc_ctlErrorMsg.gridy = 0;
		control.add(ctlErrMsg, gbc_ctlErrorMsg);

		JLabel lblMasterVolume = new JLabel("Master Volume");
		GridBagConstraints gbc_lblMasterVolume = new GridBagConstraints();
		gbc_lblMasterVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblMasterVolume.gridx = 0;
		gbc_lblMasterVolume.gridy = 1;
		control.add(lblMasterVolume, gbc_lblMasterVolume);

		sliderMasterVolume = new JSlider();
		sliderMasterVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mSliderVol = sliderMasterVolume.getValue();
				mVolumeChange();
			}
		});

		GridBagConstraints gbc_sliderMasterVolume = new GridBagConstraints();
		gbc_sliderMasterVolume.gridwidth = 2;
		gbc_sliderMasterVolume.insets = new Insets(0, 0, 5, 5);
		gbc_sliderMasterVolume.gridx = 1;
		gbc_sliderMasterVolume.gridy = 1;
		control.add(sliderMasterVolume, gbc_sliderMasterVolume);

		mChckbxMute = new JCheckBox("Mute");
		mChckbxMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mMuteBox = mChckbxMute.isSelected();
				mMuteChange();
			}
		});
		GridBagConstraints gbc_mChckbxMute = new GridBagConstraints();
		gbc_mChckbxMute.insets = new Insets(0, 0, 5, 0);
		gbc_mChckbxMute.gridx = 3;
		gbc_mChckbxMute.gridy = 1;
		control.add(mChckbxMute, gbc_mChckbxMute);

		JButton btnStopAll = new JButton("Stop all music");
		btnStopAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopAllActionPerformed(e);
			}
		});
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

		locationctrCB = new JComboBox();
		locationctrCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				selectedctrlocation = cb.getSelectedIndex();
				refreshMute();
				refreshVol();
				setPlayinglbl();
			}
		});

		GridBagConstraints gbc_locationctrCB = new GridBagConstraints();
		gbc_locationctrCB.gridwidth = 2;
		gbc_locationctrCB.insets = new Insets(0, 0, 5, 5);
		gbc_locationctrCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_locationctrCB.gridx = 1;
		gbc_locationctrCB.gridy = 4;
		control.add(locationctrCB, gbc_locationctrCB);

		JLabel lblVolume = new JLabel("Volume:");
		GridBagConstraints gbc_lblVolume = new GridBagConstraints();
		gbc_lblVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblVolume.gridx = 0;
		gbc_lblVolume.gridy = 5;
		control.add(lblVolume, gbc_lblVolume);

		sliderVolume = new JSlider();
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (selectedctrlocation > -1) {
					sliderVol = sliderVolume.getValue();
					volumeChange();

				}
			}
		});
		GridBagConstraints gbc_sliderVolume = new GridBagConstraints();
		gbc_sliderVolume.gridwidth = 2;
		gbc_sliderVolume.insets = new Insets(0, 0, 5, 5);
		gbc_sliderVolume.gridx = 1;
		gbc_sliderVolume.gridy = 5;
		control.add(sliderVolume, gbc_sliderVolume);

		chckbxMute = new JCheckBox("Mute");
		chckbxMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				muteBox = chckbxMute.isSelected();
				muteChange();
			}
		});
		GridBagConstraints gbc_chckbxMute = new GridBagConstraints();
		gbc_chckbxMute.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxMute.gridx = 3;
		gbc_chckbxMute.gridy = 5;
		control.add(chckbxMute, gbc_chckbxMute);

		JLabel lblNowPlaying = new JLabel("Now Playing");
		GridBagConstraints gbc_lblNowPlaying = new GridBagConstraints();
		gbc_lblNowPlaying.insets = new Insets(0, 0, 5, 5);
		gbc_lblNowPlaying.gridx = 0;
		gbc_lblNowPlaying.gridy = 6;
		control.add(lblNowPlaying, gbc_lblNowPlaying);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopActionPerformed(e);
			}
		});

		lblPlaying = new JLabel("");
		GridBagConstraints gbc_lblPlaying = new GridBagConstraints();
		gbc_lblPlaying.gridwidth = 2;
		gbc_lblPlaying.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlaying.gridx = 1;
		gbc_lblPlaying.gridy = 6;
		control.add(lblPlaying, gbc_lblPlaying);
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.gridwidth = 2;
		gbc_btnStop.insets = new Insets(0, 0, 5, 5);
		gbc_btnStop.gridx = 1;
		gbc_btnStop.gridy = 7;
		control.add(btnStop, gbc_btnStop);

		JPanel myMusic = new JPanel();
		tabbedPane.addTab("My Music", null, myMusic, null);
		GridBagLayout gbl_myMusic = new GridBagLayout();
		gbl_myMusic.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_myMusic.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_myMusic.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_myMusic.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		myMusic.setLayout(gbl_myMusic);

		columnNames.addElement("Artist");
		columnNames.addElement("Album");
		columnNames.addElement("Song");
		columnNames.addElement("Duration (seconds)");
		columnNames.addElement("Release Date");
		columnNames.addElement("Song Position");

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
		table.setRowSelectionAllowed(true);
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener(table);
		table.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				// tableListSelectionListener(e);

			}
		});
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				tableListSelectionListener(event);
			}
		});

		JButton btnSortByAlbum = new JButton("Sort By Album");
		btnSortByAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetData();
			}
		});

		mpErrMsg = new JLabel("");
		mpErrMsg.setForeground(Color.RED);
		GridBagConstraints gbc_mpErrMsg = new GridBagConstraints();
		gbc_mpErrMsg.gridwidth = 3;
		gbc_mpErrMsg.insets = new Insets(0, 0, 5, 5);
		gbc_mpErrMsg.gridx = 0;
		gbc_mpErrMsg.gridy = 0;

		myMusic.add(mpErrMsg, gbc_mpErrMsg);
		GridBagConstraints gbc_btnSortByAlbum = new GridBagConstraints();
		gbc_btnSortByAlbum.insets = new Insets(0, 0, 5, 5);
		gbc_btnSortByAlbum.gridx = 0;
		gbc_btnSortByAlbum.gridy = 1;
		myMusic.add(btnSortByAlbum, gbc_btnSortByAlbum);

		JButton btnSortByArtist = new JButton("Sort By Artist");
		btnSortByArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetDataArtist();
			}
		});
		GridBagConstraints gbc_btnSortByArtist = new GridBagConstraints();
		gbc_btnSortByArtist.gridwidth = 2;
		gbc_btnSortByArtist.insets = new Insets(0, 0, 5, 5);
		gbc_btnSortByArtist.gridx = 1;
		gbc_btnSortByArtist.gridy = 1;
		myMusic.add(btnSortByArtist, gbc_btnSortByArtist);

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 2;
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		myMusic.add(table, gbc_table);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.BOTH;
		myMusic.add(new JScrollPane(table), gbc);

		JLabel lblLocation = new JLabel("Location:");
		GridBagConstraints gbc_lblLocation = new GridBagConstraints();
		gbc_lblLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocation.anchor = GridBagConstraints.EAST;
		gbc_lblLocation.gridx = 0;
		gbc_lblLocation.gridy = 3;
		myMusic.add(lblLocation, gbc_lblLocation);

		locationCB = new JComboBox();
		locationCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				selectedLocation = cb.getSelectedIndex();

			}
		});
		GridBagConstraints gbc_locationCB = new GridBagConstraints();
		gbc_locationCB.gridwidth = 2;
		gbc_locationCB.insets = new Insets(0, 0, 5, 5);
		gbc_locationCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_locationCB.gridx = 1;
		gbc_locationCB.gridy = 3;
		myMusic.add(locationCB, gbc_locationCB);

		JButton btnPlaySltSong = new JButton("Play Song");
		btnPlaySltSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSltSongActionPerformed(e);
			}
		});

		JButton btnPlayAlbum = new JButton("Play Album");
		btnPlayAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAlbumActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnPlayAlbum = new GridBagConstraints();
		gbc_btnPlayAlbum.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlayAlbum.gridx = 1;
		gbc_btnPlayAlbum.gridy = 4;
		myMusic.add(btnPlayAlbum, gbc_btnPlayAlbum);
		GridBagConstraints gbc_btnPlaySltSong = new GridBagConstraints();
		gbc_btnPlaySltSong.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlaySltSong.anchor = GridBagConstraints.EAST;
		gbc_btnPlaySltSong.gridx = 2;
		gbc_btnPlaySltSong.gridy = 4;
		myMusic.add(btnPlaySltSong, gbc_btnPlaySltSong);

		JPanel playlists2 = new JPanel();
		tabbedPane.addTab("Playlists", null, playlists2, null);
		GridBagLayout gbl_playlists2 = new GridBagLayout();
		gbl_playlists2.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_playlists2.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_playlists2.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_playlists2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		playlists2.setLayout(gbl_playlists2);

		pppErrMsg = new JLabel("");
		pppErrMsg.setForeground(Color.RED);
		GridBagConstraints gbc_pppErrMsg = new GridBagConstraints();
		gbc_pppErrMsg.gridwidth = 3;
		gbc_pppErrMsg.insets = new Insets(0, 0, 5, 5);
		gbc_pppErrMsg.gridx = 0;
		gbc_pppErrMsg.gridy = 0;
		playlists2.add(pppErrMsg, gbc_pppErrMsg);

		JLabel lblNewLabel = new JLabel(" Playlist:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		playlists2.add(lblNewLabel, gbc_lblNewLabel);

		playlistCB = new JComboBox();
		playlistCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPlaylist = playlistCB.getSelectedIndex();
			}
		});
		GridBagConstraints gbc_playlistCB = new GridBagConstraints();
		gbc_playlistCB.gridwidth = 2;
		gbc_playlistCB.insets = new Insets(0, 0, 5, 5);
		gbc_playlistCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_playlistCB.gridx = 1;
		gbc_playlistCB.gridy = 1;
		playlists2.add(playlistCB, gbc_playlistCB);

		JLabel lblLocation_1 = new JLabel("Location:");
		GridBagConstraints gbc_lblLocation_1 = new GridBagConstraints();
		gbc_lblLocation_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocation_1.anchor = GridBagConstraints.EAST;
		gbc_lblLocation_1.gridx = 1;
		gbc_lblLocation_1.gridy = 2;
		playlists2.add(lblLocation_1, gbc_lblLocation_1);

		locations3CB = new JComboBox();
		locations3CB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected3Location = locations3CB.getSelectedIndex();
			}
		});
		GridBagConstraints gbc_locations3CB = new GridBagConstraints();
		gbc_locations3CB.insets = new Insets(0, 0, 5, 5);
		gbc_locations3CB.fill = GridBagConstraints.HORIZONTAL;
		gbc_locations3CB.gridx = 2;
		gbc_locations3CB.gridy = 2;
		playlists2.add(locations3CB, gbc_locations3CB);

		JButton btnPlayPlaylist = new JButton("Play Playlist");
		btnPlayPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playPlaylistActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnPlayPlaylist = new GridBagConstraints();
		gbc_btnPlayPlaylist.anchor = GridBagConstraints.EAST;
		gbc_btnPlayPlaylist.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlayPlaylist.gridx = 2;
		gbc_btnPlayPlaylist.gridy = 3;
		playlists2.add(btnPlayPlaylist, gbc_btnPlayPlaylist);

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
		errorMessage.setForeground(Color.RED);
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
		SpinnerNumberModel songSpinModel = new SpinnerNumberModel(0, 0, 100, 1);
		songPosSpin.setModel(songSpinModel);
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
		SpinnerNumberModel songSpinDurModel = new SpinnerNumberModel(0, 0, 3600, 1);
		songDurSpin.setModel(songSpinDurModel);
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

		JPanel Playlist = new JPanel();
		tabbedPane.addTab("Create Playlist", null, Playlist, null);
		GridBagLayout gbl_Playlist = new GridBagLayout();
		gbl_Playlist.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_Playlist.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_Playlist.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_Playlist.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		Playlist.setLayout(gbl_Playlist);

		playlistErrMsg = new JLabel("");
		playlistErrMsg.setForeground(Color.RED);
		GridBagConstraints gbc_playlistErrMsg = new GridBagConstraints();
		gbc_playlistErrMsg.gridwidth = 2;
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
		GridBagConstraints gbc_txtPlaylistName = new GridBagConstraints();
		gbc_txtPlaylistName.insets = new Insets(0, 0, 5, 5);
		gbc_txtPlaylistName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPlaylistName.gridx = 1;
		gbc_txtPlaylistName.gridy = 1;
		Playlist.add(txtPlaylistName, gbc_txtPlaylistName);
		txtPlaylistName.setColumns(10);

		JLabel lblSelectSongs = new JLabel("Select Songs:");
		GridBagConstraints gbc_lblSelectSongs = new GridBagConstraints();
		gbc_lblSelectSongs.insets = new Insets(0, 0, 5, 5);
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
		gbc_songsList.insets = new Insets(0, 0, 5, 5);
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
		gbc_btnCreatePlaylist.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreatePlaylist.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnCreatePlaylist.gridx = 1;
		gbc_btnCreatePlaylist.gridy = 3;
		Playlist.add(btnCreatePlaylist, gbc_btnCreatePlaylist);

		JPanel RoomPanel = new JPanel();
		tabbedPane.addTab("Add Rooms", null, RoomPanel, null);
		GridBagLayout gbl_RoomPanel = new GridBagLayout();
		gbl_RoomPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_RoomPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_RoomPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_RoomPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		RoomPanel.setLayout(gbl_RoomPanel);

		roomErrMsg = new JLabel("");
		roomErrMsg.setForeground(Color.RED);
		GridBagConstraints gbc_lblRoomerrmsg = new GridBagConstraints();
		gbc_lblRoomerrmsg.gridwidth = 3;
		gbc_lblRoomerrmsg.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomerrmsg.gridx = 0;
		gbc_lblRoomerrmsg.gridy = 0;
		RoomPanel.add(roomErrMsg, gbc_lblRoomerrmsg);

		JLabel lblRoomName = new JLabel("Room name:");
		GridBagConstraints gbc_lblRoomName = new GridBagConstraints();
		gbc_lblRoomName.anchor = GridBagConstraints.WEST;
		gbc_lblRoomName.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomName.gridx = 0;
		gbc_lblRoomName.gridy = 1;
		RoomPanel.add(lblRoomName, gbc_lblRoomName);

		txtRoomName = new JTextField();
		txtRoomName.setText("Room name");
		GridBagConstraints gbc_txtRoomName = new GridBagConstraints();
		gbc_txtRoomName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRoomName.insets = new Insets(0, 0, 5, 5);
		gbc_txtRoomName.gridx = 1;
		gbc_txtRoomName.gridy = 1;
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
		gbc_btnCreateRoom.gridx = 2;
		gbc_btnCreateRoom.gridy = 1;
		RoomPanel.add(btnCreateRoom, gbc_btnCreateRoom);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 2;
		RoomPanel.add(verticalStrut, gbc_verticalStrut);

		roomList = new JList();
		roomList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				roomListSelectionListener(e);
			}
		});

		JLabel lblRoomGroupName = new JLabel("Room group name:");
		GridBagConstraints gbc_lblRoomGroupName = new GridBagConstraints();
		gbc_lblRoomGroupName.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomGroupName.anchor = GridBagConstraints.EAST;
		gbc_lblRoomGroupName.gridx = 0;
		gbc_lblRoomGroupName.gridy = 3;
		RoomPanel.add(lblRoomGroupName, gbc_lblRoomGroupName);

		txtRoomGroupName = new JTextField();
		txtRoomGroupName.setText("Room Group Name");
		GridBagConstraints gbc_txtRoomGroupName = new GridBagConstraints();
		gbc_txtRoomGroupName.insets = new Insets(0, 0, 5, 5);
		gbc_txtRoomGroupName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRoomGroupName.gridx = 1;
		gbc_txtRoomGroupName.gridy = 3;
		RoomPanel.add(txtRoomGroupName, gbc_txtRoomGroupName);
		txtRoomGroupName.setColumns(10);

		JLabel lblSelectRooms = new JLabel("Select Rooms:");
		GridBagConstraints gbc_lblSelectRooms = new GridBagConstraints();
		gbc_lblSelectRooms.anchor = GridBagConstraints.WEST;
		gbc_lblSelectRooms.insets = new Insets(0, 0, 0, 5);
		gbc_lblSelectRooms.gridx = 0;
		gbc_lblSelectRooms.gridy = 4;
		RoomPanel.add(lblSelectRooms, gbc_lblSelectRooms);
		GridBagConstraints gbc_roomList = new GridBagConstraints();
		gbc_roomList.insets = new Insets(0, 0, 0, 5);
		gbc_roomList.fill = GridBagConstraints.BOTH;
		gbc_roomList.gridx = 1;
		gbc_roomList.gridy = 4;
		RoomPanel.add(roomList, gbc_roomList);

		JButton btnCreateRoomGroup = new JButton("Create Room Group");
		btnCreateRoomGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createRoomGroupActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnCreateRoomGroup = new GridBagConstraints();
		gbc_btnCreateRoomGroup.anchor = GridBagConstraints.SOUTH;
		gbc_btnCreateRoomGroup.gridx = 2;
		gbc_btnCreateRoomGroup.gridy = 4;
		RoomPanel.add(btnCreateRoomGroup, gbc_btnCreateRoomGroup);

	}

	// update view on action
	private void refreshData() {

		HAS h = HAS.getInstance();

		resetDataArtist();

		// error messages
		playlistErrMsg.setText(error_PP);
		addMusicErrMsg.setText(error_AP);
		roomErrMsg.setText(error_RP);
		pppErrMsg.setText(error_PPP);
		mpErrMsg.setText(error_MP);
		ctlErrMsg.setText(error_CP);

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
			roomGroupString.put(index2, rg.getName());
			index2++;
		}

		// Playlist Play Page

		if (error_PPP == null || error_PPP.length() == 0) {

			playlistSelectionList = new HashMap<Integer, Playlist>();
			playlistCB.removeAllItems();

			Iterator<Playlist> pIt = h.getPlaylists().iterator();
			Integer index8 = 0;
			while (pIt.hasNext()) {
				Playlist p = pIt.next();
				playlistSelectionList.put(index8, p);
				playlistCB.addItem(p.getName());
				index8++;
			}

			location3Map = new HashMap<Integer, String>();
			locations3CB.removeAllItems();

			for (int i = 0; i < (h.getRooms().size() + h.getRoomGroups().size()); i++) {

				if (i < h.getRooms().size()) {
					location3Map.put(i, h.getRoom(i).getName());
				} else {
					location3Map.put(i, h.getRoomGroup(i - h.getRooms().size()).getName());
				}

			}

			for (int i = 0; i < location3Map.size(); i++) {
				locations3CB.addItem(location3Map.get(i));
			}

			selectedPlaylist = -1;
			playlistCB.setSelectedIndex(selectedPlaylist);

			selected3Location = -1;
			locations3CB.setSelectedIndex(selected3Location);

		}

		// ctl page
		if (error_CP == null || error_CP.length() == 0) {

			locationctrMap = new HashMap<Integer, String>();
			locationctrCB.removeAllItems();

			for (int i = 0; i < (h.getRooms().size() + h.getRoomGroups().size()); i++) {

				if (i < h.getRooms().size()) {
					locationctrMap.put(i, h.getRoom(i).getName());
				} else {
					locationctrMap.put(i, h.getRoomGroup(i - h.getRooms().size()).getName());
				}

			}

			for (int i = 0; i < locationctrMap.size(); i++) {
				locationctrCB.addItem(locationctrMap.get(i));
			}

		}
		// add music page
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
			albumCB.setSelectedIndex(selectedAlbum);

		}

		// room page
		if (error_RP == null || error_RP.length() == 0)

		{

			txtRoomName.setText("");

			// if there are rooms update room list
			if (!roomsString.isEmpty()) {
				roomList.setListData(roomsString.values().toArray());
				roomList.clearSelection();
				txtRoomGroupName.setText("");
			}

			// initialize data for rooms list
			rooms = new HashMap<Integer, Room>();
			Iterator<Room> roomIt2 = h.getRooms().iterator();
			Integer index6 = 0;
			while (roomIt2.hasNext()) {
				Room r = roomIt2.next();
				rooms.put(index6, r);
				index6++;
			}

			// initialize data for roomgroups list
			roomGroups = new HashMap<Integer, RoomGroup>();
			Iterator<RoomGroup> roomgIt = h.getRoomGroups().iterator();
			Integer index7 = 0;
			while (roomgIt.hasNext()) {
				RoomGroup rg = roomgIt.next();
				roomGroups.put(index7, rg);
				index7++;
			}

		}

		// Add Playlist page
		if (error_PP == null || error_PP.length() == 0)

		{
			// initialize data for song list
			songsString = new HashMap<Integer, String>();
			Iterator<Song> songIt = h.getSongs().iterator();
			Integer index5 = 0;
			while (songIt.hasNext()) {
				Song s = songIt.next();
				songsString.put(index5, s.getName());
				index5++;
			}

			txtRoomName.setText("");

			// set song list
			if (!songsString.isEmpty()) {
				songList.setListData(songsString.values().toArray());
				songList.clearSelection();
				txtRoomGroupName.setText("");

			}
		}
		// music page
		if (error_MP == null || error_MP.length() == 0) {

			// update location combobox
			locationMap = new HashMap<Integer, String>();
			locationCB.removeAllItems();

			for (int i = 0; i < (h.getRooms().size() + h.getRoomGroups().size()); i++) {

				if (i < h.getRooms().size()) {
					locationMap.put(i, h.getRoom(i).getName());
				} else {
					locationMap.put(i, h.getRoomGroup(i - h.getRooms().size()).getName());

				}

			}

			for (int i = 0; i < locationMap.size(); i++) {
				locationCB.addItem(locationMap.get(i));
			}
			selectedLocation = -1;
			locationCB.setSelectedIndex(selectedLocation);

		}

		// reset textfields and spinners
		txtAlbumName.setText("");
		txtAlbumGenre.setText("");
		albumReleaseDatePicker.getModel().setValue(null);
		txtArtistName.setText("");
		txtSongName.setText("");
		songDurSpin.setValue(0);
		songPosSpin.setValue(0);

	}

	// create room
	private void createRoomActionPerformed(java.awt.event.ActionEvent evt) {

		HASController hc = new HASController();
		error_RP = "";
		try {
			hc.createRoom(txtRoomName.getText());
		} catch (InvalidInputException e) {
			error_RP = e.getMessage();
		}

		refreshData();
	}

	// get selected rooms for roomgroup creation
	private void roomListSelectionListener(ListSelectionEvent e) {
		HAS h = HAS.getInstance();

		boolean adjust = e.getValueIsAdjusting();
		if (!adjust) {
			JList list = (JList) e.getSource();
			int roomSelections[] = list.getSelectedIndices();
			roomSelectionlist = new ArrayList<Room>();
			for (int i = 0; i < roomSelections.length; i++) {
				roomSelectionlist.add(i, h.getRoom(roomSelections[i]));

			}
		}
	}

	// create room group from selected songs
	private void createRoomGroupActionPerformed(java.awt.event.ActionEvent evt) {
		HASController hc = new HASController();
		error_RP = "";
		try {
			hc.createRoomGroup(txtRoomGroupName.getText(), roomSelectionlist);
		}

		catch (InvalidInputException e) {
			error_RP = e.getMessage();
		}
		refreshData();
	}

	// get songs selections for playlist creation
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

	// create playlist from selected songs
	private void createPlaylistActionPerformed(java.awt.event.ActionEvent evt) {
		error_PP = "";
		HASController hc = new HASController();
		try {
			hc.createPlaylist(txtPlaylistName.getText(), songSelectionlist);
		} catch (InvalidInputException e) {
			error_PP = e.getMessage();
		}
		refreshData();
	}

	// create artist
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

	// create album with associated artist
	private void addAlbumActionPerformed(java.awt.event.ActionEvent evt) {
		error_AP = "";
		if (selectedArtist < 0)
			error_AP = error_AP + "Album must have an artist!";
		if (albumReleaseDatePicker.getModel().getValue() == null) {
			error_AP = error_AP + " Album must have a release date! ";
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

	// get featuring artist selection
	private void featuringArtistListSelectionListener(ListSelectionEvent e) {
		HAS h = HAS.getInstance();

		boolean adjust = e.getValueIsAdjusting();
		if (!adjust) {
			JList list = (JList) e.getSource();
			int artistSelections[] = list.getSelectedIndices();
			fArtistSelectionList = new ArrayList<Artist>();
			for (int i = 0; i < artistSelections.length; i++) {
				fArtistSelectionList.add(i, h.getArtist(artistSelections[i]));
			}
		}
	}

	// add song to album
	private void addSongActionPerformed(java.awt.event.ActionEvent evt) {
		error_AP = "";
		if (selectedAlbum < 0)
			error_AP = error_AP + "Song must have an album! ";

		error_AP = error_AP.trim();
		if (error_AP.length() == 0) {

			HASController hc = new HASController();
			try {

				hc.addSongtoAlbum(albums.get(selectedAlbum), txtSongName.getText(),
						(Integer) (songDurSpin.getModel().getValue()), (Integer) (songPosSpin.getModel().getValue()),
						fArtistSelectionList);
			} catch (InvalidInputException e) {
				error_AP = e.getMessage();
			}

		}

		refreshData();
	}

	// get selected row of table
	private void tableListSelectionListener(ListSelectionEvent e) {
		HAS h = HAS.getInstance();

		boolean adjust = e.getValueIsAdjusting();
		if (!adjust) {
			row = table.getSelectedRow();

		}

	}

	// fill table data, sorted by Album
	private void resetData() {
		HAS h = HAS.getInstance();
		sortedByArtist = false;
		data.clear();
		if (h.getAlbums().size() > 0) {
			for (int in = 0; in < h.getAlbums().size(); in++) {
				if (h.getAlbum(in).getSongs().size() > 0) {
					for (int jn = 0; jn < h.getAlbum(in).getSongs().size(); jn++) {

						Vector tablerow = new Vector();

						tablerow.addElement(h.getAlbum(in).getMainArtist().getName());
						tablerow.addElement(h.getAlbum(in).getName());
						tablerow.addElement(h.getAlbum(in).getSong(jn).getName());
						tablerow.addElement(h.getAlbum(in).getSong(jn).getDuration());
						tablerow.addElement(h.getAlbum(in).getReleaseDate().getDate());
						tablerow.addElement(h.getAlbum(in).getSong(jn).getPosition());

						data.addElement(tablerow);

					}
				}
			}
		}
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();

	}

	// fill table data, sorted by Artist
	private void resetDataArtist() {
		HAS h = HAS.getInstance();
		sortedByArtist = true;
		data.clear();

		for (int in = 0; in < h.getArtists().size(); in++) {
			if (h.getArtist(in).getAlbums().size() > 0) {
				for (int jn = 0; jn < h.getArtist(in).getAlbums().size(); jn++) {
					if (h.getArtist(in).getAlbum(jn).getSongs().size() > 0)
						for (int kn = 0; kn < h.getArtist(in).getAlbum(jn).getSongs().size(); kn++) {

							Vector tablerowA = new Vector();

							tablerowA.addElement(h.getArtist(in).getName());
							tablerowA.addElement(h.getArtist(in).getAlbum(jn).getName());
							tablerowA.addElement(h.getArtist(in).getAlbum(jn).getSong(kn).getName());
							tablerowA.addElement(h.getArtist(in).getAlbum(jn).getSong(kn).getDuration());
							tablerowA.addElement(h.getArtist(in).getAlbum(jn).getReleaseDate().toString());
							tablerowA.addElement(h.getArtist(in).getAlbum(jn).getSong(kn).getPosition());

							data.addElement(tablerowA);
						}

				}
			}
		}

		table.setModel(tableModel);
		tableModel.fireTableDataChanged();

	}

	// play song in selected location
	private void playSltSongActionPerformed(java.awt.event.ActionEvent evt) {
		HASController hc = new HASController();
		HAS h = HAS.getInstance();
		error_MP = "";

		// check if location has been selected
		if (row > -1) {

			// gets song from list depending on how the table is sorted
			albumListSortByArtist();
			if (sortedByArtist == false) {
				albumListSortByAlbum();
			}

			if (selectedLocation > h.getRooms().size() - 1) {
				try {
					hc.playRoomGroup(playSong, h.getRoomGroup(selectedLocation - h.getRooms().size()));
					nowPlaying = playSong.getName();
				} catch (InvalidInputException e) {
					error_MP = e.getMessage();
				}
			} else {
				try {
					hc.playSingleRoom(playSong, h.getRoom(selectedLocation));
					nowPlaying = playSong.getName();
				} catch (InvalidInputException e) {
					error_MP = e.getMessage();
				}
			}
		} else {
			error_MP = "Please select a row to play a song";
		}
		refreshData();
	}

	// find album/song of selected row in JTable when it is sorted by artist
	private void albumListSortByArtist() {

		sortedByArtist = true;
		HAS h = HAS.getInstance();
		HashMap<Integer, Album> a = new HashMap<Integer, Album>();
		HashMap<Integer, Song> s = new HashMap<Integer, Song>();
		Integer index = 0;
		for (int in = 0; in < h.getArtists().size(); in++) {
			if (h.getArtist(in).getAlbums().size() > 0) {
				for (int jn = 0; jn < h.getArtist(in).getAlbums().size(); jn++) {
					if (h.getArtist(in).getAlbum(jn).getSongs().size() > 0)
						for (int kn = 0; kn < h.getArtist(in).getAlbum(jn).getSongs().size(); kn++) {

							a.put(index, h.getArtist(in).getAlbum(jn));
							s.put(index, h.getArtist(in).getAlbum(jn).getSong(kn));
							index++;

						}
				}
			}
		}
		playAlbum = a.get(row);
		playSong = s.get(row);
	}

	// find album/song of selected row in JTable when it is sorted by album
	private void albumListSortByAlbum() {
		sortedByArtist = false;
		HAS h = HAS.getInstance();
		HashMap<Integer, Album> a = new HashMap<Integer, Album>();
		HashMap<Integer, Song> s = new HashMap<Integer, Song>();
		Integer index = 0;

		if (h.getArtists().size() > 0) {
			for (int jn = 0; jn < h.getAlbums().size(); jn++) {
				if (h.getAlbum(jn).getSongs().size() > 0)
					for (int kn = 0; kn < h.getAlbum(jn).getSongs().size(); kn++) {

						a.put(index, h.getAlbum(jn));
						s.put(index, h.getAlbum(jn).getSong(kn));
						index++;

					}
			}
		}
		playAlbum = a.get(row);
		playSong = s.get(row);
	}

	// play album in selected location
	private void playAlbumActionPerformed(java.awt.event.ActionEvent evt) {
		HASController hc = new HASController();
		HAS h = HAS.getInstance();

		// check that an album has been selected
		if (row > -1) {
			error_MP = "";
			albumListSortByArtist();
			if (sortedByArtist == false) {
				albumListSortByAlbum();
			}

			if (selectedLocation > (h.getRooms().size() - 1)) {
				try {

					hc.playRoomGroup(playAlbum, h.getRoomGroup(selectedLocation - h.getRooms().size() - 1));
					nowPlaying = playAlbum.getName();

				} catch (InvalidInputException e) {
					error_MP = e.getMessage();
				}
			} else {
				try {
					hc.playSingleRoom(playAlbum, h.getRoom(selectedLocation));
					nowPlaying = playAlbum.getName();
				} catch (InvalidInputException e) {
					error_MP = e.getMessage();
				}
			}
		} else {
			error_MP = "Please select an row to play an album";
		}
		refreshData();
	}

	// change volume in selected location
	private void volumeChange() {
		error_CP = "";
		HASController hc = new HASController();
		HAS h = HAS.getInstance();
		if (selectedctrlocation > (h.getRooms().size() - 1)) {
			try {
				hc.setRoomGroupVolumeLevel(h.getRoomGroup(selectedctrlocation - h.getRooms().size()), sliderVol);

			} catch (InvalidInputException e) {
				error_CP = e.getMessage();
			}
		} else {
			try {
				hc.setRoomVolumeLevel(h.getRoom(selectedctrlocation), sliderVol);
			} catch (InvalidInputException e) {
				error_CP = e.getMessage();
			}
		}
		refreshVol();
	}

	// change volume in all locations
	private void mVolumeChange() {
		error_CP = "";
		HAS h = HAS.getInstance();
		for (int i = 0; i < h.getRooms().size() + h.getRoomGroups().size(); i++) {
			HASController hc = new HASController();
			if (i > (h.getRooms().size() - 1)) {
				try {
					hc.setRoomGroupVolumeLevel(h.getRoomGroup(i - h.getRooms().size()), mSliderVol);
				} catch (InvalidInputException e) {
					error_CP = e.getMessage();
				}
			} else {
				try {
					hc.setRoomVolumeLevel(h.getRoom(i), mSliderVol);
				} catch (InvalidInputException e) {
					error_CP = e.getMessage();
				}
			}
		}
		refreshVol();
	}

	// mute in selected location
	private void muteChange() {
		error_CP = "";
		HASController hc = new HASController();
		HAS h = HAS.getInstance();

		if (selectedctrlocation > h.getRooms().size()) {
			try {
				hc.setRoomGroupMute(h.getRoomGroup(selectedctrlocation - h.getRooms().size()), muteBox);
			} catch (InvalidInputException e) {
				error_CP = e.getMessage();
			}
		} else {
			try {
				hc.setRoomMute(rooms.get(selectedctrlocation), muteBox);
			} catch (InvalidInputException e) {
				error_CP = e.getMessage();
			}
		}
		
		refreshMute();
	}

	// mute all locations
	private void mMuteChange() {
		error_CP = "";
		HASController hc = new HASController();
		HAS h = HAS.getInstance();

		for (int i = 0; i < h.getRooms().size() + h.getRoomGroups().size(); i++) {
			if (i > h.getRooms().size() - 1) {

				try {
					hc.setRoomGroupMute(h.getRoomGroup(i - h.getRooms().size()), mMuteBox);
				} catch (InvalidInputException e) {
					error_CP = e.getMessage();
				}
			} else {
				try {
					hc.setRoomMute(h.getRoom(i), mMuteBox);
				} catch (InvalidInputException e) {
					error_CP = e.getMessage();
				}
			}
		}
		refreshMute();
	}

	// play playlist in room or roomgroup
	private void playPlaylistActionPerformed(java.awt.event.ActionEvent evt) {
		error_PPP = "";
		HASController hc = new HASController();
		HAS h = HAS.getInstance();

		// check if a playlist is selected
		if (selectedPlaylist > -1) {
			if (selected3Location > (roomsString.size() - 1)) {
				try {
					hc.playRoomGroup(h.getPlaylist(selectedPlaylist), roomGroups.get(selected3Location - rooms.size()));
					nowPlaying = h.getPlaylist(selectedPlaylist).getName();
				} catch (InvalidInputException e) {
					error_PPP = e.getMessage();
				}
			} else {
				try {
					hc.playSingleRoom(h.getPlaylist(selectedPlaylist), rooms.get(selected3Location));
					nowPlaying = h.getPlaylist(selectedPlaylist).getName();
				} catch (InvalidInputException e) {
					error_PPP = e.getMessage();
				}
			}
		} else {
			error_PPP = "Please select a playlist";
		}
		refreshData();
	}

	// stop playable in the selected location
	private void stopActionPerformed(java.awt.event.ActionEvent evt) {
		error_CP = "";
		HASController hc = new HASController();
		HAS h = HAS.getInstance();

		if (selectedctrlocation > (h.getRooms().size() - 1)) {
			try {
				hc.playRoomGroup(null, h.getRoomGroup(selectedctrlocation - h.getRooms().size()));
				nowPlaying = "";
			} catch (InvalidInputException e) {
				error_CP = e.getMessage();
			}
		} else {
			try {
				hc.playSingleRoom(null, h.getRoom(selectedctrlocation));
				nowPlaying = "";
			} catch (InvalidInputException e) {
				error_CP = e.getMessage();
			}
		}
		setPlayinglbl();
	}

	// stop playable in all locations
	private void stopAllActionPerformed(java.awt.event.ActionEvent evt) {
		error_PPP = "";
		HASController hc = new HASController();
		for (int i = 0; i < rooms.size(); i++) {

			try {
				hc.playSingleRoom(null, rooms.get(i));
				nowPlaying = "";
			} catch (InvalidInputException e) {
				error_CP = e.getMessage();
			}
		}
		refreshData();
	}

	// update volume bar when the location is changed
	private void refreshVol() {
		HAS h = HAS.getInstance();
		// check if it is a room or roomGroup
		if (selectedctrlocation > h.getRooms().size() - 1) {
			Integer sliderVols = h.getRoomGroup(selectedctrlocation - rooms.size()).getVolume();
			sliderVolume.setValue(sliderVols);
		} else if (selectedctrlocation > -1) {
			Integer sliderVols = h.getRoom(selectedctrlocation).getVolume();
			sliderVolume.setValue(sliderVols);
		}
	}

	// update mute box for current room or roomGroup
	private void refreshMute() {
		HAS h = HAS.getInstance();

		if (selectedctrlocation > h.getRooms().size() - 1) {
			muteBox = h.getRoomGroup(selectedctrlocation - rooms.size()).getMute();
			chckbxMute.setSelected(muteBox);
		} else if (selectedctrlocation > -1) {
			muteBox = h.getRoom(selectedctrlocation).getMute();
			chckbxMute.setSelected(muteBox);
		}

	}

	// update the now playing label to show the currently playing playable
	private void setPlayinglbl() {
		HAS h = HAS.getInstance();
		if (selectedctrlocation > h.getRooms().size() - 1) {
			if (h.getRoomGroup(selectedctrlocation - h.getRooms().size()).hasPlayable()) {
				nowPlaying = h.getRoomGroup(selectedctrlocation - h.getRooms().size()).getPlayable().getName();
				lblPlaying.setText(nowPlaying);
			} else {
				lblPlaying.setText("");
			}
		} else if (selectedctrlocation > -1) {
			if (h.getRoom(selectedctrlocation).hasPlayable()) {
				nowPlaying = h.getRoom(selectedctrlocation).getPlayable().getName();
				lblPlaying.setText(nowPlaying);
			} else {
				lblPlaying.setText("");
			}
		}

	}

}
