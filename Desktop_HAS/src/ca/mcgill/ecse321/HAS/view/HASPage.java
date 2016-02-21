package ca.mcgill.ecse321.HAS.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePickerImpl;

import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;

public class HASPage extends JFrame
{
	private static final long serialVersionUID = -8062635784771606869L;

	// UI elements
	private JLabel errorMessage;

	// for add artist
	private JLabel artistNameLabel;
	private JTextField artistNameTextField;
	private JButton addArtistButton;

	// for add album
	private JLabel albumNameLabel;
	private JTextField albumNameTextField;
	private JLabel albumGenreLabel;
	private JTextField albumGenreTextField;
	private JLabel albumReleaseDateLabel;
	private JLabel albumArtistLabel;
	private JComboBox<String> artistList;
	private JButton addAlbumButton;
	// find a better way to implement the date
	private JTextField albumReleaseDateTextField;

	// for add song
	private JLabel albumLabel;
	private JComboBox<String> albumList;
	private JLabel songNameLabel;
	private JTextField songNameTextField;
	private JLabel songDurationLabel;
	private JLabel songPositionLabel;
	private JButton addSongButton;
	// also find a way to make sure that it is an integer
	private int songDurationIntegerPicker;
	private int songPositionIntegerPicker;

	// data elements;
	private String error = null;
	private Integer selectedArtist = -1;
	private HashMap<Integer, Artist> artists;
	private Integer selectedAlbum = -1;
	private HashMap<Integer, Album> albums;

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

		// elements for add Album
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
		albumLabel = new JLabel();
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
		// iffy part still need the integer pickers
		songDurationLabel = new JLabel();
		songPositionLabel = new JLabel();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("HAS - Home Audio System");

		artistNameLabel.setText("Artist Name: ");
		addArtistButton.setText("Add Artist");
		addArtistButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addArtistButtonActionPerformed(evt);
			}
		});
		
		albumNameLabel.setText("Album Name: ");

	}

	/**/
}
