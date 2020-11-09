package kz.med.repository;

import kz.med.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<User, Integer> {

    User findByEmailIdIgnoreCaseAndPassword(String email, String password);

    User findByEmailIdContainingIgnoreCase(String email);

    User findByEmailIdIgnoreCase(String email);
}
