package com.example.demo.service;

import com.example.demo.models.CountryInfo;

import java.util.List;

public interface CountryService {

    CountryInfo createCountry(String countryName);
    List<CountryInfo> getAllCountryInfo();
    CountryInfo getCountryById(Long id);
    CountryInfo updateCountry(Long id, String countryName);
    void deleteCountry(Long id);
}
