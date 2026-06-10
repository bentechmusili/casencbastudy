package com.example.demo.service;

import java.util.List;

public interface CountryService {

    CountryInfo createCountry(String countryName);
    List<CountryInfo> getAllCountries();
    CountryInfo getCountryById(Long id);
    CountryInfo updateCountry(Long id, String countryName);
    void deleteCountry(Long id);
}
