package song.service;

import java.util.List;

import org.springframework.stereotype.Service;

import song.model.SongInfo;

@Service
public class SongInfoService {
	private List<SongInfo> songInfoList;

    public void setSongInfoList(List<SongInfo> songInfoList) {
        this.songInfoList = songInfoList;
    }

    public List<SongInfo> getSongInfoList() {
        return songInfoList;
    }
}
