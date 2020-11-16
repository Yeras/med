package kz.med.repository;

import kz.med.model.PatientAppointmentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientAppointmentScheduleRepository extends JpaRepository<PatientAppointmentSchedule, Integer> {

    List<PatientAppointmentSchedule> findAllByAppointmentId(Integer id);

}
