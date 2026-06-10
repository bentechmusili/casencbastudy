package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String languageName;
    private String isoCode;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryInfo countryInfo;
}
