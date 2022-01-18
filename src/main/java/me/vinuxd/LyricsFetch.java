package me.vinuxd;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

/*
* Lyrics Fetcher Online
*
* @author VINU
* @site https://vinuxd.me
*
*/

public class LyricsFetch extends JFrame implements ActionListener {

    // instance variables
    private final JTextField songName;
    private final JTextArea lyricsArea;
    private final JButton searchButton;
    private final JButton clearButton;
    private final JButton exitButton;
    private final JScrollPane scrollPane;
    public String title;
    public String artist;
    public String lyrics;

    // constructor
    public LyricsFetch() {
        super("Lyrics Fetcher");
        setSize(300, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setResizable(false);

        // create JLabel
        final JLabel heading = new JLabel("Lyrics Fetcher Online");
        add(heading);
        heading.setBounds(90, 5, 200, 40);

        // create text field
        songName = new JTextField("Enter Song Name", 20);
        add(songName);
        songName.setBounds(10, 40, 250, 30);

        // create search button
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        add(searchButton);
        searchButton.setBounds(10, 80, 80, 30);

        // create clear button
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        add(clearButton);
        clearButton.setBounds(100, 80, 80, 30);

        // create exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        add(exitButton);
        exitButton.setBounds(190, 80, 80, 30);

        // create text area
        lyricsArea = new JTextArea(20, 20);
        lyricsArea.setEditable(false);
        scrollPane = new JScrollPane(lyricsArea);
        add(scrollPane);
        scrollPane.setBounds(10, 120, 270, 280);

        // create JLabel
        final JLabel authorName = new JLabel("Made with \u2764 by VINU");
        add(authorName);
        authorName.setBounds(10, 410, 200, 40);
    }

    // action performed
    public void actionPerformed(final ActionEvent ae) {
        final String command = ae.getActionCommand();

        if (command.equals("Search")) {
            if (songName.getText().isEmpty() == true) {
                JOptionPane.showMessageDialog(this, "Please enter song name");
                System.out.println("Alerted user to enter song name.");
            } else {
                try {
                    System.out.println("Search Initialized.");
                    final String apiUrl = "http://jostapi.notavailable.live/lyrics/";
                    final String songNameString = songName.getText();
                    final String finalUrl = apiUrl + songNameString.replace(" ", "%20");
                    String finalLyrics = "";
                    final URL url = new URL(finalUrl);
                    System.out.println("Song Name: " + songNameString);
                    final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    System.out.println(
                            "Status: " + "" + connection.getResponseCode() + " " + connection.getResponseMessage());
                    final Scanner sc = new Scanner(url.openStream());
                    while (sc.hasNext()) {
                        finalLyrics += sc.nextLine();
                    }
                    sc.close();
                    final JSONArray jsonarray = new JSONArray("[" + finalLyrics + "]");
                    final JSONObject jsonobject = jsonarray.getJSONObject(0);
                    lyricsArea.setText(
                            "Title: " + jsonobject.getString("title") + "\n\n" + 
                            "Singer: " + jsonobject.getString("artist") + "\n\n" +
                            jsonobject.getString("lyrics").replace("EmbedShare URLCopyEmbedCopy", ""));
                    System.out.println("Success.\n");
                } catch (final Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                    lyricsArea.setText(
                            "No Lyrics Found!\n\n"
                                    + "- Check your internet connection\n"
                                    + "- Check if the song is correctly spelled\n"
                                    + "- Or It may be Internal Sever Error\n\n"
                                    + "Please try again later");
                }
            }
        }

        else if (command.equals("Clear"))

        {
            lyricsArea.setText("");
            songName.setText("");
            System.out.print("Cleared.\n");
        }

        else if (command.equals("Exit")) {
            System.out.println("Program Terminated by the user.\n");
            System.exit(0);
        }
    }

    // main method
    public static void main(final String[] args) {
        final LyricsFetch lyrics = new LyricsFetch();
        lyrics.setVisible(true);
    }
}
