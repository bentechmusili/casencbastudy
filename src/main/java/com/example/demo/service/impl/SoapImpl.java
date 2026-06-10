package com.example.demo.service.impl;


import com.example.demo.soap.client.CountryInfoSoapClient;
import com.example.demo.soap.client.IsoSoapClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoapImpl {

    private final IsoSoapClient isoSoapClient;
    private final CountryInfoSoapClient countryInfoSoapClient;

    public String getCountryInfo(String countryName) {

        String normalized =
                countryName.substring(0,1).toUpperCase()
                        + countryName.substring(1).toLowerCase();

        String isoCode = isoSoapClient.getIsoCode(normalized);

        return countryInfoSoapClient.getFullCountryInfo(isoCode);
    }
}

