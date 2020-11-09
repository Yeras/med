package kz.med.controller;

import kz.med.model.University;
import kz.med.service.impl.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/university")
public class UniversityController {

    @Autowired
    UniversityService universityService;


    @GetMapping("/all/{cityId}")
    public List<University> findByCountryId(@PathVariable int cityId) {
        return universityService.findByCityId(cityId);
    }

    @GetMapping("/{universityId}")
    public University findById(@PathVariable int universityId) {
        return universityService.findById(universityId);
    }

}
