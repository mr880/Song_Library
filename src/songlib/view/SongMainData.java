/**
 * 10/4/2017
 * @authors Michael Russo and Mustafa Kasabchy
 */
package songlib.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class SongMainData 
{
    //main data object song info
    private String name;
    private final StringProperty songInfo;
    private final StringProperty artist;
    private final StringProperty album;
    private final IntegerProperty Year;

    /*
     * The 4 Methods below signify the valid ways in which info 
     * can be enetered into their fields. At the very least, 
     * a song and an artist are needed.
     */
    public SongMainData()
    {
        this(null, null);
        this.name = "";
    }

    public SongMainData(String songInfo, String artist)
    {
        this.songInfo = new SimpleStringProperty(songInfo);
        this.name = name;
        this.artist = new SimpleStringProperty(artist);

        this.album = new SimpleStringProperty();
        this.Year = new SimpleIntegerProperty();

    }

    public SongMainData(String songInfo, String artist, String album)
    {
        this.songInfo = new SimpleStringProperty(songInfo);
        this.artist = new SimpleStringProperty(artist);

        this.album = new SimpleStringProperty(album);
        this.Year = new SimpleIntegerProperty();

    }

    public SongMainData(String songInfo, String artist, String album, int Year)
    {
        this.songInfo = new SimpleStringProperty(songInfo);
        this.artist = new SimpleStringProperty(artist);

        this.album = new SimpleStringProperty(album);
        this.Year = new SimpleIntegerProperty(Year);

    }
    
    /*
     * Getters and Setters for the song info is below
     */
    public String getSongName()
    {
        return songInfo.get();
    }

    public StringProperty songProperty() 
    {
        return songInfo;
    }

    public void setSongName(String songInfo)
    {
        this.songInfo.set(songInfo);
    }

    public String getArtistName()
    {
        return artist.get();
    }

    public StringProperty artistProperty()
    {
        return artist;
    }

    public void setArtistName(String artist)
    {
        this.artist.set(artist);
    }

    public String getAlbumName()
    {
        return album.get();
    }

    public StringProperty albumProperty()
    {
        return album;
    }

    public void setAlbumName(String album)
    {
        this.album.set(album);
    }

    public int getSongYear() 
    {
        return Year.get();
    }

    public IntegerProperty yearProperty() 
    {
        return Year;
    }

    public void setSongYear(int Year)
    {
        this.Year.set(Year);
    }

    
}
