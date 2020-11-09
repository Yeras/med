package kz.med.service.impl;

import kz.med.model.UniversityInformation;
import kz.med.model.UserEducationInformation;
import kz.med.repository.UniversityInformationRepository;
import kz.med.repository.UserEducationInformationRepository;
import kz.med.service.IUserEducationInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserEducationEducationInformationService implements IUserEducationInformation {

    @Autowired
    UserEducationInformationRepository userEducationInformationRepository;

    @Autowired
    UniversityInformationRepository universityInformationRepository;


    public UserEducationInformation save(UserEducationInformation userEducationInformationList) {
        return userEducationInformationRepository.save(userEducationInformationList);
    }

    @Override
    public UserEducationInformation findById(int id) {

        /**
         * Find Information about this education info
         * */
        UserEducationInformation userEducationInformation = userEducationInformationRepository.findById(id);

        /**
         * Find user id of university for picture
         * */
        UniversityInformation universityInformation = universityInformationRepository.findByUniversityId(userEducationInformation.getUniversityId());

        /**
         * Add user id for picture
         * */
        userEducationInformation.setUniversityPictureId(universityInformation.getUserId());

        return userEducationInformation;
    }

    @Override
    public List<UserEducationInformation> findAllByUserId(int userId) {

        /**
         * List of education information
         * */
        List<UserEducationInformation> userEducationInformationList = userEducationInformationRepository.findAllByUserInfoIdOrderById(userId);

        /**
         * Add information for this Object(List), then return
         * */
        List<UserEducationInformation> userEducationInformationAdd = new ArrayList<>();

        for (UserEducationInformation userEducationInformation : userEducationInformationList) {

            // @ Find user id of university - then will use for getting avatar
            UniversityInformation universityInformation = universityInformationRepository.findByUniversityId(userEducationInformation.getUniversityId());

            // @ Set this user id of university
            userEducationInformation.setUniversityPictureId(universityInformation.getUserId());

            userEducationInformationAdd.add(userEducationInformation);
        }

        return userEducationInformationAdd;
    }

    public void delete(int id) {
        userEducationInformationRepository.deleteById(id);
    }

}
