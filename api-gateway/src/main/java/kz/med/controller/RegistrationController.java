package kz.med.controller;


import kz.med.model.User;
import kz.med.model.UserVerificationToken;
import kz.med.repository.RegistrationRepository;
import kz.med.repository.UserVerificationTokenRepository;
import kz.med.service.impl.EmailService;
import kz.med.service.impl.RegistrationService;
import kz.med.service.impl.UserVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@RestController
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserVerificationTokenRepository userVerificationTokenRepository;
    @Autowired
    UserVerificationTokenService userVerificationTokenService;
    @Autowired
    RegistrationRepository registrationRepository;


//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;


    /**************************************************************
     * #1 For user registration (registration, token, token activation)
     * ************************************************************/

    @PostMapping("/registration")
    @CrossOrigin(origins = "http://localhost:4200")
//    @CrossOrigin(origins = "http://78.140.223.4:4200")
    public User save(@RequestBody User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        System.out.println("Registration Service");
        // Use for return object and for token
        User userRegistration;

        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setEmailId(user.getEmailId().toLowerCase());
        user.setLanguageType("English");
        user.setUserActivate(true);

        userRegistration = registrationService.saveUser(user);

//        String tokenGenerate = UUID.randomUUID().toString();
//        userVerificationTokenService.saveToken(userRegistration.getEmailId(), tokenGenerate);
//
//        try {
//            System.out.println("Try get email: " + userRegistration.getEmailId());
//            emailService.sendHtmlMail(userRegistration.getEmailId());
//        } catch (Exception e) {
//            System.out.println("Verification Token: " + e.getCause());
//            return userRegistration;
//        }

        return userRegistration;
    }

    @GetMapping("/activation")
    public boolean activation(@RequestParam("token") String token) {

        UserVerificationToken userVerificationToken = userVerificationTokenRepository.findByToken(token);

        if (userVerificationToken == null) {
            return false;
        } else {
            Optional<User> userObject = registrationRepository.findById(userVerificationToken.getUserId());

            User userStatChange = new User();

            userStatChange.setId(userObject.get().getId());
            userStatChange.setEmailId(userObject.get().getEmailId());
            userStatChange.setPassword(userObject.get().getPassword());
            userStatChange.setPrivatePolicy(userObject.get().getPrivatePolicy());
            userStatChange.setLanguageType(userObject.get().getLanguageType());
            userStatChange.setUserActivate(true);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            if (userVerificationToken.getExpiryDate().before(currentTime)) {
                return false;
            } else {
                registrationRepository.save(userStatChange);
            }
        }

        return true;
    }

    @GetMapping("/activation/stat")
    public boolean activationStat(@RequestParam("token") String token) {

        UserVerificationToken userVerificationToken = userVerificationTokenRepository.findByToken(token);

        User userStat = registrationService.findById(userVerificationToken.getUserId());

        if (!userStat.getUserActivate()) {
            return false;
        } else {
            return true;
        }
    }

    @GetMapping("/regenerate/token/stat")
    public String regenerateTokenStat(@RequestParam("emailId") String emailId) {

        // @ Find user by emailId
        User userObject = userVerificationTokenService.findByEmail(emailId);

        if (userObject == null) {
            return "Email is not registered before";
        } else if (userObject.getUserActivate()) {
            return "Your account was activated";
        }

        // @ Find Activation Token Information by UserId
        UserVerificationToken userVerificationToken = userVerificationTokenService.findByUserId(userObject.getId());

        // @ Check token
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (userVerificationToken.getExpiryDate().before(currentTime)) {
            String newTokenInfo = regenerateToken(userObject.getEmailId());
            return newTokenInfo;
        } else {
            return "Your token is active, please try after 24 hours";
        }
    }

    public String regenerateToken(String emailId) {

        // @ Find user by emailId
        User userObject = userVerificationTokenService.findByEmail(emailId);

        // @ Find Activation Token Information by UserId
        UserVerificationToken userVerificationToken = userVerificationTokenService.findByUserId(userObject.getId());

        // @ New Token
        String tokenGenerate = UUID.randomUUID().toString();

        // @ Save new token
        userVerificationTokenService.regenerateToken(userVerificationToken, tokenGenerate);

        try {
            // @ Send new token
            emailService.sendHtmlMail(userObject.getEmailId());
        } catch (Exception e) {
            System.out.println("Regenerate token exception: " + e.getCause());
        }

        return "Token regenerated";
    }

    @GetMapping("/status/user")
    @CrossOrigin(origins = "http://localhost:4200")
