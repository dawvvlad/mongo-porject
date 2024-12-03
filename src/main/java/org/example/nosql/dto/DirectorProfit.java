package org.example.nosql.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorProfit {
    private String director;
    private double maxProfit;
    private List<MovieProfit> movies;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MovieProfit {
        private String title;
        private double profit;
    }
}
