package kz.med.service.impl;

import kz.med.model.UserInformationBgPicture;
import kz.med.repository.UserInformationBgPictureRepository;
import kz.med.service.IUserInformationBgPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInformationBgPictureService implements IUserInformationBgPictureService {

    @Autowired
    UserInformationBgPictureRepository bgPictureRepository;


    public UserInformationBgPicture uploadFile(UserInformationBgPicture informationBgPicture) {
        return bgPictureRepository.save(informationBgPicture);
    }

    @Override
    public Optional<UserInformationBgPicture> findByUserId(int userId) {
        return bgPictureRepository.findByUserId(userId);
    }

    @Override
    public UserInformationBgPicture getByUserId(int userId) {
        return bgPictureRepository.getByUserId(userId);
    }


}
