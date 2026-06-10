package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "country_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String countryName;
    private String countryIsoCode;
    private String capital;
    private String phoneCode;
    private String continent;

    @OneToMany(mappedBy = "countryInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Language> languages;
}



}
