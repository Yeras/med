package kz.med.service.impl;

import kz.med.model.UserInformationPicture;
import kz.med.repository.UserInformationPictureRepository;
import kz.med.service.IUserInformationPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInformationPictureService implements IUserInformationPictureService {

    @Autowired
    UserInformationPictureRepository pictureRepository;


    public UserInformationPicture uploadFile(UserInformationPicture informationPicture) {
        return pictureRepository.save(informationPicture);
    }

    @Override
    public Optional<UserInformationPicture> findByUserId(int userId) {
        return pictureRepository.findByUserId(userId);
    }

    @Override
    public UserInformationPicture getByUserId(int userId) {
        return pictureRepository.getByUserId(userId);
    }

}
