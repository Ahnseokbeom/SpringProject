package song.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "music")
@Data
public class Music {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String ranking;
	private String title;
	private String img;
	private String artist;
	private String album;

	@ManyToOne
	@JoinColumn(name="type_id")
	MusicType type;

	@ManyToOne
	@JoinColumn(name="genre_id")
	Genre genre;
}