//    @CrossOrigin(origins = "http://78.140.223.4:4200")
    public boolean userStatus(@RequestParam("emailId") String emailId) {

        User user = registrationService.findByEmailIdIgnoreCase(emailId);
        if (user == null) {
            return false;
        }

        System.out.println("User Status: " + user.getUserActivate());
        return user.getUserActivate();
    }

    /**************************************************************
     * #2 For user settings, access to front (login and change main information, change password, deactivate account, change language)
     * ************************************************************/

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
//    @CrossOrigin(origins = "http://78.140.223.4:4200")
    public User loginUser(@RequestBody User user) throws Exception {

        User userObj = registrationService.findByEmailIdIgnoreCase(user.getEmailId());

        if (userObj != null) {
            user.setId(userObj.getId());
            user.setLanguageType(userObj.getLanguageType());
            return user;
        } else {
            throw new Exception("Bad connection");
        }
    }

    @PostMapping("/registration/admin")
    @CrossOrigin(origins = "http://localhost:4200")
//    @CrossOrigin(origins = "http://78.140.223.4:4200")
    public User createByAdmin(@RequestBody User user) {
        return registrationService.saveUser(user);
    }

    @GetMapping("/user/{emailId}")
    @CrossOrigin(origins = "http://localhost:4200")
//    @CrossOrigin(origins = "http://78.140.223.4:4200")
    public User findByEmail(@PathVariable String emailId) {
        return registrationService.findByEmailId(emailId.toLowerCase());
    }

    @GetMapping("/check/user/{emailId}")
    @CrossOrigin(origins = "http://localhost:4200")
//    @CrossOrigin(origins = "http://78.140.223.4:4200")
    public String checkUserByEmail(@PathVariable String emailId) {

        try {
            registrationService.findByEmailIdIgnoreCase(emailId).getEmailId();
        } catch (Exception e) {
            return "Not found, you can use this account name";
        }

        return registrationService.findByEmailIdIgnoreCase(emailId).getEmailId();
    }

    @PostMapping("/password/change")
    public String changePassword(@RequestParam("emailId") String emailId,
                                 @RequestParam("oldPass") String oldPass,
                                 @RequestParam("newPass") String newPass) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodeNewPassword = bCryptPasswordEncoder.encode(newPass);

        User user = registrationService.findByEmailId(emailId);

        if (bCryptPasswordEncoder.matches(newPass, user.getPassword())) {
            return "Exception the same";
        } else if (bCryptPasswordEncoder.matches(oldPass, user.getPassword())) {
            user.setPassword(encodeNewPassword);
            registrationService.saveUser(user);

            return "Password was changed";
        } else {
            return "Exception not the same";
        }
    }

    @PostMapping("/language/change")
    public String changeLanguage(@RequestParam("emailId") String emailId,
                                 @RequestParam("language") String language) {

        User user = registrationService.findByEmailId(emailId);

        if (user.getLanguageType().equals(language)) {
            return "Exception the same";
        } else {
            user.setLanguageType(language);
            registrationService.saveUser(user);

            return "Language was changed";
        }
    }

    @GetMapping("/deactivate/account/{userId}")
    public String deactivateAccount(@PathVariable Integer userId) {

        registrationService.deactivateUser(userId);

        return "deactivated";
    }

    /******************************
     * FORGOT PASSWORD METHODS
     * ****************************/
    @PostMapping("/forgot/password")
    public String forgotPasswordGenerate(@RequestParam("emailId") String emailId) {

        User user = registrationRepository.findByEmailIdIgnoreCase(emailId);

        if (user == null){
            return "Invalid email";
        }

        // @ New Token
        String tokenGenerate = UUID.randomUUID().toString();

        try {
            // @ Send new token
            emailService.sendHtmlMailChangePassword(emailId, tokenGenerate);
        } catch (Exception e) {
            System.out.println("Generate new password: " + e.getCause());
            return "Exception";
        }

        return "Correct";
    }

    @GetMapping("/activation/forgot/password")
    public String activationForgotPassword(@RequestParam("token") String token) {

        String activationInformation = "";
        activationInformation = emailService.giveNewPassword(token);

        return activationInformation;
    }

}
