package com.example.demo.service.impl;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.CountryInfo;
import com.example.demo.repos.CountryRepository;
import com.example.demo.service.CountryService;
import com.example.demo.soap.client.CountryInfoSoapClient;
import com.example.demo.soap.client.IsoSoapClient;
import com.example.demo.util.TextUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;
    private final IsoSoapClient isoSoapClient;
    private final CountryInfoSoapClient countryInfoSoapClient;

    @Override
    @Transactional
    public CountryInfo createCountry(String countryName) {

        log.info("Starting country creation | input={}", countryName);

        String normalizedName = TextUtil.normalizeCountry(countryName);

        String isoCode;
        try {
            isoCode = isoSoapClient.getIsoCode(normalizedName);
        } catch (Exception ex) {
            log.error("ISO SOAP failure for country={}", normalizedName, ex);
            throw new ExternalServiceException("Failed to fetch ISO code");
        }

        if (isoCode == null || isoCode.isBlank() || "UNKNOWN".equalsIgnoreCase(isoCode)) {
            throw new ExternalServiceException("Invalid ISO code received for " + normalizedName);
        }

        String fullInfoXml;
        try {
            fullInfoXml = countryInfoSoapClient.getFullCountryInfo(isoCode);
        } catch (Exception ex) {
            log.error("FullCountryInfo SOAP failure for ISO={}", isoCode, ex);
            throw new ExternalServiceException("Failed to fetch country details");
        }

        if (fullInfoXml == null || fullInfoXml.isBlank()) {
            throw new ExternalServiceException("Empty response from country info service");
        }

        String capital = countryInfoSoapClient.extract(fullInfoXml, "sCapitalCity");
        String continent = countryInfoSoapClient.extract(fullInfoXml, "sContinentCode");
        String phoneCode = countryInfoSoapClient.extract(fullInfoXml, "sPhoneCode");
        String soapName = countryInfoSoapClient.extract(fullInfoXml, "sName");

        CountryInfo country = CountryInfo.builder()
                .countryName(
                        normalizedName != null ? normalizedName : soapName
                )
                .countryIsoCode(isoCode)
                .capital(capital)
                .continent(continent)
                .phoneCode(phoneCode)
                .build();

        CountryInfo saved = repository.save(country);

        log.info("Country created successfully | id={}", saved.getId());

        return saved;
    }

    @Override
    public List<CountryInfo> getAllCountryInfo() {
        log.info("Fetching all countries");
        return repository.findAll();
    }

    @Override
    public CountryInfo getCountryById(Long id) {

        log.info("Fetching country by id={}", id);

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Country not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public CountryInfo updateCountry(Long id, String countryName) {

        log.info("Updating country | id={} | name={}", id, countryName);

        CountryInfo existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Country not found with id: " + id
                ));

        existing.setCountryName(TextUtil.normalizeCountry(countryName));

        CountryInfo updated = repository.save(existing);

        log.info("Country updated | id={}", updated.getId());

        return updated;
    }

    @Override
    public void deleteCountry(Long id) {

        log.info("Deleting country | id={}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Country not found with id: " + id
            );
        }

        repository.deleteById(id);

        log.info("Country deleted successfully | id={}", id);
    }
}