package ca.mcgill.ecse321.HAS.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Date;
import java.text.NumberFormat;
//import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;

/*
//TODO Update view for and implement checks: 
 * Add playlist
 * Add song to playlist
 * Add room
 * Create RoomGroup
 * add Room to RoomGroup
 * setVolume
 * setMute - CHECK BOX 
 * 
 * Also need to do the actual view of the sorted Artists and Albums
 * And the view for seeing the songs within the album
 * 
 * And "Song is playing" message should be incorporated
*/
public class HASPage extends JFrame
{
	private static final long serialVersionUID = -8062635784771606869L;

	// UI elements
	private JLabel errorMessage;

	// for add artist
	private JLabel artistLabel;
	private JLabel artistNameLabel;
	private JTextField artistNameTextField;
	private JButton addArtistButton;

	// for add album
	private JLabel albumLabel;
	private JLabel albumNameLabel;
	private JTextField albumNameTextField;
	private JLabel albumGenreLabel;
	private JTextField albumGenreTextField;
	private JLabel albumReleaseDateLabel;
	private JLabel albumArtistLabel;
	private JComboBox<String> artistList;
	private JButton addAlbumButton;
	// find a better way to implement the date
	private JDatePickerImpl albumReleaseDatePicker;

	// for add song
	private JLabel songLabel;
	private JLabel songAlbumLabel;
	private JComboBox<String> albumList;
	private JLabel songNameLabel;
	private JTextField songNameTextField;
	private JLabel songDurationLabel;
	private JLabel songPositionLabel;
	private JButton addSongButton;

	// data elements;
	private String error = null;
	private Integer selectedArtist = -1;
	private HashMap<Integer, Artist> artists;
	private Integer selectedAlbum = -1;
	private HashMap<Integer, Album> albums;

	NumberFormat amountFormat = NumberFormat.getNumberInstance();
	private JSpinner songDurationSpinner;
	private JSpinner songPositionSpinner;
	private JSeparator separator;
	private JSeparator separator_1;

	public HASPage()
	{
		initComponents();
		refreshData();
	}

