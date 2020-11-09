package kz.med.service.impl;

import kz.med.model.User;
import kz.med.model.UserVerificationToken;
import kz.med.repository.RegistrationRepository;
import kz.med.repository.UserVerificationTokenRepository;
import kz.med.service.IUserVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class UserVerificationTokenService implements IUserVerificationTokenService {

    @Autowired
    UserVerificationTokenRepository userVerificationTokenRepository;

    @Autowired
    RegistrationRepository registrationRepository;


    @Override
    public UserVerificationToken findByToken(String token) {
        return userVerificationTokenRepository.findByToken(token);
    }

    @Override
    public UserVerificationToken findByUserId(Integer userId) {
        return userVerificationTokenRepository.findByUserId(userId);
    }

    @Override
    public User findByEmail(String emailId) {
        return registrationRepository.findByEmailIdIgnoreCase(emailId);
    }

    public void saveToken(String emailId, String token) {
        UserVerificationToken userVerificationToken = new UserVerificationToken();

        User userObject = registrationRepository.findByEmailIdIgnoreCase(emailId);

        userVerificationToken.setUserId(userObject.getId());
        userVerificationToken.setToken(token);
        userVerificationToken.setExpiryDate(calculateExpiryDate(24 * 60));


        userVerificationTokenRepository.save(userVerificationToken);
    }

    public void regenerateToken(UserVerificationToken userVerificationToken, String newToken) {
        userVerificationToken.setToken(newToken);
        userVerificationToken.setExpiryDate(calculateExpiryDate(24 * 60));

        userVerificationTokenRepository.save(userVerificationToken);

    }

    private Timestamp calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);

        return new Timestamp(calendar.getTime().getTime());
    }

}
