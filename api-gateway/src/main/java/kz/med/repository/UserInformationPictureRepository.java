package kz.med.repository;

import kz.med.model.UserInformationPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInformationPictureRepository extends JpaRepository<UserInformationPicture, Integer> {

    Optional<UserInformationPicture> findByUserId(int userId);

    UserInformationPicture getByUserId(int userId);

}