	private void initComponents()
	{

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("HAS - Home Audio System");

		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		// refreshData(); TODO COME FIX THIS
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]
		{ 0, 178, 202, 114, 0 };
		gridBagLayout.rowHeights = new int[]
		{ 1, 16, 29, 0, 16, 26, 26, 29, 29, 0, 16, 26, 26, 26, 29, 0, 0 };
		gridBagLayout.columnWeights = new double[]
		{ 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[]
		{ 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		GridBagConstraints gbc_errorMessage = new GridBagConstraints();
		gbc_errorMessage.anchor = GridBagConstraints.NORTHWEST;
		gbc_errorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_errorMessage.gridx = 1;
		gbc_errorMessage.gridy = 0;
		getContentPane().add(errorMessage, gbc_errorMessage);
		artistLabel = new JLabel();

		artistLabel.setText("Artist");
		GridBagConstraints gbc_artistLabel = new GridBagConstraints();
		gbc_artistLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_artistLabel.insets = new Insets(0, 0, 5, 5);
		gbc_artistLabel.gridx = 1;
		gbc_artistLabel.gridy = 1;
		getContentPane().add(artistLabel, gbc_artistLabel);
		artistNameLabel = new JLabel();
		artistNameLabel.setText("Artist Name: ");
		GridBagConstraints gbc_artistNameLabel = new GridBagConstraints();
		gbc_artistNameLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_artistNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_artistNameLabel.gridx = 1;
		gbc_artistNameLabel.gridy = 2;
		getContentPane().add(artistNameLabel, gbc_artistNameLabel);

		// elements for add Artist
		artistNameTextField = new JTextField();
		GridBagConstraints gbc_artistNameTextField = new GridBagConstraints();
		gbc_artistNameTextField.anchor = GridBagConstraints.NORTH;
		gbc_artistNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_artistNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_artistNameTextField.gridx = 2;
		gbc_artistNameTextField.gridy = 2;
		getContentPane().add(artistNameTextField, gbc_artistNameTextField);
		addArtistButton = new JButton();
		addArtistButton.setText("Add Artist");
		addArtistButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addArtistButtonActionPerformed(evt);
			}
		});
		GridBagConstraints gbc_addArtistButton = new GridBagConstraints();
		gbc_addArtistButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_addArtistButton.insets = new Insets(0, 0, 5, 0);
		gbc_addArtistButton.gridx = 3;
		gbc_addArtistButton.gridy = 2;
		getContentPane().add(addArtistButton, gbc_addArtistButton);

		separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.gridwidth = 3;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 1;
		gbc_separator_1.gridy = 3;
		getContentPane().add(separator_1, gbc_separator_1);

		// elements for add Album
		albumLabel = new JLabel();

		albumLabel.setText("Album");
		GridBagConstraints gbc_albumLabel = new GridBagConstraints();
		gbc_albumLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_albumLabel.insets = new Insets(0, 0, 5, 5);
		gbc_albumLabel.gridx = 1;
		gbc_albumLabel.gridy = 4;
		getContentPane().add(albumLabel, gbc_albumLabel);
		albumNameLabel = new JLabel();
		albumNameLabel.setText("Album Name: ");
		GridBagConstraints gbc_albumNameLabel = new GridBagConstraints();
		gbc_albumNameLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_albumNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_albumNameLabel.gridx = 1;
		gbc_albumNameLabel.gridy = 5;
		getContentPane().add(albumNameLabel, gbc_albumNameLabel);
		albumNameTextField = new JTextField();
		GridBagConstraints gbc_albumNameTextField = new GridBagConstraints();
		gbc_albumNameTextField.anchor = GridBagConstraints.NORTH;
		gbc_albumNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_albumNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_albumNameTextField.gridx = 2;
		gbc_albumNameTextField.gridy = 5;
		getContentPane().add(albumNameTextField, gbc_albumNameTextField);
		albumGenreLabel = new JLabel();
		albumGenreLabel.setText("Album Genre: ");
		GridBagConstraints gbc_albumGenreLabel = new GridBagConstraints();
		gbc_albumGenreLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_albumGenreLabel.insets = new Insets(0, 0, 5, 5);
		gbc_albumGenreLabel.gridx = 1;
		gbc_albumGenreLabel.gridy = 6;
		getContentPane().add(albumGenreLabel, gbc_albumGenreLabel);
		albumGenreTextField = new JTextField();
		GridBagConstraints gbc_albumGenreTextField = new GridBagConstraints();
		gbc_albumGenreTextField.anchor = GridBagConstraints.NORTH;
		gbc_albumGenreTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_albumGenreTextField.insets = new Insets(0, 0, 5, 5);
		gbc_albumGenreTextField.gridx = 2;
		gbc_albumGenreTextField.gridy = 6;
		getContentPane().add(albumGenreTextField, gbc_albumGenreTextField);
		albumList = new JComboBox<String>();
		albumList.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedAlbum = cb.getSelectedIndex();
			}
		});

		// release date stuff (need to figure that out)
		albumReleaseDateLabel = new JLabel();
		albumReleaseDateLabel.setText("Album Release Date");
		GridBagConstraints gbc_albumReleaseDateLabel = new GridBagConstraints();
		gbc_albumReleaseDateLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_albumReleaseDateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_albumReleaseDateLabel.gridx = 1;
		gbc_albumReleaseDateLabel.gridy = 7;
		getContentPane().add(albumReleaseDateLabel, gbc_albumReleaseDateLabel);
		albumReleaseDatePicker = new JDatePickerImpl(datePanel,
				new DateLabelFormatter());
		GridBagConstraints gbc_albumReleaseDatePicker = new GridBagConstraints();
		gbc_albumReleaseDatePicker.anchor = GridBagConstraints.NORTHWEST;
		gbc_albumReleaseDatePicker.insets = new Insets(0, 0, 5, 5);
		gbc_albumReleaseDatePicker.gridx = 2;
		gbc_albumReleaseDatePicker.gridy = 7;
		getContentPane().add(albumReleaseDatePicker,
				gbc_albumReleaseDatePicker);

		albumArtistLabel = new JLabel();

		albumArtistLabel.setText("Album Artist");
		GridBagConstraints gbc_albumArtistLabel = new GridBagConstraints();
		gbc_albumArtistLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_albumArtistLabel.insets = new Insets(0, 0, 5, 5);
		gbc_albumArtistLabel.gridx = 1;
		gbc_albumArtistLabel.gridy = 8;
		getContentPane().add(albumArtistLabel, gbc_albumArtistLabel);

		addAlbumButton = new JButton();
		addAlbumButton.setText("Add Album");
		addAlbumButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addAlbumButtonActionPerformed(evt);
			}
		});
		artistList = new JComboBox<String>();
		artistList.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedArtist = cb.getSelectedIndex();
			}
		});
		GridBagConstraints gbc_artistList = new GridBagConstraints();
		gbc_artistList.anchor = GridBagConstraints.NORTH;
		gbc_artistList.fill = GridBagConstraints.HORIZONTAL;
		gbc_artistList.insets = new Insets(0, 0, 5, 5);
		gbc_artistList.gridx = 2;
		gbc_artistList.gridy = 8;
		getContentPane().add(artistList, gbc_artistList);
		GridBagConstraints gbc_addAlbumButton = new GridBagConstraints();
		gbc_addAlbumButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_addAlbumButton.insets = new Insets(0, 0, 5, 0);
		gbc_addAlbumButton.gridx = 3;
		gbc_addAlbumButton.gridy = 8;
		getContentPane().add(addAlbumButton, gbc_addAlbumButton);

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 3;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 9;
		getContentPane().add(separator, gbc_separator);

		// for adding a song
		songLabel = new JLabel();

		songLabel.setText("Song");
		GridBagConstraints gbc_songLabel = new GridBagConstraints();
		gbc_songLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_songLabel.insets = new Insets(0, 0, 5, 5);
		gbc_songLabel.gridx = 1;
		gbc_songLabel.gridy = 10;
		getContentPane().add(songLabel, gbc_songLabel);

		songNameLabel = new JLabel();
		songNameLabel.setText("Song Name: ");
		GridBagConstraints gbc_songNameLabel = new GridBagConstraints();
		gbc_songNameLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_songNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_songNameLabel.gridx = 1;
		gbc_songNameLabel.gridy = 11;
		getContentPane().add(songNameLabel, gbc_songNameLabel);
		songNameTextField = new JTextField();
		GridBagConstraints gbc_songNameTextField = new GridBagConstraints();
		gbc_songNameTextField.anchor = GridBagConstraints.NORTH;
		gbc_songNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_songNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_songNameTextField.gridx = 2;
		gbc_songNameTextField.gridy = 11;
		getContentPane().add(songNameTextField, gbc_songNameTextField);

		// PROBLEM MIGHT BE HERE
		songDurationLabel = new JLabel();
		songDurationLabel.setText("Song Duration (in seconds): ");
		GridBagConstraints gbc_songDurationLabel = new GridBagConstraints();
		gbc_songDurationLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_songDurationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_songDurationLabel.gridx = 1;
		gbc_songDurationLabel.gridy = 12;
		getContentPane().add(songDurationLabel, gbc_songDurationLabel);

		songDurationSpinner = new JSpinner();
		GridBagConstraints gbc_songDurationSpinner = new GridBagConstraints();
		gbc_songDurationSpinner.anchor = GridBagConstraints.NORTH;
		gbc_songDurationSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_songDurationSpinner.insets = new Insets(0, 0, 5, 5);
		gbc_songDurationSpinner.gridx = 2;
		gbc_songDurationSpinner.gridy = 12;
		getContentPane().add(songDurationSpinner, gbc_songDurationSpinner);
		songPositionLabel = new JLabel();
		songPositionLabel.setText("Song Position: ");
		GridBagConstraints gbc_songPositionLabel = new GridBagConstraints();
		gbc_songPositionLabel.anchor = GridBagConstraints.WEST;
		gbc_songPositionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_songPositionLabel.gridx = 1;
		gbc_songPositionLabel.gridy = 13;
		getContentPane().add(songPositionLabel, gbc_songPositionLabel);

		songPositionSpinner = new JSpinner();
		GridBagConstraints gbc_songPositionSpinner = new GridBagConstraints();
		gbc_songPositionSpinner.anchor = GridBagConstraints.NORTH;
		gbc_songPositionSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_songPositionSpinner.insets = new Insets(0, 0, 5, 5);
		gbc_songPositionSpinner.gridx = 2;
		gbc_songPositionSpinner.gridy = 13;
		getContentPane().add(songPositionSpinner, gbc_songPositionSpinner);
		songAlbumLabel = new JLabel();
		songAlbumLabel.setText("Album: ");
		GridBagConstraints gbc_songAlbumLabel = new GridBagConstraints();
		gbc_songAlbumLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_songAlbumLabel.insets = new Insets(0, 0, 5, 5);
		gbc_songAlbumLabel.gridx = 1;
		gbc_songAlbumLabel.gridy = 14;
		getContentPane().add(songAlbumLabel, gbc_songAlbumLabel);
		GridBagConstraints gbc_albumList = new GridBagConstraints();
		gbc_albumList.anchor = GridBagConstraints.NORTH;
		gbc_albumList.fill = GridBagConstraints.HORIZONTAL;
		gbc_albumList.insets = new Insets(0, 0, 5, 5);
		gbc_albumList.gridx = 2;
		gbc_albumList.gridy = 14;
		getContentPane().add(albumList, gbc_albumList);
		addSongButton = new JButton();
		addSongButton.setText("Add Song");
		addSongButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addSongButtonActionPerformed(evt);
			}
		});
		GridBagConstraints gbc_addSongButton = new GridBagConstraints();
		gbc_addSongButton.insets = new Insets(0, 0, 5, 0);
		gbc_addSongButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_addSongButton.gridx = 3;
		gbc_addSongButton.gridy = 14;
		getContentPane().add(addSongButton, gbc_addSongButton);

		pack();
	}

	// need to fix the refresh data for stuff not sure about
	private void refreshData()
	{
		HAS h = HAS.getInstance();

		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0)
		{
			// album list
			albums = new HashMap<Integer, Album>();
			albumList.removeAllItems();
			Iterator<Album> alIt = h.getAlbums().iterator();
			Integer index = 0;
			while (alIt.hasNext())
			{
				Album a = alIt.next();
				albums.put(index, a);
				albumList.addItem(a.getName());
				index++;
			}
			selectedAlbum = -1;
			albumList.setSelectedIndex(selectedAlbum);

			// artist list
			artists = new HashMap<Integer, Artist>();
			artistList.removeAllItems();
			Iterator<Artist> arIt = h.getArtists().iterator();
			index = 0;
			while (arIt.hasNext())
			{
				Artist ar = arIt.next();
				artists.put(index, ar);
				artistList.addItem(ar.getName());
				index++;
			}
			selectedArtist = -1;
			artistList.setSelectedIndex(selectedArtist);

			// artist name
			artistNameTextField.setText("");

			// album WE ARE MISSING THE OTHER STUFF IN HERE
			albumNameTextField.setText("");
			albumGenreTextField.setText("");

			// Song stuff and song duration/position
			songNameTextField.setText("");
			songDurationSpinner.setValue(0);
			songPositionSpinner.setValue(0);
			albumReleaseDatePicker.getModel().setValue(null);

		}

		pack();
	}

	private void addArtistButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		HASController hc = new HASController();
		error = null;
		try
		{
			hc.createArtist(artistNameTextField.getText());
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		refreshData();
	}

	// need to fix the release date problem
	private void addAlbumButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		error = "";
		if (selectedArtist < 0)
			error = error + "Album must have an artist!";
		if (albumReleaseDatePicker.getModel().getValue() == null)
		{
			error = error + "Album must have a release date! ";
			refreshData();
		}
		error = error.trim();
		if (error.length() == 0)
		{
			// call the method to create an album
			HASController hc = new HASController();
			Date selectedDate = (java.sql.Date) albumReleaseDatePicker
					.getModel().getValue();

			try
			{
				hc.createAlbum(albumNameTextField.getText(),
						albumGenreTextField.getText(), selectedDate,
						artists.get(selectedArtist));
			} catch (InvalidInputException e)
			{
				error = e.getMessage();
			}

			refreshData();
		}

	}

	private void addSongButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		error = "";
		if (selectedAlbum < 0)
			error = error + "Song must have an album! ";

		// TODO change the ranges of values for songDurationSpinner and
		// songPositionSpinner

		error = error.trim();
		if (error.length() == 0)
		{
			HASController hc = new HASController();
			try
			{
				// TODO:need to fix this for featured artist
				hc.addSongtoAlbum(albums.get(selectedAlbum),
						songNameTextField.getText(),
						(Integer) (songDurationSpinner.getModel().getValue()),
						(Integer) (songPositionSpinner.getModel().getValue()),
						null);
			} catch (InvalidInputException e)
			{
				error = e.getMessage();
			}
		}

		refreshData();
	}
}
