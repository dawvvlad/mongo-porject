package org.example.nosql.service;

import org.example.nosql.data.Mapper;
import org.example.nosql.dto.Movie;
import org.example.nosql.dto.MovieProfitability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MongoTemplate mongoTemplate;
    private final Mapper mapper;

    @Autowired
    public MovieService(MongoTemplate mongoTemplate, Mapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    public List<Movie> getAllMovies() {
        return mongoTemplate.findAll(Movie.class);
    }

    public void insertMovie(Movie movie) {
        mongoTemplate.insert(movie);
    }

    public void insertMovies(List<Movie> movies) {
        mongoTemplate.insertAll(movies);
    }

    public void insertMovieProfitability(List<MovieProfitability> movieProfitabilities) {
        mongoTemplate.insertAll(movieProfitabilities);
    }

    public void deleteMovie(Movie movie) {
        mongoTemplate.remove(movie);
    }

    public void updateMovie(Movie movie) {
        mongoTemplate.save(movie);
    }

}
