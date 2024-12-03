package org.example.nosql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreProfitAnalysis {
    private String genre;
    private double totalProfit;
    private int count; // Количество фильмов в данном жанре
}

