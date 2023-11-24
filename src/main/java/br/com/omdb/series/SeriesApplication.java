package br.com.omdb.series;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.omdb.series.utils.Menu;

@SpringBootApplication
public class SeriesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SeriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Menu.display();
	}

}
