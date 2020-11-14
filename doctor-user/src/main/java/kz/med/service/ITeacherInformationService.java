package kz.med.service;


import kz.med.model.TeacherInformation;

public interface ITeacherInformationService {

    TeacherInformation findByUserId(int userId);

    TeacherInformation findAll();

}
