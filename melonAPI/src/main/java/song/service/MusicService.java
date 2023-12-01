package song.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import song.dto.MusicDTO;
import song.entity.Genre;
import song.entity.Music;
import song.entity.MusicType;
import song.exception.InvalidRequestException;
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

    public List<MusicDTO> getGenreMusic(int genreId) {
        List<Music> musicList = musicRepository.findByGenreId(genreId);
        List<MusicDTO> musicDTOList = new ArrayList<>();

        for (Music music : musicList) {
            MusicDTO musicDTO = new MusicDTO();
            musicDTO.setRanking(music.getRanking());
            musicDTO.setTitle(music.getTitle());
            musicDTO.setImg(music.getImg());
            musicDTO.setArtist(music.getArtist());
            musicDTO.setAlbum(music.getAlbum());

            // 장르 정보 추가
            Genre genre = genreRepository.findById(genreId).orElse(null);
            if (genre != null) {
                musicDTO.setGenre(genre.getGenre()); // 수정된 부분
            } else {
                musicDTO.setGenre("Unknown Genre");
            }

            musicDTOList.add(musicDTO);
        }

        return musicDTOList;
    }

    public List<MusicDTO> getTypeMusic(int genreId) {
        List<Music> musicList = musicRepository.findByGenreId(genreId);
        List<MusicDTO> musicDTOList = new ArrayList<>();

        for (Music music : musicList) {
            MusicDTO musicDTO = new MusicDTO();
            musicDTO.setRanking(music.getRanking());
            musicDTO.setTitle(music.getTitle());
            musicDTO.setImg(music.getImg());
            musicDTO.setArtist(music.getArtist());
            musicDTO.setAlbum(music.getAlbum());

            // 장르 정보 추가
            Genre genre = genreRepository.findById(genreId).orElse(null);
            if (genre != null) {
                musicDTO.setGenre(genre.getGenre()); // 수정된 부분
            } else {
                musicDTO.setGenre("Unknown Genre");
            }

            musicDTOList.add(musicDTO);
        }

        return musicDTOList;
    }

    public List<MusicDTO> getTypeByGenreMusic(int genre_id, int type_id) {
        MusicType type = musicTypeRepository.findById(type_id).orElse(null);
        Genre genre = genreRepository.findById(genre_id).orElse(null);

        if (type == null || genre == null) {
            throw new InvalidRequestException("유효하지 않은 type 또는 genre 값입니다.");
        }

        List<Music> musicList = musicRepository.findByTypeAndGenre(type, genre);
        return convertToDTOList(musicList, genre); // 수정된 부분
    }

    private List<MusicDTO> convertToDTOList(List<Music> musicList, Genre genre) {
        List<MusicDTO> musicDTOList = new ArrayList<>();

        for (Music music : musicList) {
            MusicDTO musicDTO = new MusicDTO();
            musicDTO.setRanking(music.getRanking());
            musicDTO.setTitle(music.getTitle());
            musicDTO.setImg(music.getImg());
            musicDTO.setArtist(music.getArtist());
            musicDTO.setAlbum(music.getAlbum());

            // genre 정보 추가
            if (genre != null) {
                musicDTO.setGenre(genre.getGenre());
            } else {
                musicDTO.setGenre("Unknown Genre");
            }

            musicDTOList.add(musicDTO);
        }

        return musicDTOList;
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 원하는 스레드 풀 크기로 설정
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("MyAsyncThread-");
        executor.initialize();
        return executor;
    }
}
