package kz.med.service;

import kz.med.model.University;

import java.util.List;

public interface IUniversityService {

    List<University> findByCityId(int cityId);

    University findById(int universityId);

}
