package kz.med.repository;

import kz.med.model.UserTeacherInformationList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTeacherInformationListRepository extends JpaRepository<UserTeacherInformationList, Integer> {

    List<UserTeacherInformationList> findByUserId(Integer userId);

}
