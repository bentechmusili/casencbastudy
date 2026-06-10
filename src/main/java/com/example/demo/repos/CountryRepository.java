package com.example.demo.repos;

import com.example.demo.models.CountryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryInfo, Long> {
}

