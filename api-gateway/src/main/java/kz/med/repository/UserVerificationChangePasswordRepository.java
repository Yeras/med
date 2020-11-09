package kz.med.repository;

import kz.med.model.UserVerificationChangePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerificationChangePasswordRepository extends JpaRepository<UserVerificationChangePassword, Integer> {

    UserVerificationChangePassword findByUserId(Integer userId);

    UserVerificationChangePassword findByToken(String token);

}
