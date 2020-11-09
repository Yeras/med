package kz.med.repository;

import kz.med.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {

    UserInformation findByUserId(int userId);
}
