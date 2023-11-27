package song.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import song.dto.MusicDTO;
import song.entity.Genre;
import song.entity.Music;
import song.entity.MusicType;
import song.repository.GenreRepository;
import song.repository.MusicRepository;
import song.repository.MusicTypeRepository;

@Service
public class MusicService {

    private final MusicRepository musicRepository;
    private final GenreRepository genreRepository;
    private final MusicTypeRepository musicTypeRepository;

    @Autowired
    public MusicService(MusicRepository musicRepository, GenreRepository genreRepository, MusicTypeRepository musicTypeRepository) {
        this.musicRepository = musicRepository;
        this.genreRepository = genreRepository;
        this.musicTypeRepository = musicTypeRepository;
    }

    public List<MusicDTO> getGenreMusic(int genre_id) {
        Genre genre = new Genre();
        genre.setId(genre_id);

        List<Music> musicList = musicRepository.findByGenre(genre);
        return convertToDTOList(musicList);
    }

    public List<MusicDTO> getTypeMusic(int type_id) {
        MusicType type = new MusicType();
        type.setId(type_id);

        List<Music> musicList = musicRepository.findByType(type);
        return convertToDTOList(musicList);
    }

    public List<MusicDTO> getTypeByGenreMusic(int genre_id, int type_id) {
        MusicType type = new MusicType();
        type.setId(type_id);

        Genre genre = new Genre();
        genre.setId(genre_id);

        List<Music> musicList = musicRepository.findByTypeAndGenre(type, genre);
        return convertToDTOList(musicList);
    }

    // 다른 서비스 메서드 추가 가능

    private List<MusicDTO> convertToDTOList(List<Music> musicList) {
        return musicList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MusicDTO convertToDTO(Music music) {
        MusicDTO musicDTO = new MusicDTO();
        musicDTO.setRanking(music.getRanking());
        musicDTO.setTitle(music.getTitle());
        musicDTO.setImg(music.getImg());
        musicDTO.setArtist(music.getArtist());
        musicDTO.setAlbum(music.getAlbum());
        // 필요한 정보 추가

        return musicDTO;
    }
}
