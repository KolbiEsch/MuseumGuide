package io.github.kolbiesch.museumguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "io.github.kolbiesch.museumguide.repository")
public class MuseumguideApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuseumguideApplication.class, args);
	}

}
