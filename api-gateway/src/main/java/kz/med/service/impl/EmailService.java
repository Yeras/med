package kz.med.service.impl;

import kz.med.model.User;
import kz.med.model.UserVerificationChangePassword;
import kz.med.model.UserVerificationToken;
import kz.med.repository.RegistrationRepository;
import kz.med.repository.UserVerificationChangePasswordRepository;
import kz.med.repository.UserVerificationTokenRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    UserVerificationTokenService verificationTokenService;
    @Autowired
    UserVerificationTokenRepository verificationTokenRepository;
    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserVerificationChangePasswordRepository changePasswordRepository;


    public void sendHtmlMail(String emailId) throws MessagingException {

        // Find User by email address
        User userObject = registrationRepository.findByEmailIdIgnoreCase(emailId);

        // Find Token by email address
        UserVerificationToken userVerificationToken = verificationTokenRepository.findByUserId(userObject.getId());

        if (userVerificationToken != null) {
            String registrationToken = userVerificationToken.getToken();

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(userObject.getEmailId());
            messageHelper.setSubject("Account Activation, verification token path");

//            String tokenLink = "http://localhost:4200/#/activation?token=" + registrationToken;
            String tokenLink = "http://78.140.223.4:4200/#/activation?token=" + registrationToken;

            String messageText = "<h1>Welcome!</h1>\n\n" +
                    "<h2>Thanks for signing up with World University Knowledge!</h2><br>\n" +
                    "<h2>You must follow this link to activate your account:</h2>\n" + tokenLink;

            messageHelper.setText(messageText, true);

            javaMailSender.send(mimeMessage);
            System.out.println("Mail was send");
        }
    }

    public void sendHtmlMailChangePassword(String emailId, String token) throws MessagingException {

        int length = 8;
        boolean useLetters = true;
        boolean useNumbers = true;
        // @ Generate new password
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);


        // @ Find User Id by Email
        User user = registrationRepository.findByEmailIdIgnoreCase(emailId);

        // @ Find change password history
        UserVerificationChangePassword changePassword = changePasswordRepository.findByUserId(user.getId());

        if (changePassword != null) {
            changePasswordRepository.deleteById(changePassword.getId());
        }

        // @ Save new password
        UserVerificationChangePassword changePasswordCreate = new UserVerificationChangePassword();

        changePasswordCreate.setToken(token);
        changePasswordCreate.setUserId(user.getId());
        changePasswordCreate.setTokenStatus(false);
        changePasswordCreate.setNewPassword(generatedString);

        changePasswordRepository.save(changePasswordCreate);

        // @ Send message with password
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(user.getEmailId());
        messageHelper.setSubject("Forgot Password for WUK");

//        String tokenLink = "http://localhost:4200/#/activation-password?token=" + token;
        String tokenLink = "http://78.140.223.4:4200/#/activation-password?token=" + token;

        String messageText = "<h3>Your new password copy for WUK:</h3> <h1><u>\n" +
                generatedString + "</u></h1>" +
                "<h3>If you forgot your password, open the link, and your password will be changed:</h3>\n" + tokenLink +
                "<h2>You can change your password in your profile settings<h2> <br>" +
                "<h2>If you not forgot password and not choose <u>'Forgot password'</u>, ignore this message<h2>";

        messageHelper.setText(messageText, true);

        javaMailSender.send(mimeMessage);
        System.out.println("Mail was send");
    }

    public String giveNewPassword(String token) {

        UserVerificationChangePassword changePassword = changePasswordRepository.findByToken(token);

        if (changePassword == null) {
            return "Invalid token";
        } else if (changePassword.getTokenStatus() == true) {
            return "Token is active";
        }

        // @ Deactivate token
        changePassword.setTokenStatus(true);
        changePasswordRepository.save(changePassword);

        try {
            // @ Find User for changing password
            Optional<User> user = registrationRepository.findById(changePassword.getUserId());

            // Crypt new password
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodeNewPassword = bCryptPasswordEncoder.encode(changePassword.getNewPassword());
            user.get().setPassword(encodeNewPassword);

            registrationRepository.save(user.get());

            // @ Delete information forgot password
            changePasswordRepository.deleteById(changePassword.getId());
        } catch (Exception e) {
            // @ Activate token, if method finish with exception
            changePassword.setTokenStatus(false);
            changePasswordRepository.save(changePassword);
            return "Exception token";
        }

        return "Correct";
    }

}
