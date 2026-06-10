package com.example.demo.soap.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class CountryInfoSoapClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL =
            "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";

    public String getFullCountryInfo(String isoCode) {

        String soapRequest =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<soap:Body>" +
                        "<FullCountryInfo xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">" +
                        "<sCountryISOCode>" + isoCode + "</sCountryISOCode>" +
                        "</FullCountryInfo>" +
                        "</soap:Body>" +
                        "</soap:Envelope>";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        HttpEntity<String> request = new HttpEntity<>(soapRequest, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(URL, request, String.class);

        return response.getBody();
    }

    public String extract(String xml, String tag) {
        if (xml == null || !xml.contains("<" + tag + ">")) return null;

        return xml.split("<" + tag + ">")[1]
                .split("</" + tag + ">")[0];
    }
}