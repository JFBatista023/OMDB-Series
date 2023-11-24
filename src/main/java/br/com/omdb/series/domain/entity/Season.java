package br.com.omdb.series.domain.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Season(
        @JsonAlias("Season") Integer number,
        @JsonAlias("Episodes") List<Episode> episodes) {

}
