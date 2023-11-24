package br.com.omdb.series.domain.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.omdb.series.dto.EpisodeData;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Season(
        @JsonAlias("Season") Integer number,
        @JsonAlias("Episodes") List<EpisodeData> episodes) {

}
