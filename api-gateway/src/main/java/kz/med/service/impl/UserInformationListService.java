package kz.med.service.impl;

import kz.med.model.UserInformationList;
import kz.med.repository.UserInformationListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInformationListService {

    @Autowired
    UserInformationListRepository informationListRepository;


    public UserInformationList save(UserInformationList userInformationList){
        return informationListRepository.save(userInformationList);
    }

    public List<UserInformationList> findById(Integer userId) {
        return informationListRepository.findByUserId(userId);
    }

}
