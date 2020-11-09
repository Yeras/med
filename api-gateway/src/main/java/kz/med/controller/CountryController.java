package kz.med.controller;

import kz.med.model.Country;
import kz.med.service.impl.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryService countryService;


    @GetMapping("/all")
    public List<Country> findAll() {
        return countryService.findAll();
    }

    @GetMapping("/{countryId}")
    public Country findById(@PathVariable int countryId) {
        return countryService.findById(countryId);
    }

}
