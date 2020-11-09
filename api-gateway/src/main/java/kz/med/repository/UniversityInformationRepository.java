package kz.med.repository;

import kz.med.model.UniversityInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityInformationRepository extends JpaRepository<UniversityInformation, Integer> {

    UniversityInformation findByUniversityId(Integer universityId);

}
