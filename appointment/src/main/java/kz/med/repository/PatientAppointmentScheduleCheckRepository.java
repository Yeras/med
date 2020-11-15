package kz.med.repository;

import kz.med.model.PatientAppointmentScheduleCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientAppointmentScheduleCheckRepository extends JpaRepository<PatientAppointmentScheduleCheck, Integer> {

}
