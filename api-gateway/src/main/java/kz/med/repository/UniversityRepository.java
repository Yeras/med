package kz.med.repository;

import kz.med.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {

    List<University> findAllByCityIdOrderByName(int cityId);

    University findById(int universityId);

}
