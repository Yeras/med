package kz.med.service.impl;

import kz.med.model.User;
import kz.med.repository.RegistrationRepository;
import kz.med.service.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService implements IRegistrationService {

    @Autowired
    RegistrationRepository registrationRepository;


    public User saveUser(User user) {
        return registrationRepository.save(user);
    }

    public User deactivateUser(Integer userId) {

        /**
         * # Find user for deactivating
         * */
        Optional<User> userObject = registrationRepository.findById(userId);

        /**
         * # Change account status, false means deactivate
         * */
        userObject.get().setUserActivate(false);

        return registrationRepository.save(userObject.get());
    }

    public User findById(int userId) {
        return registrationRepository.findById(userId).get();
    }

    @Override
    public User loginUser(String email, String password) {

        try {
            registrationRepository.findByEmailIdIgnoreCaseAndPassword(email, password).getEmailId();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getCause());
            return null;
        }

        return registrationRepository.findByEmailIdIgnoreCaseAndPassword(email, password);
    }

    @Override
    public User findByEmailId(String email) {
        return registrationRepository.findByEmailIdContainingIgnoreCase(email);
    }

    @Override
    public User findByEmailIdIgnoreCase(String email) {
        return registrationRepository.findByEmailIdIgnoreCase(email);
    }

}
