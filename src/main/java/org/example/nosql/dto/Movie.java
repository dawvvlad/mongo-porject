package org.example.nosql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "movies")
public class Movie implements Serializable {
    private String title;
    private String rating;
    private String genre;
    private String year;
    private String released;
    private double score;
    private double votes;
    private String director;
    private String writer;
    private String star;
    private String country;
    private double budget;
    private double gross;
    private String company;
    private double runtime;
}
