package song.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import song.dto.MusicDTO;
import song.entity.Genre;
import song.entity.MusicType;
import song.entity.Top;
import song.repository.GenreRepository;
import song.repository.MusicTypeRepository;
import song.service.MusicService;
import song.service.TopService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Controller.class)
@AutoConfigureMockMvc

/*
 * 단위 테스트: 테스트는 MockMvc를 사용하여 컨트롤러 메서드를 개별적으로 테스트하며, 서비스 및 리포지토리의 행위를 모의화합니다.
입력 유효성 검사: 컨트롤러가 올바르지 않은 입력을 적절히 처리하고 400 Bad Request를 반환하는지 확인합니다.
예외 처리: 글로벌 예외 처리기가 의도적으로 예외를 throw하고 해당 예외에 대한 올바른 HTTP 상태 및 오류 메시지를 반환하는지 확인합니다.
모의화 전략
서비스 모의화: 테스트에서는 @MockBean을 사용하여 서비스 (TopService, MusicService) 및 리포지토리 (MusicTypeRepository, GenreRepository)를 모의화합니다.
데이터 모의화: 모의 데이터는 기본적으로 서비스 및 리포지토리의 동작을 시뮬레이션하기 위해 사용됩니다.
테스트 실행
테스트는 Spring MVC Test 프레임워크를 사용하여 실행되며, 컨트롤러 계층을 독립적으로 테스트할 수 있습니다.
MockMvc를 사용하여 HTTP 요청을 수행하고 응답을 확인합니다.
*/
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopService topService;

    @MockBean
    private MusicService musicService;

    @MockBean
    private MusicTypeRepository musicTypeRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testWithAdminUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/music/top")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // 테스트 1: getTop - /api/music/top 엔드포인트가 Top 엔터티 목록을 올바르게 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getTop_shouldReturnListOfTop() throws Exception {
        // Arrange
        List<Top> topList = Arrays.asList(
                new Top("Song 1", "img1", "Artist 1", "Album 1"),
                new Top("Song 2", "img2", "Artist 2", "Album 2")
        );
        when(topService.getAll()).thenReturn(topList);

        // Act & Assert
        mockMvc.perform(get("/api/music/top"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Song 1"))
                .andExpect(jsonPath("$[1].title").value("Song 2"));
    }

    // 테스트 2: getTopId_withValidId - /api/music/top/{id} 엔드포인트가 유효한 ID에 대한 올바른 Top 엔터티를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getTopId_withValidId_shouldReturnTop() throws Exception {
        // Arrange
        String validId = "1";  // 문자열로 설정
        List<Top> topList = Arrays.asList(
                new Top("Song 1", "img1", "Artist 1", "Album 1"),
                new Top("Song 2", "img2", "Artist 2", "Album 2")
        );
        when(topService.getId(Integer.parseInt(validId))).thenReturn(topList);

        // Act & Assert
        mockMvc.perform(get("/api/music/top/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Song 1"))
                .andExpect(jsonPath("$[1].title").value("Song 2"))
                .andReturn();
    }

    // 테스트 3: getTopId_withInvalidId - /api/music/top/{id} 엔드포인트가 유효하지 않은 ID에 대해 400 Bad Request를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getTopId_withInvalidId_shouldReturnBadRequest() throws Exception {
        // Arrange
        String invalidId = "top";  // 숫자가 아닌 문자열로 설정

        // Act & Assert
        mockMvc.perform(get("/api/music/top/{id}", invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("순위 값은 숫자이어야 합니다."))  // 변경된 예외 메시지
                .andReturn();
    }

    // 테스트 4: genreMusic_withValidGenreId - /api/music/genre/{genre_id} 엔드포인트가 유효한 장르 ID에 대한 MusicDTO 엔터티 목록을 반환하는지 확인합니다.
    @Test
    void genreMusic_withValidGenreId_shouldReturnListOfMusicDTO() throws Exception {
        // Arrange
        String validGenreId = "1";  // 문자열로 설정
        List<MusicDTO> musicList = Arrays.asList(
                new MusicDTO("Song 1", "img1", "Artist 1", "Album 1"),
                new MusicDTO("Song 2", "img2", "Artist 2", "Album 2")
        );
        when(musicService.getGenreMusic(Integer.parseInt(validGenreId))).thenReturn(musicList);

        // Act & Assert
        mockMvc.perform(get("/api/music/genre/{genre_id}", validGenreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Song 1"))
                .andExpect(jsonPath("$[1].title").value("Song 2"));
    }


    // 테스트 5: genreMusic_withInvalidGenreId - /api/music/genre/{genre_id} 엔드포인트가 유효하지 않은 장르 ID에 대해 400 Bad Request를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void genreMusic_withInvalidGenreId_shouldReturnBadRequest() throws Exception {
        // Arrange
        int invalidGenreId = 0;

        // Act & Assert
        mockMvc.perform(get("/api/music/genre/{genre_id}", invalidGenreId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("유효하지 않은 genre 값입니다."));
    }

    // 테스트 6: typeMusic_withValidTypeId - /api/music/type/{type_id} 엔드포인트가 유효한 유형 ID에 대한 MusicDTO 엔터티 목록을 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void typeMusic_withValidTypeId_shouldReturnListOfMusicDTO() throws Exception {
        // Arrange
    	int validTypeId = 1;
        List<MusicDTO> musicList = Arrays.asList(
                new MusicDTO("Song 1", "img1", "Artist 1", "Album 1"),
                new MusicDTO("Song 2", "img2", "Artist 2", "Album 2")
        );
        when(musicService.getTypeMusic(validTypeId)).thenReturn(musicList);

        // Act & Assert
        mockMvc.perform(get("/api/music/type/{type_id}", validTypeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Song 1"))
                .andExpect(jsonPath("$[1].title").value("Song 2"));
    }

    // 테스트 7: typeMusic_withInvalidTypeId - /api/music/type/{type_id} 엔드포인트가 유효하지 않은 유형 ID에 대해 400 Bad Request를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void typeMusic_withInvalidTypeId_shouldReturnBadRequest() throws Exception {
        // Arrange
        int invalidTypeId = 0;

        // Act & Assert
        mockMvc.perform(get("/api/music/type/{type_id}", invalidTypeId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("유효하지 않은 type 값입니다."));
    }

    // 테스트 8: typeByGenreMusic_withValidTypeIdAndGenreId - /api/music/{type_id}/{genre_id} 엔드포인트가 유효한 유형 및 장르 ID에 대한 MusicDTO 엔터티 목록을 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void typeByGenreMusic_withValidTypeIdAndGenreId_shouldReturnListOfMusicDTO() throws Exception {
        // Arrange
        int validTypeId = 1;
        int validGenreId = 1;
        List<MusicDTO> musicList = Arrays.asList(
                new MusicDTO("Song 1", "img1", "Artist 1", "Album 1"),
                new MusicDTO("Song 2", "img2", "Artist 2", "Album 2")
        );
        when(genreRepository.findDistinctGenreIdByTypeId(validTypeId)).thenReturn(Arrays.asList(validGenreId));
        when(musicService.getTypeByGenreMusic(validGenreId, validTypeId)).thenReturn(musicList);

        // Act & Assert
        mockMvc.perform(get("/api/music/{type_id}/{genre_id}", validTypeId, validGenreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Song 1"))
                .andExpect(jsonPath("$[1].title").value("Song 2"));
    }

    // 테스트 9: typeByGenreMusic_withInvalidTypeId - /api/music/{type_id}/{genre_id} 엔드포인트가 유효하지 않은 유형 ID에 대해 400 Bad Request를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void typeByGenreMusic_withInvalidTypeId_shouldReturnBadRequest() throws Exception {
        // Arrange
        int invalidTypeId = 0;
        int validGenreId = 1;

        // Act & Assert
        mockMvc.perform(get("/api/music/{type_id}/{genre_id}", invalidTypeId, validGenreId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("유효하지 않은 type 값입니다."));
    }

    // 테스트 10: typeByGenreMusic_withInvalidGenreId - /api/music/{type_id}/{genre_id} 엔드포인트가 유효하지 않은 장르 ID에 대해 400 Bad Request를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void typeByGenreMusic_withInvalidGenreId_shouldReturnBadRequest() throws Exception {
        // Arrange
        int validTypeId = 1;
        int invalidGenreId = 0;

        // Act & Assert
        mockMvc.perform(get("/api/music/{type_id}/{genre_id}", validTypeId, invalidGenreId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("유효하지 않은 genre 값입니다."));
    }

    // 테스트 11: typeByGenreMusic_withNonMatchingGenreIdAndTypeId - /api/music/{type_id}/{genre_id} 엔드포인트가 일치하지 않는 장르 및 유형 ID에 대해 400 Bad Request를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void typeByGenreMusic_withNonMatchingGenreIdAndTypeId_shouldReturnBadRequest() throws Exception {
        // Arrange
        int validTypeId = 1;
        int validGenreId = 2;
        when(genreRepository.findDistinctGenreIdByTypeId(validTypeId)).thenReturn(Arrays.asList(3, 4, 5));

        // Act & Assert
        mockMvc.perform(get("/api/music/{type_id}/{genre_id}", validTypeId, validGenreId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("유효하지 않은 genre 또는 type 값입니다."));
    }

    // 테스트 12: getType - /api/type 엔드포인트가 MusicType 엔터티 목록을 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getType_shouldReturnListOfMusicType() throws Exception {
        // Arrange
        List<MusicType> musicTypeList = Arrays.asList(
                new MusicType("Type 1"),
                new MusicType("Type 2")
        );
        when(musicTypeRepository.findAll()).thenReturn(musicTypeList);

        // Act & Assert
        mockMvc.perform(get("/api/type"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type").value("Type 1"))
                .andExpect(jsonPath("$[1].type").value("Type 2"));
    }

    // 테스트 13: getGenre - /api/genre 엔드포인트가 Genre 엔터티 목록을 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getGenre_shouldReturnListOfGenre() throws Exception {
        // Arrange
    	List<Genre> genreList = Arrays.stream(new Genre[] {
    		    new Genre("Genre 1"),
    		    new Genre("Genre 2")
    		}).collect(Collectors.toList());
        when(genreRepository.findAll()).thenReturn(genreList);

        // Act & Assert
        mockMvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].genre").value("Genre 1"))
                .andExpect(jsonPath("$[1].genre").value("Genre 2"));
    }

    // 테스트 14: typeCheck_withInvalidTypeId - /api/music/type/{type_id} 엔드포인트가 유효하지 않은 유형 ID에 대해 400 Bad Request를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void typeCheck_withInvalidTypeId_shouldReturnBadRequest() throws Exception {
        // Arrange
        int invalidTypeId = 0;

        // Act & Assert
        mockMvc.perform(get("/api/music/type/{type_id}", invalidTypeId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("유효하지 않은 type 값입니다."));
    }

    // 테스트 15: topCheck_withInvalidId - /api/music/top/{id} 엔드포인트가 유효하지 않은 ID에 대해 400 Bad Request를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void topCheck_withInvalidId_shouldReturnBadRequest() throws Exception {
        // Arrange
        int invalidId = 101;

        // Act & Assert
        mockMvc.perform(get("/api/music/top/{id}", invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("유효하지 않은 순위 값입니다."));
    }

    // 테스트 16: handleInvalidInput_withInvalidRequestException - /api/music/genre/{genre_id} 엔드포인트가 유효하지 않은 장르 ID에 대해 400 Bad Request 및 사용자 정의 오류 메시지를 반환하는지 확인합니다.
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void handleInvalidInput_withInvalidRequestException_shouldReturnBadRequest() throws Exception {
        // Arrange
        String errorMessage = "Test error message";
        when(genreRepository.findMaxGenreValue()).thenReturn(10);

        // Act & Assert
        mockMvc.perform(get("/api/music/genre/15"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("유효하지 않은 genre 값입니다."));
    }
}


