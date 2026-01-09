package dev.ArtBlast;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import dev.ArtBlast.Entities.Post;
import dev.ArtBlast.Entities.User;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtBlastApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldRetrieveAllPostsForUser(){
		ResponseEntity<String> response = restTemplate
			.withBasicAuth("roster", "abc123")
			.getForEntity("/posts/user", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void shouldRetrieveSinglePost(){
		ResponseEntity<String> response = restTemplate
			.withBasicAuth("roster", "abc123")
			.getForEntity("/posts/99", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int id = documentContext.read("$.id");
		assertThat(id).isEqualTo(99);

		String username = documentContext.read("$.username");
		assertThat(username).isEqualTo("roster");

		Boolean has_media = documentContext.read("$.hasMedia");
		assertThat(has_media).isEqualTo(false);

		String media_link = documentContext.read("$.mediaLink");
		assertThat(media_link).isEqualTo(null);

		String text_content = documentContext.read("$.textContent");
		assertThat(text_content).isEqualTo("abcdefg");
	}

	@Test
	@DirtiesContext
	void shouldCreateNewPost() throws ParseException{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Post post = new Post(null, "roster", true, "a", "a", timestamp);
		ResponseEntity<Void> response = restTemplate
			.withBasicAuth("roster", "abc123")
			.postForEntity("/posts/createNew", post, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	@DirtiesContext
	void shouldCreateNewUser() throws ParseException{
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
		User user = new User(null, "roster", "abc123", true, "a@b.com", "", "testing", authority);
		ResponseEntity<Void> response = restTemplate
			.postForEntity("/user/createNew", user, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	/*
	@Test
	@DirtiesContext
	void shouldAllowUserToDeleteOwnPost(){
		ResponseEntity<Void> response = restTemplate
			.withBasicAuth("roster", "abc123")
			.exchange("/posts/100", HttpMethod.DELETE, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
		*/

	@Test
	void contextLoads() {
	}

}
