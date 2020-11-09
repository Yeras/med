package kz.med.service;

import kz.med.model.UserEducationInformation;

import java.util.List;

public interface IUserEducationInformation {

//    List<UserEducationInformation> findAllByEmail(String emailId);
    List<UserEducationInformation> findAllByUserId(int userId);

    UserEducationInformation findById(int id);
}
