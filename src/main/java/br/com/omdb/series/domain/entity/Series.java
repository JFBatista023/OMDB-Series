package br.com.omdb.series.domain.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Series(
                @JsonAlias("Title") String title,
                @JsonAlias("totalSeasons") Integer totalSeasons,
                @JsonAlias("imdbRating") String rate) {

}
