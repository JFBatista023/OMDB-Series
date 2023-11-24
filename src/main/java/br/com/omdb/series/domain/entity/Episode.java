package br.com.omdb.series.domain.entity;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import br.com.omdb.series.dto.EpisodeData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Episode {

    private Integer season;

    private String title;

    private Integer number;

    private Double rate;

    private LocalDate releaseDate;

    public Episode(Integer seasonNumber, EpisodeData episodeData) {
        this.season = seasonNumber;
        this.title = episodeData.title();
        this.number = episodeData.number();

        try {
            this.rate = Double.valueOf(episodeData.rate());
        } catch (NumberFormatException e) {
            this.rate = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(episodeData.releaseDate());
        } catch (DateTimeParseException e) {
            this.releaseDate = null;
        }
    }
}
