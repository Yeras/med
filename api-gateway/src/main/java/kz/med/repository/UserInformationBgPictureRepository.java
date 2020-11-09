package kz.med.repository;

import kz.med.model.UserInformationBgPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInformationBgPictureRepository extends JpaRepository<UserInformationBgPicture, Integer> {

    Optional<UserInformationBgPicture> findByUserId(int userId);

    UserInformationBgPicture getByUserId(int userId);
}
