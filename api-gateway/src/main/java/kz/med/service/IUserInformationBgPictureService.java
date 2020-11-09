package kz.med.service;

import kz.med.model.UserInformationBgPicture;

import java.util.Optional;

public interface IUserInformationBgPictureService {

    Optional<UserInformationBgPicture> findByUserId(int userId);

    UserInformationBgPicture getByUserId(int userId);
}
