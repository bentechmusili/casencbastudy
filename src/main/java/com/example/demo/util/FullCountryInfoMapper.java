package com.example.demo.util;

import com.example.demo.models.dto.FullCountryInfoResponse;
import com.example.demo.models.dto.LanguageDto;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class FullCountryInfoMapper {

    public static FullCountryInfoResponse map(String xml) {
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes()));

            Element root = (Element) doc.getElementsByTagNameNS("*", "FullCountryInfoResult")
                    .item(0);

            FullCountryInfoResponse response = new FullCountryInfoResponse();

            response.setIsoCode(get(root, "sISOCode"));
            response.setName(get(root, "sName"));
            response.setCapitalCity(get(root, "sCapitalCity"));
            response.setPhoneCode(get(root, "sPhoneCode"));
            response.setContinentCode(get(root, "sContinentCode"));
            response.setCurrencyISOCode(get(root, "sCurrencyISOCode"));
            response.setCountryFlag(get(root, "sCountryFlag"));

            // Languages
            NodeList langNodes = root.getElementsByTagNameNS("*", "tLanguage");
            List<LanguageDto> languages = new ArrayList<>();

            for (int i = 0; i < langNodes.getLength(); i++) {
                Element langEl = (Element) langNodes.item(i);

                LanguageDto lang = new LanguageDto();
                lang.setIsoCode(get(langEl, "sISOCode"));
                lang.setName(get(langEl, "sName"));

                languages.add(lang);
            }

            response.setLanguages(languages);

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse SOAP response", e);
        }
    }

    private static String get(Element el, String tag) {
        NodeList nodes = el.getElementsByTagNameNS("*", tag);
        if (nodes.getLength() == 0) return null;
        return nodes.item(0).getTextContent();
    }
}
