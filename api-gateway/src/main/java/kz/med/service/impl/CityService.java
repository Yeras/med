package kz.med.service.impl;

import kz.med.model.City;
import kz.med.repository.CityRepository;
import kz.med.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService implements ICityService {

    @Autowired
    CityRepository cityRepository;


    public List<City> findByCountryId(int countryId) {
        return cityRepository.findAllByCountryIdOrderByName(countryId);
    }

    public City findById(int cityId) {
        return cityRepository.findById(cityId);
    }

}
