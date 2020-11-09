package kz.med.service.impl;

import kz.med.model.University;
import kz.med.repository.UniversityRepository;
import kz.med.service.IUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService implements IUniversityService {

    @Autowired
    UniversityRepository universityRepository;


    @Override
    public List<University> findByCityId(int cityId) {
        return universityRepository.findAllByCityIdOrderByName(cityId);
    }

    @Override
    public University findById(int universityId) {
        return universityRepository.findById(universityId);
    }

}
