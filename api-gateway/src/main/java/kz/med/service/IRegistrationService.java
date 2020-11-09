package kz.med.service;

import kz.med.model.User;

public interface IRegistrationService {

    User loginUser(String email, String password);

    User findByEmailId(String email);

    User findByEmailIdIgnoreCase(String email);

}
