package com.example.demo.util;

import com.example.demo.models.CountryInfo;
import com.example.demo.models.Language;
import com.example.demo.models.dto.FullCountryInfoResponse;

import java.util.Collections;

public class DTOtoEntityMapper {


    public static CountryInfo toEntity(FullCountryInfoResponse dto) {

        if (dto == null) return null;

        return CountryInfo.builder()
                .countryName(dto.getName())
                .countryIsoCode(dto.getIsoCode())
                .capital(dto.getCapitalCity())
                .continent(dto.getContinentCode())
                .phoneCode(dto.getPhoneCode())
                .currencyIsoCode(dto.getCurrencyISOCode())
                .countryFlag(dto.getCountryFlag())
                .languages(
                        (
                                dto.getLanguages() == null
                                        ? Collections.emptyList()
                                        : dto.getLanguages().stream()
                                        .map(lang -> Language.builder()
                                                .isoCode(lang.getIsoCode())
                                                .languageName(lang.getName())
                                                .build())
                                        .toList()
                        )
                )
                .build();

    }
}
