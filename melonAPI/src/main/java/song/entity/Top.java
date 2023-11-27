package song.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="top100")
@Data
public class Top {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;
	private String img;
	private String artist;
	private String album;

	public Top(String title, String img, String artist, String album) {
		this.title = title;
    	this.img = img;
    	this.artist = artist;
    	this.album = album;
	}
	public Top() {}
}
