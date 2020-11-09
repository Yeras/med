package kz.med.repository;

import kz.med.model.UserEducationInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEducationInformationRepository extends JpaRepository<UserEducationInformation, Integer> {

    List<UserEducationInformation> findAllByUserInfoIdOrderById(int userInfoId);

    UserEducationInformation findById(int id);
}
