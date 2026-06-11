package com.example.demo.soap.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
@Slf4j
public class CountryInfoSoapClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL =
            "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";

    public String getFullCountryInfo(String isoCode) {
        log.info("ISO Code: {}", isoCode);

        String soapRequest =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                        "xmlns:web=\"http://www.oorsprong.org/websamples.countryinfo\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                        "<web:FullCountryInfo>" +
                        "<web:sCountryISOCode>" + isoCode + "</web:sCountryISOCode>" +
                        "</web:FullCountryInfo>" +
                        "</soapenv:Body>" +
                        "</soapenv:Envelope>";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        log.info("SOAP Request: {}", soapRequest);

        HttpEntity<String> request = new HttpEntity<>(soapRequest, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(URL, request, String.class);
        log.info("Full CountryInfo Body: {}", response);

        return response.getBody();
    }

    public String extract(String xml, String tag) {
        if (xml == null || !xml.contains("<" + tag + ">")) return null;

        return xml.split("<" + tag + ">")[1]
                .split("</" + tag + ">")[0];
    }
}