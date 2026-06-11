package com.example.demo.util;

import com.example.demo.models.dto.FullCountryInfoResponse;
import com.example.demo.models.dto.LanguageDto;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CountryInfoMapper {

    public static FullCountryInfoResponse map(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            Document doc = factory.newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));

            NodeList resultNodes =
                    doc.getElementsByTagNameNS("*", "FullCountryInfoResult");

            if (resultNodes.getLength() == 0) {
                throw new RuntimeException(
                        "FullCountryInfoResult element not found in SOAP response");
            }

            Element root = (Element) resultNodes.item(0);

            FullCountryInfoResponse response = new FullCountryInfoResponse();

            response.setIsoCode(get(root, "sISOCode"));
            response.setName(get(root, "sName"));
            response.setCapitalCity(get(root, "sCapitalCity"));
            response.setPhoneCode(get(root, "sPhoneCode"));
            response.setContinentCode(get(root, "sContinentCode"));
            response.setCurrencyISOCode(get(root, "sCurrencyISOCode"));
            response.setCountryFlag(get(root, "sCountryFlag"));

            // Languages
            List<LanguageDto> languages = new ArrayList<>();

            NodeList languageNodes =
                    root.getElementsByTagNameNS("*", "tLanguage");

            for (int i = 0; i < languageNodes.getLength(); i++) {
                Element languageElement = (Element) languageNodes.item(i);

                LanguageDto language = new LanguageDto();
                language.setIsoCode(get(languageElement, "sISOCode"));
                language.setName(get(languageElement, "sName"));

                languages.add(language);
            }

            response.setLanguages(languages);

            return response;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse FullCountryInfo SOAP response", e);
        }
    }

    private static String get(Element parent, String tagName) {
        if (parent == null) {
            return null;
        }

        NodeList nodes =
                parent.getElementsByTagNameNS("*", tagName);

        if (nodes.getLength() == 0) {
            return null;
        }

        Node node = nodes.item(0);

        return node != null
                ? node.getTextContent().trim()
                : null;
    }
}


