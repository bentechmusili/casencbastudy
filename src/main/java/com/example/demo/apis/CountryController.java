package com.example.demo.apis;


import com.example.demo.models.CountryInfo;
import com.example.demo.models.dto.CountryRequest;
import com.example.demo.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;


    @PostMapping
    public CountryInfo create(@RequestBody CountryRequest request) {
        return countryService.createCountry(request.getName());
    }

    @GetMapping
    public List<CountryInfo> getAll() {
        return countryService.getAllCountryInfo();
    }

    @GetMapping("/{id}")
    public CountryInfo getById(@PathVariable Long id) {
        return countryService.getCountryById(id);
    }

    @PutMapping("/{id}")
    public CountryInfo update(@PathVariable Long id,
                              @RequestBody CountryRequest request) {
        return countryService.updateCountry(id, request.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }

}