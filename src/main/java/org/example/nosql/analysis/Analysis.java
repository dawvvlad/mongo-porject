package org.example.nosql.analysis;

import jakarta.annotation.PostConstruct;
import org.example.nosql.dto.Movie;
import org.example.nosql.dto.MovieProfitability;
import org.example.nosql.repository.AggregationMovieRepository;
import org.example.nosql.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Analysis {
    private final AggregationMovieRepository aggregationMovieRepository;
    private final MovieService movieService;

    @Autowired
    public Analysis(AggregationMovieRepository aggregationMovieRepository, MovieService movieService) {
        this.aggregationMovieRepository = aggregationMovieRepository;
        this.movieService = movieService;
    }

    @PostConstruct
    public Movie getMinProfitMovie() {
        Movie min = aggregationMovieRepository.findLeastProfitableMovie();
        System.out.println(min.getTitle());

        return min;
    }

    @PostConstruct
    public Movie getMaxProfitMovie() {
        Movie max = aggregationMovieRepository.findMostProfitableMovie();
        System.out.println(max.getTitle());
        return max;
    }

    @PostConstruct
    public List<MovieProfitability> findAllMoviesProfitability() {
        List<MovieProfitability> moviesProfitability = aggregationMovieRepository.findAllMoviesProfitability();
        System.out.println(moviesProfitability);
        return moviesProfitability;
    }
}
