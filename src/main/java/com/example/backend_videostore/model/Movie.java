package com.example.backend_videostore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private String id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Short description is required")
    private String shortDescription;

    @NotNull(message = "Rental price is required")
    private Double rentalPrice;

    @NotNull(message = "Purchase price is required")
    private Double purchasePrice;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotBlank(message = "Type is required")
    private String type;
}


