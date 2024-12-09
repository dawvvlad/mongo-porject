package org.example.nosql.analysis;

import jakarta.annotation.PostConstruct;
import org.example.nosql.dto.Movie;
import org.example.nosql.dto.MovieProfitability;
import org.example.nosql.repository.AggregationMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Analysis implements Analizator {
    private final AggregationMovieRepository aggregationMovieRepository;

    @Autowired
    public Analysis(AggregationMovieRepository aggregationMovieRepository) {
        this.aggregationMovieRepository = aggregationMovieRepository;
    }

    public Movie getMinProfitMovie() {
        Movie min = aggregationMovieRepository.findLeastProfitableMovie();
        System.out.println(min.getTitle());

        return min;
    }

    public Movie getMaxProfitMovie() {
        Movie max = aggregationMovieRepository.findMostProfitableMovie();
        System.out.println(max.getTitle());
        return max;
    }

    public List<MovieProfitability> findAllMoviesProfitability() {
        List<MovieProfitability> moviesProfitability = aggregationMovieRepository.findAllMoviesProfitability();
        System.out.println(moviesProfitability);
        return moviesProfitability;
    }


    @PostConstruct
    @Override
    public void analyze() {
        List<Movie> movies = aggregationMovieRepository.findByBudgetGreaterThanAndGrossGreaterThanAndScoreGreaterThan(0, 0, 0);

        double totalProfitability = 0;
        double totalScore = 0;

        int validMoviesCount = 0;
        for (Movie movie : movies) {
            Double profitability = movie.getProfitability();
            Double score = movie.getScore();

            if (profitability != null && score != null) {
                totalProfitability += profitability;
                totalScore += score;
                validMoviesCount++;
            }
        }

        if (validMoviesCount > 0) {
            double avgProfitability = totalProfitability / validMoviesCount;
            double avgScore = totalScore / validMoviesCount;

            System.out.println("Средняя рентабельность: " + avgProfitability);
            System.out.println("Средняя оценка: " + avgScore);

            calculateCorrelation(movies);
        } else {
            System.out.println("Нет данных для анализа.");
        }
    }

    private void calculateCorrelation(List<Movie> movies) {
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;
        int n = 0;

        for (Movie movie : movies) {
            Double profitability = movie.getProfitability();
            Double score = movie.getScore();

            if (profitability != null && score != null) {
                sumX += profitability;
                sumY += score;
                sumXY += profitability * score;
                sumX2 += profitability * profitability;
                sumY2 += score * score;
                n++;
            }
        }

        if (n > 0) {
            double numerator = n * sumXY - sumX * sumY;
            double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

            if (denominator != 0) {
                double correlation = numerator / denominator;
                System.out.println("Корреляция между рентабельностью и оценкой: " + correlation);
            } else {
                System.out.println("Невозможно рассчитать корреляцию: деление на ноль.");
            }
        } else {
            System.out.println("Нет валидных данных для расчёта корреляции.");
        }
    }

}
