package kz.med.service.impl;

import kz.med.model.UserInformation;
import kz.med.repository.UserInformationRepository;
import kz.med.service.IUserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService implements IUserInformationService {

    @Autowired
    UserInformationRepository userInformationRepository;


    public UserInformation save(UserInformation userInformation) {
        return userInformationRepository.save(userInformation);
    }

    @Override
    public UserInformation findByUserId(int userId) {
        return userInformationRepository.findByUserId(userId);
    }

}
