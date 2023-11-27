package song.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetTop() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/music/top", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // 추가적인 테스트 수행 가능
    }

    @Test
    public void testGetTopId() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/music/top/1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // 추가적인 테스트 수행 가능
    }

    // 다른 테스트 메서드들 추가 가능

    @Test
    public void testInvalidTopId() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/music/top/101", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // 추가적인 테스트 수행 가능
    }
}

