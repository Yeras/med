package kz.med.repository;

import kz.med.model.TeacherInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherInformationRepository extends JpaRepository<TeacherInformation, Integer> {

    TeacherInformation findByUserId(int userId);

//    List<TeacherInformation> findAllByUserIdIn(List<Integer> userId);

    List<TeacherInformation> findAllByUserIdInAndFirstNameContainingIgnoreCaseOrUserIdInAndLastNameContainingIgnoreCase(List<Integer> userId, String firstName, List<Integer> userId2, String lastName);

    Page<TeacherInformation> findAllByUserIdInAndFirstNameContainingIgnoreCaseOrUserIdInAndLastNameContainingIgnoreCase(Pageable var1, List<Integer> userId, String firstName, List<Integer> userId2, String lastName);

}
