package kz.med.service;

import kz.med.model.UserInformationPicture;

import java.util.Optional;

public interface IUserInformationPictureService {

    Optional<UserInformationPicture> findByUserId(int userId);

    UserInformationPicture getByUserId(int userId);

}
