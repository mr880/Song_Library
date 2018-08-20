
/**
 * 10/4/2017
 * @authors Michael Russo and Mustafa Kasabchy
 */
package songlib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import songlib.view.FirstWindowController;
import songlib.view.SecondWindowController;
import songlib.view.SongMainData;

public class SongLib extends Application 
{
    //create stage, anchor pane and an observable list for our main window
    private static Stage mainStage;
    private static AnchorPane mainLayout;
    private static ObservableList<SongMainData> songData = FXCollections.observableArrayList();
    
    /*
     * Creates file for song list that reads through 
     * the file in order to fill our ObservableList
     * with the current data
     */
    public SongLib() throws IOException 
    {
        //Load in song list file
        File file = new File("SongData.java");
        //checks that it is not an empty file
        if(file != null)
        {
            try{
                BufferedReader read = new BufferedReader(new FileReader(file));
                String string;
                //reads first line of text from file
                string = read.readLine();
                /*iterates through the string from our file
                 * and addts to our observable list with an array created
                 * by ',' seperation
                 */
                while (string != null) 
                {
                    String[] arg = string.split(" , ");
                    songData.add(new SongMainData(arg[0], arg[1], arg[2], Integer.parseInt(arg[3])));
                    //reads in next line of text from file to str
                    string = read.readLine();
                }

                read.close();
            }
            catch (IOException e) 
            {
            }

        }
    }
    //Method to update file
    public static void updateList(ObservableList<SongMainData> songData) throws IOException
    {
        
        String data = "";
        SongMainData fileTmp = null;
        //Load in Song list file
        File file = new File("SongData.java");
        
        // if file doesnt exists, then create it
        if (!file.exists())
        {
            file.createNewFile();
        }

        FileWriter fileWrite = new FileWriter(file.getAbsoluteFile());
        BufferedWriter buffWrite = new BufferedWriter(fileWrite);
        //iterates through our list and writes to the file (update)
        for(int i = 0; i < songData.size(); i++)
        {
            fileTmp = songData.get(i);
            data = fileTmp.getSongName() + " , " + fileTmp.getArtistName() + " , " + fileTmp.getAlbumName() + " , " + fileTmp.getSongYear() + "\n";
            buffWrite.write(data);
            data = "";
        }

        buffWrite.close();

   
    }
    
    //song data getter
    public static ObservableList<SongMainData> getSongData()
    {
        return songData;
    }
    
    //Start method inherited from Applications
    @Override
    public void start(Stage mainStage) throws IOException
    {
        //sets main stage(window) and title of stage
        this.mainStage = mainStage;
        this.mainStage.setTitle("Song Library By Michael and Mustafa");
        displayFirstWin();
    }

    //Shows our first window window
    private void displayFirstWin() throws IOException
    {   //create an FXML loader
        FXMLLoader load = new FXMLLoader();
        //initializes it to our FirstWindow.fxml
        load.setLocation(SongLib.class.getResource("view/FirstWindow.fxml"));
        //add an anchorpane 
        mainLayout = load.load();
        //creates the new scene with our main layout
        Scene scene = new Scene(mainLayout);
        FirstWindowController controller = load.getController();
        controller.setSongLib(this);
        mainStage.setScene(scene);
        mainStage.show();
    }

    //Shows our Second window
    public static boolean displaySecondWin(SongMainData mainData) throws IOException
    {
        //create an FXML loader
        FXMLLoader load = new FXMLLoader();
        //initializes it to our SecondWindow.fxml
        load.setLocation(SongLib.class.getResource("view/SecondWindow.fxml"));
        //add an anchorpane
        AnchorPane window = (AnchorPane) load.load();
        //Create new window, sets title, modality and owner 
        Stage dataWin = new Stage();
        dataWin.setTitle("Add Data");
        dataWin.initModality(Modality.WINDOW_MODAL);
        dataWin.initOwner(mainStage);
        //create new scene with our anchorpane and add it to our stage
        Scene scene = new Scene(window);
        //add our scene to our window
        dataWin.setScene(scene);
        //Create a AddListController object
        SecondWindowController controller = load.getController();
        //set Dialog Stage = our dialog Stage 
        controller.setDialogStage(dataWin);
        //set Song = our method input
        controller.setSong(mainData);
        //show window
        dataWin.showAndWait();
        return controller.done();
    }

    //main stage getter
    public static Stage getMainStage()
    {
        return mainStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
