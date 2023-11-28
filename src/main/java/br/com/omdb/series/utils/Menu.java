package br.com.omdb.series.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.omdb.series.domain.entity.Episode;
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("Enter a series name: ");
        var seriesName = scanner.nextLine();

        var json = apiConsumer.getData(ADDRESS + seriesName.replace(" ", "+") + APIKEY);
        Series series = converter.getData(json, Series.class);

        List<Season> seasons = new ArrayList<>();

        for (int i = 1; i <= series.totalSeasons(); i++) {
            json = apiConsumer.getData(ADDRESS + seriesName.replace(" ", "+") + "&season=" + i + APIKEY);
            Season season = converter.getData(json, Season.class);
            seasons.add(season);
        }

        List<Episode> episodes = seasons.stream()
                .flatMap(season -> season.episodes().stream()
                        .map(episodeData -> new Episode(season.number(), episodeData)))
                .collect(Collectors.toList());

        Optional<LocalDate> startDate = episodes.stream()
                .map(Episode::getReleaseDate)
                .min(LocalDate::compareTo);

        Optional<LocalDate> endDate = episodes.stream()
                .map(Episode::getReleaseDate)
                .max(LocalDate::compareTo);

        System.out.println("\nAbout:");
        System.out.println(
                series.title() + " started on " + startDate.get().format(dateFormatter) + " and ended on " + endDate.get().format(dateFormatter) + " with a "
                        + series.rate() + " rating and " + series.totalSeasons() + " seasons with " + episodes.size()
                        + " episodes in total.");

        System.out.println("\nSeasons: ");
        seasons.forEach(season -> System.out
                .println("Season " + season.number() + ", Number of Episodes: " + season.episodes().size()));

        System.out.println("\nTop 5 episodes:");
        episodes.stream()
                .sorted(Comparator.comparing(Episode::getRate).reversed())
                .limit(5)
                .forEach(episode -> System.out.println("Episode: " + episode.getTitle() + ", Season: " + episode.getSeason() + ", Rating: " + episode.getRate() + ", Release date: " + episode.getReleaseDate().format(dateFormatter)));
        
        System.out.print("\nFrom which year would you like to see the episodes? ");
        int year = scanner.nextInt();
        scanner.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);

        episodes.stream()
                .filter(episode -> episode.getReleaseDate() != null && episode.getReleaseDate().isAfter(searchDate))
                .forEach(episode -> System.out.println("Season: " + episode.getSeason() + ", Episode: " + episode.getTitle() + ", Release date: " + episode.getReleaseDate().format(dateFormatter)));

        System.out.print("\nWhat is the title of the episode you want to see information about? ");
        String title = scanner.nextLine();
        String titleCapitalized = title.substring(0, 1).toUpperCase() + title.substring(1);

        episodes.stream()
                .filter(episode -> episode.getTitle().toLowerCase().contains(title.toLowerCase()))
                .findFirst()
                .ifPresentOrElse(episode -> System.out.println("The first episode containing the word " + "'" + titleCapitalized + "'" + " in the title is:\n" + "Season: " + episode.getSeason() + ", Episode: " + episode.getTitle() + ", Release date: " + episode.getReleaseDate().format(dateFormatter)), () -> System.out.println("No episodes containing the word " + "'" + titleCapitalized + "'" + " were found."));
    }
}
