package song.dto;

import lombok.Data;

@Data
public class MusicDTO {
    private String ranking;
    private String title;
    private String img;
    private String artist;
    private String album;

    public MusicDTO(String title,String img, String artist, String album) {
    	this.title = title;
    	this.img = img;
    	this.artist = artist;
    	this.album = album;
    }
    public MusicDTO() {}
}
