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
//@Retryable(
//        value = Exception.class,
//        maxAttempts = 3,
//        backoff = @Backoff(delay = 2000)
public class IsoSoapClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String URL =
            "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";

    public String getIsoCode(String countryName) {

        String soapRequest =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<soap:Body>" +
                        "<CountryNameToCountryISOCode xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">" +
                        "<sCountryName>" + countryName + "</sCountryName>" +
                        "</CountryNameToCountryISOCode>" +
                        "</soap:Body>" +
                        "</soap:Envelope>";
        log.info("SOAP Request: {}", soapRequest);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.TEXT_XML);

        HttpEntity<String> request = new HttpEntity<>(soapRequest, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(URL, request, String.class);

        return extract(response.getBody(), "CountryNameToCountryISOCodeResult");
    }

    private String extract(String xml, String tag) {
        if (xml == null || !xml.contains(tag)) return null;

        return xml.split("<" + tag + ">")[1]
                .split("</" + tag + ">")[0];
    }

}
