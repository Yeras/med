package kz.med.repository;

import kz.med.model.UserInformationList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInformationListRepository extends JpaRepository<UserInformationList, Integer> {

    List<UserInformationList> findByUserId(Integer userId);
}
