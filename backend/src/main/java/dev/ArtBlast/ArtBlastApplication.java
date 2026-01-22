package dev.ArtBlast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("dev.*")
@ComponentScan(basePackages = { "dev.*" })
@EntityScan("dev.*")
public class ArtBlastApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtBlastApplication.class, args);
	}

}
