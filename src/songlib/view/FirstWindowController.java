/**
 * 10/4/2017
 * @authors Michael Russo and Mustafa Kasabchy
 */
package songlib.view;

import java.io.IOException;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import songlib.SongLib;


public class FirstWindowController 
{
    //Create a table
    @FXML
    private TableView<SongMainData> songTable;
    //Create a song name column in table
    @FXML
    private
    TableColumn<SongMainData, String> songColumn;
    
    //Create labels
    @FXML
    private TableColumn<SongMainData, String> artistColumn;
    
    //Create a table
    @FXML
    private Label songLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label albumLabel;
    @FXML
    private Label yearLabel;
    
    //create a song library
    private SongLib songLib;
   
        
    @FXML
    private void initialize()
    {
        songColumn.setCellValueFactory(songData -> songData.getValue().songProperty());
        artistColumn.setCellValueFactory(songData -> songData.getValue().artistProperty());


        showSongDetails(null);
        songTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue, newValue) -> 
        {
            showSongDetails(newValue);
        });

    }
    
    //setter for song library
    public void setSongLib(SongLib songLib) 
    {
        this.songLib = songLib;
        songTable.setItems(SongLib.getSongData());
    }

    //shows song details window 
    private void showSongDetails(SongMainData songMainData)
    {
        //set our labels to the info from songMainData
        if(songMainData != null)
        {
            songLabel.setText(songMainData.getSongName());
            artistLabel.setText(songMainData.getArtistName());
            albumLabel.setText(songMainData.getAlbumName());
            yearLabel.setText(Integer.toString(songMainData.getSongYear()));
        }
        //set our labels to empty if settings setting is empty
        else
        {
            songLabel.setText("");
            artistLabel.setText("");
            albumLabel.setText("");
            yearLabel.setText("");
        }
    }

    //Class designed to compare two song inputs in order to sort them in alphabetical order
    class Sorter implements Comparator<SongMainData>
	{
            //compares strings, if equal return 0, returns an integer
	    @Override
	    public int compare(SongMainData song1, SongMainData song2) 
	    {
	        int value = song1.getSongName().compareTo(song2.getSongName());
	        return value;
	            
	    }
        
     }
    
    //Add button functionality
    @FXML
    private void addPushButton() throws IOException
    {
        //create a temporary Settings object
        SongMainData tempSong = new SongMainData();
        //create a switch that is set to true after calling displaySecondWin successfully 
        boolean doneButton = SongLib.displaySecondWin(tempSong);
        //if successful create observablelist with main's song data
        if (doneButton) 
        {
            ObservableList<SongMainData> y = SongLib.getSongData();
            //Checks for duplicate songs upon adding a new song
            for(int x=0; x<y.size();  x++)
            {
                if(y.get(x).getSongName().trim().equals(tempSong.getSongName().trim()) && y.get(x).getArtistName().trim().equals(tempSong.getArtistName().trim()))
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(SongLib.getMainStage());
                    alert.setTitle("Duplicate");
                    alert.setContentText("Check your data this song already exists!!!");

                    alert.showAndWait();
                    return;
                }
            }

            int tempSongPlace = 0;
            SongLib.getSongData().add(tempSong);
            FXCollections.sort(SongLib.getSongData(), new Sorter());
            SongLib.updateList(SongLib.getSongData());

            for(int i=0; i< SongLib.getSongData().size(); i++)
            {
                if(SongLib.getSongData().get(i).getSongName().equals(tempSong.getSongName()))
                {
                    tempSongPlace = i;
                }
            }
            songTable.getSelectionModel().select(tempSongPlace);
        }
    }
    
    //Edit Button Functionality
    @FXML
    private void editPushButton() throws IOException  
    {
        //creates a SongMainData object for the currently selected song
        SongMainData selectedSong = songTable.getSelectionModel().getSelectedItem();
        if (selectedSong != null) 
        {
            //create a switch that is set to true after calling displaySecondWin successfully
            boolean doneButton = SongLib.displaySecondWin(selectedSong);
            //if successful show song details of the selected song
            if (doneButton) 
            {
                    showSongDetails(selectedSong);
                    //sort the songs and update the file accordingly after edit
                    FXCollections.sort(SongLib.getSongData(), new Sorter());
                    SongLib.updateList(SongLib.getSongData());
            }
        } 
        
        else 
        {           
            // Nothing selected, alert user
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(SongLib.getMainStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Song Selected");
            alert.setContentText("Make sure you select a song!");
            alert.showAndWait();
        }
    }

    //Delete Button Functionality
    @FXML
    private void deletePushButton() throws IOException 
    {   
        //grabs index of selected song
        int selectedIndex = songTable.getSelectionModel().getSelectedIndex();
        //checks validity of the song index then removes and updates
        if (selectedIndex >= 0) 
        {
            songTable.getItems().remove(selectedIndex);
            FXCollections.sort(SongLib.getSongData(), new Sorter());
            SongLib.updateList(SongLib.getSongData());
        } 
        else 
        {
            // Nothing selected, alert user.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(SongLib.getMainStage());
            alert.setTitle("Nothing been selected");
            alert.setContentText("Please Select a Song");
            alert.showAndWait();
        }
    }
    
}
