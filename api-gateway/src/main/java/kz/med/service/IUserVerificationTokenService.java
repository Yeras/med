package kz.med.service;

import kz.med.model.User;
import kz.med.model.UserVerificationToken;

public interface IUserVerificationTokenService {

    UserVerificationToken findByToken(String token);

    UserVerificationToken findByUserId(Integer userId);

    User findByEmail(String emailId);

}
