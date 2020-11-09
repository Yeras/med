package kz.med.service;

import kz.med.model.UserInformation;

public interface IUserInformationService {

    UserInformation findByUserId(int userId);

}
