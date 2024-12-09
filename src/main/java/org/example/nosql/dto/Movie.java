package org.example.nosql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.beans.Transient;
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
    private Double score;
    private Double votes;
    private String director;
    private String writer;
    private String star;
    private String country;
    private Double budget;
    private Double gross;
    private String company;
    private Double runtime;

    public Double getProfitability() {
        if (budget != null && budget > 0 && gross != null) {
            return gross / budget;
        }
        return null;
    }
}
