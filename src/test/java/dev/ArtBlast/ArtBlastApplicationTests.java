package dev.ArtBlast;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtBlastApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldRetrieveSinglePost(){
		ResponseEntity<String> response = restTemplate
			.withBasicAuth("roster", "abc123")
			.getForEntity("/posts/99", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int id = documentContext.read("$.id");
		assertThat(id).isEqualTo(99);

		String author = documentContext.read("$.author");
		assertThat(author).isEqualTo("roster");

		Boolean has_media = documentContext.read("$.has_media");
		assertThat(has_media).isEqualTo(false);

		String media_link = documentContext.read("$.media_link");
		assertThat(media_link).isEqualTo(null);

		String text_content = documentContext.read("$.text_content");
		assertThat(text_content).isEqualTo("abcdefg");
	}

	@Test
	@DirtiesContext
	void shouldCreateNewPost(){
		Post post = new Post(null, "roster", true, "a", "a");
		ResponseEntity<Void> response = restTemplate
			.withBasicAuth("roster", "abc123")
			.postForEntity("/posts/createNew", post, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}



	@Test
	void contextLoads() {
	}

}
