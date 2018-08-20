/**
 * 10/4/2017
 * @authors Michael Russo and Mustafa Kasabchy
 */
package songlib.view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SecondWindowController 
{
    //create textfields for songs, artists, albums and years
    @FXML
    private TextField songField;
    @FXML
    private TextField artistField;
    @FXML
    private TextField albumField;
    @FXML
    private TextField yearField;

    //Create window for adding songs
    private Stage secondWin;
    //Create main data object
    private SongMainData songInfo;
    //Create switch for done button
    private boolean done = false;
    
    
    @FXML
    private void initialize() {
    }

   //Dialog Stage Setter
    public void setDialogStage(Stage secondWin) {
        this.secondWin = secondWin;
    }

    //Settings object song setter
    public void setSong(SongMainData songInfo) {
        this.songInfo = songInfo;

        songField.setText(songInfo.getSongName());
        artistField.setText(songInfo.getArtistName());
        albumField.setText(songInfo.getAlbumName());
        yearField.setText(Integer.toString(songInfo.getSongYear()));
    }

    //doneButton getter
    public boolean done() {
        return done;
    }

    //Enter button functionality
    @FXML
    private void enterPushButton() 
    {
        //if valid input, set song info to our Field data
        if (checkInputData()) 
        {
            songInfo.setSongName(songField.getText());
            songInfo.setArtistName(artistField.getText());
            songInfo.setAlbumName(albumField.getText());
            songInfo.setSongYear(Integer.parseInt(yearField.getText()));
            //set doneButton to true and close window
            done = true;
            secondWin.close();
        }
    }

    //Cancel button Functionality
    @FXML
    private void cancelPushButton() {
        secondWin.close();
    }

    //Method that checks for input validity
    private boolean checkInputData() {
        String errorMessage = "";
        //checks if our fields were populted, if not, populate the error message
        if (songField.getText() == null || songField.getText().length() == 0) {
            errorMessage += "No valid songInfo name!\n"; 
        }
        if (artistField.getText() == null || artistField.getText().length() == 0) {
            errorMessage += "No valid artist name!\n"; 
        }
        if (yearField.getText().length() == 0) {
            return true;
        } 
        else 
        {
            // try to parse the postal code into an int.
            try 
            {
                Integer.parseInt(yearField.getText());
            } 
            catch (NumberFormatException e) 
            {
                errorMessage += "Make Sure your input is an INTEGER\n"; 
            }
        }
         

        //if error message was NOT populated, return true
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(secondWin);
            alert.setTitle("Empty Fields 'Not Valid'");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
}
