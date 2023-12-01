package song.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
@Data
@JsonPropertyOrder({"rank", "songNum", "title", "artist"})
public class SongInfo {
	private int rank;
    private String songNum;
    private String title;
    private String artist;
}
