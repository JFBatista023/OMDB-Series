package br.com.omdb.series.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.omdb.series.domain.entity.Season;
import br.com.omdb.series.domain.entity.Series;
import br.com.omdb.series.service.APIConsumer;
import br.com.omdb.series.service.DataConverter;

public class Menu {

    private static Scanner scanner = new Scanner(System.in);

    private static APIConsumer apiConsumer = new APIConsumer();

    private static DataConverter converter = new DataConverter();

    private final static String ADDRESS = "https://www.omdbapi.com/?t=";

    private final static String APIKEY = "&apikey=6585022c";

    public static void display() {
        System.out.print("Enter a series name: ");
        var seriesName = scanner.nextLine();

        var json = apiConsumer.getData(ADDRESS + seriesName.replace(" ", "+") + APIKEY);
        Series series = converter.getData(json, Series.class);
        System.out.println(series);

        List<Season> seasons = new ArrayList<>();

        for (int i = 1; i <= series.totalSeasons(); i++) {
            json = apiConsumer.getData(ADDRESS + seriesName.replace(" ", "+") + "&season=" + i + APIKEY);
            Season season = converter.getData(json, Season.class);
            seasons.add(season);
        }

        seasons.forEach(System.out::println);

        seasons.forEach(season -> season.episodes().forEach(episode -> System.out.println(episode.title())));
    }
}
