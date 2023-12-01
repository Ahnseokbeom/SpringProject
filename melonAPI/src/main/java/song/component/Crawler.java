package song.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import song.model.SongInfo;
import song.service.SongInfoService;

@Component
public class Crawler {

	@Autowired
	private SongInfoService songInfoService;

	private List<SongInfo> songInfoList = new ArrayList<>();

	@Scheduled(fixedDelay = 180000)
	public void crawlWebsite() {
        try {
            // 웹 페이지에서 데이터 크롤링 예시 (Jsoup 사용)
            Document doc = Jsoup.connect("https://www.tjmedia.com/tjsong/song_monthPopular.asp").get();
            Elements songE = doc.select("div#BoardType1 td");

            for (int i = 0;i<songE.size();i+=4) {
                SongInfo songInfo = new SongInfo();

                songInfo.setRank(Integer.parseInt(songE.get(i).text()));
                songInfo.setSongNum(songE.get(i+1).text());
                songInfo.setTitle(songE.get(i+2).text());
                songInfo.setArtist(songE.get(i+3).text());

                songInfoList.add(songInfo);
            }
            songInfoService.setSongInfoList(songInfoList);
        } catch (IOException e) {
            System.err.println("Error during crawling: " + e.getMessage());
        }
    }
}
