package org.example.nosql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrossScoreAnalysis {
    private double gross;
    private double averageScore;
    private int count; // Количество фильмов с таким бюджетом
}
