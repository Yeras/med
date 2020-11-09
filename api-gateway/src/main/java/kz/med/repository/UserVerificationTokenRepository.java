package kz.med.repository;

import kz.med.model.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Integer> {

    UserVerificationToken findByToken(String token);

    UserVerificationToken findByUserId(Integer userId);

}
