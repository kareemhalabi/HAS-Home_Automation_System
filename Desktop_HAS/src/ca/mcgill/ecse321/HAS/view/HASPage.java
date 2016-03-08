package ca.mcgill.ecse321.HAS.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;

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
	// also find a way to make sure that it is an integer
	private JTextField songDurationIntegerPicker;
	private JTextField songPositionIntegerPicker;

	// data elements;
	private String error = null;
	private Integer selectedArtist = -1;
	private HashMap<Integer, Artist> artists;
	private Integer selectedAlbum = -1;
	private HashMap<Integer, Album> albums;
	
	NumberFormat amountFormat = NumberFormat.getNumberInstance();

	public HASPage()
	{
		initComponents();
		refreshData();
	}

	private void initComponents()
	{
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		// elements for add Artist
		artistNameTextField = new JTextField();
		artistNameLabel = new JLabel();
		addArtistButton = new JButton();
		artistLabel = new JLabel();

		// elements for add Album
		albumLabel = new JLabel();
		albumNameLabel = new JLabel();
		albumNameTextField = new JTextField();
		albumGenreLabel = new JLabel();
		albumGenreTextField = new JTextField();

		// release date stuff (need to figure that out)
		albumReleaseDateLabel = new JLabel();

		albumArtistLabel = new JLabel();
		artistList = new JComboBox<String>(new String[0]);
		artistList.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedArtist = cb.getSelectedIndex();
			}
		});
		
	    
		addAlbumButton = new JButton();

		// for adding a song
		songLabel = new JLabel();
		songAlbumLabel = new JLabel();
		albumList = new JComboBox<String>(new String[0]);
		albumList.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedAlbum = cb.getSelectedIndex();
			}
		});

		songNameLabel = new JLabel();
		songNameTextField = new JTextField();
		addSongButton = new JButton();
		
		// PROBLEM MIGHT BE HERE
		songDurationLabel = new JLabel();
		songDurationIntegerPicker = new JTextField();
		songPositionLabel = new JLabel();
		songPositionIntegerPicker = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("HAS - Home Audio System");

		artistLabel.setText("Artist");
		artistNameLabel.setText("Artist Name: ");
		addArtistButton.setText("Add Artist");
		addArtistButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addArtistButtonActionPerformed(evt);
			}
		});

		albumLabel.setText("Album");
		albumNameLabel.setText("Album Name: ");
		albumGenreLabel.setText("Album Genre: ");
		albumReleaseDateLabel.setText("Album Release Date");
		
		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		albumReleaseDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		albumArtistLabel.setText("Album Artist");
		addAlbumButton.setText("Add Album");
		addAlbumButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addAlbumButtonActionPerformed(evt);
			}
		});

		songLabel.setText("Song");
		songNameLabel.setText("Song Name: ");
		songDurationLabel.setText("Song Duration (in seconds): ");
		songPositionLabel.setText("Song Position: ");
		songAlbumLabel.setText("Album: ");
		addSongButton.setText("Add Song");
		addSongButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addSongButtonActionPerformed(evt);
			}
		});
		
		refreshData();
		
		//layout
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
		layout.createParallelGroup().addComponent(errorMessage)
		.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(artistLabel)
						.addComponent(artistNameLabel)
						.addComponent(albumLabel)
						.addComponent(albumNameLabel)
						.addComponent(albumGenreLabel)
						.addComponent(albumReleaseDateLabel)
						.addComponent(albumArtistLabel)
						.addComponent(songLabel)
						.addComponent(songNameLabel)
						.addComponent(songDurationLabel)
						.addComponent(songPositionLabel)
						.addComponent(songAlbumLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(artistNameTextField, 200, 200, 400)
						.addComponent(albumNameTextField, 200, 200, 400)
						.addComponent(albumGenreTextField, 200, 200, 400)
						.addComponent(albumReleaseDatePicker)
						.addComponent(artistList)
						.addComponent(songNameTextField)
						.addComponent(songDurationIntegerPicker, 200, 200, 400)
						.addComponent(songPositionIntegerPicker, 200, 200, 400)
						.addComponent(albumList))
				.addGroup(layout.createParallelGroup()
						.addComponent(addArtistButton)
						.addComponent(addAlbumButton)
						.addComponent(addSongButton))
				));
		
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {artistNameLabel, songAlbumLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {artistNameTextField, albumList});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {addArtistButton, addSongButton});
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addComponent(artistLabel)
				.addGroup(layout.createParallelGroup()
						.addComponent(artistNameLabel)
						.addComponent(artistNameTextField)
						.addComponent(addArtistButton))
				.addComponent(albumLabel)
				.addGroup(layout.createParallelGroup()
						.addComponent(albumNameLabel)
						.addComponent(albumNameTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(albumGenreLabel)
						.addComponent(albumGenreTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(albumReleaseDateLabel)
						.addComponent(albumReleaseDatePicker))
				.addGroup(layout.createParallelGroup()
						.addComponent(albumArtistLabel)
						.addComponent(artistList)
						.addComponent(addAlbumButton))
				.addComponent(songLabel)
				.addGroup(layout.createParallelGroup()
						.addComponent(songNameLabel)
						.addComponent(songNameTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(songDurationLabel)
						.addComponent(songDurationIntegerPicker))
				.addGroup(layout.createParallelGroup()
						.addComponent(songPositionLabel)
						.addComponent(songPositionIntegerPicker))
				.addGroup(layout.createParallelGroup()
						.addComponent(songAlbumLabel)
						.addComponent(albumList)
						.addComponent(addSongButton)));
		

		pack();
	}
	
	//need to fix the refresh data for stuff not sure about
	private void refreshData()
	{
		HAS h = HAS.getInstance();
		
		//error
		errorMessage.setText(error);
		if(error == null || error.length() == 0)
		{
			//album list
			albums = new HashMap<Integer, Album>();
			albumList.removeAllItems();
			Iterator<Album> alIt = h.getAlbums().iterator();
			Integer index = 0;
			while(alIt.hasNext())
			{
				Album a = alIt.next();
				albums.put(index, a);
				albumList.addItem(a.getName());
				index++;
			}
			selectedAlbum = -1;
			albumList.setSelectedIndex(selectedAlbum);
			
			//artist list
			artists = new HashMap<Integer, Artist>();
			artistList.removeAllItems();
			Iterator<Artist> arIt = h.getArtists().iterator();
			index = 0;
			while(arIt.hasNext())
			{
				Artist ar = arIt.next();
				artists.put(index, ar);
				artistList.addItem(ar.getName());
				index++;
			}
			selectedArtist = -1;
			artistList.setSelectedIndex(selectedArtist);
			
			//artist name
			artistNameTextField.setText("");
			
			//album WE ARE MISSING THE OTHER STUFF IN HERE
			albumNameTextField.setText("");
			albumGenreTextField.setText("");
			
			//Song stuff and song duration/position
			songNameTextField.setText("");
			songPositionIntegerPicker.setText("");
			songDurationIntegerPicker.setText("");
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
		if(albumReleaseDatePicker.getModel().getValue() == null)
		{
			error = error + "Album must have a release date! ";
			refreshData();
		}
		error = error.trim();
		if (error.length() == 0)
		{
			// call the method to create an album
			HASController hc = new HASController();
			Date selectedDate = (java.sql.Date) albumReleaseDatePicker.getModel().getValue();

			try
			{
				hc.createAlbum(albumNameTextField.getText(), albumGenreTextField.getText(), selectedDate, artists.get(selectedArtist));
			} 
			catch (InvalidInputException e)
			{
				error = e.getMessage();
			}

			refreshData();
		}
		
	}

	private void addSongButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		error = "";
		if(selectedAlbum < 0)
			error = error + "Song must have an album!";
		if(songDurationIntegerPicker == null || songDurationIntegerPicker.getText().trim().length() == 0)
			error = error + "Song must have a duration! Please enter an integer! ";
		if(songPositionIntegerPicker == null || songPositionIntegerPicker.getText().trim().length() == 0)
			error = error + "Song must have a position! Please enter an integer! ";
		error = error.trim();
		if(error.length() == 0)
		{
			HASController hc = new HASController();
			try
			{
				//need to fix this
				hc.addSongtoAlbum(albums.get(selectedAlbum), songNameTextField.getText(), Integer.parseInt(songDurationIntegerPicker.getText()), (Integer.parseInt(songPositionIntegerPicker.getText())));
			}
			catch(InvalidInputException e)
			{
				error = e.getMessage();
			}
		}
		
		refreshData();
	}
}
