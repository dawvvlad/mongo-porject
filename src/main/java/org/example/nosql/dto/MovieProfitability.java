package org.example.nosql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "profitability")
public class MovieProfitability {
    private String title;
    private double budget;
    private double gross;
    private double profit;
    private double profitability;
}
