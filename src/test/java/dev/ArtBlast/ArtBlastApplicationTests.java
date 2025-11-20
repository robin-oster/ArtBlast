package dev.ArtBlast;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtBlastApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void createNewPost(){
		Post post = new Post(null, "test", true, "a", "a");
		ResponseEntity<Void> response = restTemplate
			.withBasicAuth("roster", "abc123")
			.postForEntity("/posts/createNew", post, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void contextLoads() {
	}

}
