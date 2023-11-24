package br.com.omdb.series.domain.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Episode(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer number,
        @JsonAlias("imdbRating") String rate,
        @JsonAlias("Released") String releaseDate) {

}
