package kz.med.service.impl;

import kz.med.model.TeacherInformation;
import kz.med.repository.TeacherInformationRepository;
import kz.med.service.ITeacherInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherInformationService implements ITeacherInformationService {

    @Autowired
    TeacherInformationRepository teacherInformationRepository;


    public TeacherInformation save(TeacherInformation teacherInformation) {
        return teacherInformationRepository.save(teacherInformation);
    }

    @Override
    public TeacherInformation findByUserId(int userId) {
        return teacherInformationRepository.findByUserId(userId);
    }

    @Override
    public TeacherInformation findAll() {
        return null;
    }

}
