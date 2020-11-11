package kz.med.service.impl;

import kz.med.model.UserTeacherInformationList;
import kz.med.repository.UserTeacherInformationListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTeacherInformationListService {

    @Autowired
    UserTeacherInformationListRepository teacherInformationListRepository;


    public UserTeacherInformationList save(UserTeacherInformationList teacherInformationList) {
        return teacherInformationListRepository.save(teacherInformationList);
    }

    public List<UserTeacherInformationList> findByUserId(Integer userId) {
        return teacherInformationListRepository.findByUserId(userId);
    }

}
