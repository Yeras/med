package kz.med.controller;

import kz.med.model.City;
import kz.med.service.impl.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityService cityService;


    @GetMapping("/all/{countryId}")
    public List<City> findByCountryId(@PathVariable int countryId) {
        return cityService.findByCountryId(countryId);
    }

    @GetMapping("/{cityId}")
    public City findById(@PathVariable int cityId) {
        return cityService.findById(cityId);
    }

}