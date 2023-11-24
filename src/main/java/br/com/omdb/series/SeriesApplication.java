package br.com.omdb.series;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.omdb.series.domain.entity.Episode;
import br.com.omdb.series.domain.entity.Series;
import br.com.omdb.series.service.APIConsumer;
import br.com.omdb.series.service.DataConverter;

@SpringBootApplication
public class SeriesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SeriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var apiConsumer = new APIConsumer();
		DataConverter converter = new DataConverter();

		var json = apiConsumer.getData("https://www.omdbapi.com/?t=breaking+bad&apikey=6585022c");
		Series series = converter.getData(json, Series.class);
		System.out.println(series);

		json = apiConsumer.getData("https://www.omdbapi.com/?t=breaking+bad&season=5&episode=14&apikey=6585022c");
		Episode episode = converter.getData(json, Episode.class);
		System.out.println(episode);
	}

}
