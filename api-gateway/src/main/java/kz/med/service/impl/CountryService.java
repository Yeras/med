package kz.med.service.impl;

import kz.med.model.Country;
import kz.med.repository.CountryRepository;
import kz.med.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService implements ICountryService {

    @Autowired
    CountryRepository countryRepository;


    public List<Country> findAll() {
        return countryRepository.findAllByOrderByName();
    }

    @Override
    public Country findById(int id) {
        return countryRepository.findById(id);
    }

}
