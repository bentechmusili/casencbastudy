package com.example.demo.models.dto;

import com.example.demo.models.Language;
import lombok.Data;

import java.util.List;

@Data
public class FullCountryInfoResponse {
    private String isoCode;
    private String name;
    private String capitalCity;
    private String phoneCode;
    private String continentCode;
    private String currencyISOCode;
    private String countryFlag;
    private List<LanguageDto> languages;
}
