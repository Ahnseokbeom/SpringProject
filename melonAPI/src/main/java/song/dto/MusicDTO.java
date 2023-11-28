package song.dto;

import lombok.Data;

@Data
public class MusicDTO {
    private String ranking;
    private String title;
    private String img;
    private String artist;
    private String album;
    private String genre;

    public MusicDTO(String title,String img, String artist, String album,String genre) {
    	this.title = title;
    	this.img = img;
    	this.artist = artist;
    	this.album = album;
    	this.genre = genre;
    }
    public MusicDTO() {}
}
