package com.example.demo.soap.client;

import com.example.demo.util.TextUtil;
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
        countryName = TextUtil.normalizeCountry(countryName);

        String soapRequest = """
        <?xml version="1.0" encoding="utf-8"?>
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                          xmlns:web="http://www.oorsprong.org/websamples.countryinfo">
           <soapenv:Header/>
           <soapenv:Body>
              <web:CountryISOCode>
                 <web:sCountryName>%s</web:sCountryName>
              </web:CountryISOCode>
           </soapenv:Body>
        </soapenv:Envelope>
        """.formatted(countryName);
        log.info("SOAP Request: {}", soapRequest);

        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "text/xml; charset=utf-8");

        HttpEntity<String> request = new HttpEntity<>(soapRequest, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(URL, request, String.class);

        String body = response.getBody();
        log.info("Full SOAP Response:\n{}", body);

        return extract(response.getBody(), "m:CountryISOCodeResult");
    }

    private String extract(String xml, String tag) {
        if (xml == null || !xml.contains(tag)) return null;

        return xml.split("<" + tag + ">")[1]
                .split("</" + tag + ">")[0];
    }

}
