package org.example.nosql.repository;

import org.example.nosql.dto.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AggregationMovieRepository extends MongoRepository<Movie, Long> {

    /**
     * Фильм с максимальной рентабельностью
     */
    @Aggregation(pipeline = {
            "{ $match: { budget: { $gt: 0 }, gross: { $gt: 0 } } }",
            "{ $addFields: { profitability: { $divide: [ { $subtract: [ \"$gross\", \"$budget\" ] }, \"$budget\" ] } } }",
            "{ $sort: { profitability: -1 } }",
            "{ $limit: 1 }"
    })
    Movie findMostProfitableMovie();

    /**
     * Фильм с минимальной рентабельностью
     */
    @Aggregation(pipeline = {
            "{ $match: { budget: { $gt: 0 }, gross: { $gt: 0 } } }",
            "{ $addFields: { profitability: { $divide: [ { $subtract: [ \"$gross\", \"$budget\" ] }, \"$budget\" ] } } }",
            "{ $sort: { profitability: 1 } }",
            "{ $limit: 1 }"
    })
    Movie findLeastProfitableMovie();

    /**
     * Рентабельность каждого фильма
     */
    @Aggregation(pipeline = {
            "{ $match: { budget: { $gt: 0 }, gross: { $gt: 0 } } }",
            "{ $addFields: { " +
                    "    profit: { $subtract: [ \"$gross\", \"$budget\" ] }, " +
                    "    profitability: { $multiply: [ { $divide: [ { $subtract: [ \"$gross\", \"$budget\" ] }, \"$budget\" ] }, 100 ] } } }",
            "{ $project: { title: 1, budget: 1, gross: 1, profit: 1, profitability: 1, _id: 0 } }"
    })
    List<MovieProfitability> findAllMoviesProfitability();

    /**
     * Самые кассовые режиссеры
     */
    @Aggregation(pipeline = {
            "{ $match: { budget: { $gt: 0 }, gross: { $gt: 0 } } }",
            "{ $addFields: { profit: { $subtract: [ \"$gross\", \"$budget\" ] } } }",
            "{ $sort: { profit: -1 } }",
            "{ $group: { _id: \"$director\", maxProfit: { $first: \"$profit\" }, movies: { $push: { title: \"$title\", profit: \"$profit\" } } } }",
            "{ $sort: { maxProfit: -1 } }",
            "{ $project: { director: \"$_id\", maxProfit: 1, movies: 1, _id: 0 } }"
    })
    List<DirectorProfit> findTopDirectorsByProfit();


    /**
     * Связь между оценкой и сборами
     */
    @Aggregation(pipeline = {
            "{ $match: { gross: { $gt: 0 }, score: { $gt: 0 } } }",
            "{ $group: { _id: \"$gross\", averageScore: { $avg: \"$score\" }, count: { $sum: 1 } } }",
            "{ $sort: { _id: 1 } }",
            "{ $project: { gross: \"$_id\", averageScore: 1, count: 1, _id: 0 } }"
    })
    List<GrossScoreAnalysis> analyzeGrossVsScore();

    /**
     * Самые прибыльные жанры
     */
    @Aggregation(pipeline = {
            "{ $match: { budget: { $gt: 0 }, gross: { $gt: 0 } } }", // фильтруем фильмы, у которых есть и бюджет, и сборы
            "{ $project: { genre: 1, profit: { $subtract: [\"$gross\", \"$budget\"] } } }", // вычисляем прибыль для каждого фильма
            "{ $group: { _id: \"$genre\", totalProfit: { $sum: \"$profit\" }, count: { $sum: 1 } } }", // группируем по жанрам
            "{ $sort: { totalProfit: -1 } }", // сортируем по прибыли
            "{ $project: { genre: \"$_id\", totalProfit: 1, count: 1, _id: 0 } }" // форматируем результат
    })
    List<GenreProfitAnalysis> findMostProfitableGenres();

    List<Movie> findByBudgetGreaterThanAndScoreGreaterThan(double budget, double score);
    List<Movie> findByBudgetGreaterThanAndGrossGreaterThanAndScoreGreaterThan(double budget, double gross, double score);
}
