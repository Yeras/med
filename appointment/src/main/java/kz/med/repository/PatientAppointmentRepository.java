package kz.med.repository;

import kz.med.model.PatientAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PatientAppointmentRepository extends JpaRepository<PatientAppointment, Integer> {


//    @Query("SELECT u FROM PatientAppointment u WHERE u.startSendMessage < ?1 AND u.startSendMessage > ?2")
//    List<PatientAppointment> findAllByStartSendMessageAndStartSendMessage(LocalDateTime sendDate, LocalDateTime sendDate2);

    List<PatientAppointment> findAllByStartSendMessageLessThanEqualAndStartSendMessageGreaterThanEqual(LocalDateTime sendDate, LocalDateTime sendDate2);
}
