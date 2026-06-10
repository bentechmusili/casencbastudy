package com.example.demo.apis;

import lombok.extern.slf4j.Slf4j;



import com.example.demo.models.CountryInfo;
import com.example.demo.models.dto.CountryRequest;
import com.example.demo.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;


    @PostMapping("/saveCountryInfo")
    public CountryInfo create(@RequestBody CountryRequest request) {
        log.info("POST /saveCountryInfo hit");
        return countryService.createCountry(request.getName());
    }

    @GetMapping("/getAll")
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